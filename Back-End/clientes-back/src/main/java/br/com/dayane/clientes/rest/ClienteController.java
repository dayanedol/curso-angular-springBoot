package br.com.dayane.clientes.rest;

import br.com.dayane.clientes.model.entity.Cliente;
import br.com.dayane.clientes.model.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")

public class ClienteController {

    private final ClienteRepository repository;

    @Autowired
    public ClienteController(ClienteRepository repository){
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente salvar(@RequestBody @Valid Cliente cliente){
        return repository.save(cliente);
    }

    @GetMapping("{id}")
    public Cliente obterPorId(@PathVariable("id") Integer id){
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cliente não encontrado!"));
    }

    @GetMapping()
    public List<Cliente> obterTodosClientes(){
        return repository.findAll();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarClientePorId(@PathVariable("id") Integer id){
        //repository.deleteById(id); Pode ser feito assim mas por boas práticas é melhor buscar primeiro e saber se existe o registro
        repository.findById(id).map( cliente -> {
            repository.delete(cliente);
            return Void.TYPE;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cliente não encontrado!"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void Cliente (@PathVariable ("id") Integer id, @RequestBody @Valid  Cliente clienteatualizado){
        repository.findById(id).map( cliente -> {
            cliente.setId(cliente.getId());
            cliente.setNome(clienteatualizado.getNome());
            cliente.setCpf(clienteatualizado.getCpf());
            return repository.save(cliente);

        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cliente não encontrado!"));

    }

}
