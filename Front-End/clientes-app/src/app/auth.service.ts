import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Usuario } from './login/usuario';

import { environment } from '../environments/environment'

import { JwtHelperService } from '@auth0/angular-jwt'
import { Token } from '@angular/compiler/src/ml_parser/lexer';
import { ReturnStatement } from '@angular/compiler';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  apiURL: string = environment.apiURLBase +"/api/usuarios";
  tokenURL: string = environment.apiURLBase + environment.obterTokenUrl;
  clientId: string = environment.clientId;
  clientSecret: string = environment.clientSecret;
  jwtHelper: JwtHelperService = new JwtHelperService();

  constructor(private http: HttpClient) { }

  obterToken(){
    const tokenString = localStorage.getItem('access_token');
    if(tokenString){
      const token = JSON.parse(tokenString).access_token;
      return token;
    }else{
      return null;
    }
  }

  encerrarSessao(){
    localStorage.removeItem('access_token');
  }

  getUsuarioAutenticado(){
    const token = this.obterToken();
    if(token){
      const usuario = this.jwtHelper.decodeToken(token).user_name;
      return usuario;
    }else{
      return null;
    }
  }

  isAuthenticated(): boolean{
    const token = this.obterToken();
    if(token){
      const expired = this.jwtHelper.isTokenExpired(token);
      return !expired;
    }else{
      return false;

    }
  }

  salvar(usuario: Usuario):Observable<any>{
    return this.http.post<any>(this.apiURL, usuario);
  }

  tentarLogar(username: string, password:string): Observable<any>{
    const params = new HttpParams()
      .set('username', username)
      .set('password', password)
      .set('grant_type', 'password')

    const headers ={  // objeto de configuração
      'Authorization': 'Basic ' + btoa(`${this.clientId}:${this.clientSecret}`),  // btoa transforma em base 64
      'Content-Type': 'application/x-www-form-urlencoded'
    }

    return this.http.post(this.tokenURL,params.toString(),{headers:headers}) ;
  }
}
