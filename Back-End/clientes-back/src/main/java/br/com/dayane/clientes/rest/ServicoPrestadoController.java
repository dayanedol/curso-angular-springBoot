package br.com.dayane.clientes.rest;

import br.com.dayane.clientes.model.entity.Cliente;
import br.com.dayane.clientes.model.entity.ServicoPrestado;
import br.com.dayane.clientes.model.repository.ClienteRepository;
import br.com.dayane.clientes.model.repository.ServicoPrestadoRepository;
import br.com.dayane.clientes.rest.dto.ServicoPrestadoDTO;
import br.com.dayane.clientes.util.BigDecimalConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RestController
@RequestMapping("/api/servico-prestados")
@RequiredArgsConstructor

public class ServicoPrestadoController {

    private final ClienteRepository clienteRepository;
    private final ServicoPrestadoRepository servicoPrestadoRepository;
    private final BigDecimalConverter bigDecimalConverter;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServicoPrestado salvar(@RequestBody @Valid  ServicoPrestadoDTO dto){
        LocalDate data = LocalDate.parse(dto.getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Integer idCliente = dto.getIdCliente();

        Cliente cliente = clienteRepository
                .findById(idCliente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cliente inexistente"));

        ServicoPrestado servicoPrestado = new ServicoPrestado();
        servicoPrestado.setDescricao(dto.getDescricao());
        servicoPrestado.setData(data);
        servicoPrestado.setCliente(cliente);
        servicoPrestado.setValor( bigDecimalConverter.converter(dto.getPreco()) );
        return servicoPrestadoRepository.save(servicoPrestado);
    }

    @GetMapping
    public List<ServicoPrestado> pesquisar(
            @RequestParam(value = "nome", required = false, defaultValue = "") String nome,
            @RequestParam(value = "mes", required = false)  Integer mes
    ){
        return servicoPrestadoRepository.findByNomeClienteAndMes("%"+ nome + "%", mes);
    }

    //where cliente.nome like '%Ful%'

 /*   @PostMapping(path = "/teste")
    @ResponseStatus(HttpStatus.CREATED)
    public void teste(@RequestBody ExemploDto dto){
        System.out.println(dto.toString());

    }
}

class ExemploDto{
    public String nome;
    public String cpf;
    public int qtd;

    @Override
    public String toString() {
        return "Nome: " + nome + "\nCpf: " + cpf +"\nQtd: " + qtd;
    }*/
}
