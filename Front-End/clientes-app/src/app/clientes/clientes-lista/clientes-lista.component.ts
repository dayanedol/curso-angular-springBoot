import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'
import { ClientesService } from '../../clientes.service';
import { Cliente } from '../clientes';
import * as XLSX from 'xlsx'; 

@Component({
  selector: 'app-clientes-lista',
  templateUrl: './clientes-lista.component.html',
  styleUrls: ['./clientes-lista.component.css']
})
export class ClientesListaComponent implements OnInit {

  clientes: Cliente[] = [];
  //clienteSelecionado : Cliente = new Cliente();
  clienteSelecionado : Cliente;
  mensagemSucesso: String;
  mensagemErro: String;
  

  constructor(private service: ClientesService, private router: Router) { 
    
  }

  ngOnInit(): void {
    //this.clientes =  this.service.getClientes();
    this.service.getClientes().subscribe(res => {
      this.clientes= res;
    }, errorResponse =>{
      console.error("erro na requisição");
   
    })
  }

  novoCadastro(){
    this.router.navigate(['/clientes/form'])
  }

  prepararDelecao(cliente: Cliente){
    this.clienteSelecionado = cliente;
  }

  deletarCliente(){
    this.service.deletar(this.clienteSelecionado)
    .subscribe(
      response => {
        this.mensagemSucesso = 'Cliente deletado com sucesso!';
        this.ngOnInit();
      },
      erro => this.mensagemErro = 'Ocorreu um erro ao deletar o Cliente!'

    )
  }


  exportarExcel(){
    //if(true) return true; exemplo de quando uma função faz somente uma instrução não é necessario abrir seu escopo {}
    const ws: XLSX.WorkSheet =XLSX.utils.json_to_sheet(this.clientes);

    /* generate workbook and add the worksheet */
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');

    /* save to file */
    XLSX.writeFile(wb, "planilhaClientes.xlsx");
  }

}
