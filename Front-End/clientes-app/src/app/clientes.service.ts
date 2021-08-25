import { Injectable } from '@angular/core';
import{ HttpClient } from '@angular/common/http'

import { Cliente } from './clientes/clientes';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment'

@Injectable({
  providedIn: 'root'
})
export class ClientesService {

  apiUrl : string = environment.apiURLBase + '/api/clientes';

  constructor( private http: HttpClient) {
   }

  salvar( cliente: Cliente) : Observable<Cliente>{
     
     return this.http.post<Cliente>(`${this.apiUrl}`, cliente);
    }

  getClientes() : Observable<Cliente[]> {
    console.log("URL", this.apiUrl);
     return this.http.get<Cliente[]>(this.apiUrl);
   } 
 
  getClienteById(id: number) : Observable<Cliente>{
     return this.http.get<any>(`${this.apiUrl}/${id}`);
   }

  atualizar( cliente: Cliente) : Observable<any>{
    return this.http.put<Cliente>(`${this.apiUrl}/${cliente.id}`, cliente);
   }

  deletar( cliente: Cliente) : Observable<any>{
    console.log('Aquiiii'+cliente);
    return this.http.delete<any>(`${this.apiUrl}/${cliente.id}`);
   }

/*
  getClientes() : Cliente[]{
    let cliente = new Cliente();
    cliente.id = 1;
    cliente.nome = 'Fulano';
    cliente.dataCadastro = '18/06/2021';
    cliente.cpf = '03823493186'
    return[cliente];
  } */
}
