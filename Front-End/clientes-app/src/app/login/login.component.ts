import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { Usuario } from './usuario'

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  username: string;
  password:string;
  cadastrando: boolean;
  mensagemSucesso: string;
  errors: String [];

  constructor(private router: Router, private authService:AuthService) { }

  onSubmit(){
    this.authService
      .tentarLogar(this.username, this.password)
      .subscribe(response =>{
        const access_token = JSON.stringify(response);  //transforma em string
        localStorage.setItem('access_token',access_token);
        this.router.navigate(['/home'])
      },errorResponse =>{
        this.errors = ['Usuário e/ou senha incorretos!'];
      })
  }

  preparaCadastrar(event:any){
    event.preventDefault();
    this.cadastrando = true;
    this.username="";
    this.password="";
  }

  cancelaCadastro(){
    this.cadastrando = false;
  }


  cadastrar(){
    const usuario: Usuario = new Usuario();
    usuario.username = this.username;
    usuario.password = this.password;
    this.authService.salvar(usuario).subscribe(response =>{
      this.mensagemSucesso = "Cadastro relizado com sucesso!Efetue o login."
      this.cadastrando = false;
      this.username="";
      this.password= "";
      this.errors =[];
    },errorResponse =>{
      this.mensagemSucesso = "";  
      this.errors = errorResponse.error.errors;
            
    })
  }


}
