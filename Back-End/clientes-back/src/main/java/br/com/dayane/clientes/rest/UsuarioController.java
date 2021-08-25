package br.com.dayane.clientes.rest;

import br.com.dayane.clientes.model.entity.Usuario;
import br.com.dayane.clientes.model.repository.UsuarioRepository;
import br.com.dayane.clientes.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor // cria um construtor com todos os argumentos obrigatórios no caso todos anotados com 'final'
public class UsuarioController {


    private final UsuarioService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody @Valid Usuario usuario){
        try {
            service.salvar(usuario);
        }catch (Exception e){
            throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, e.getMessage() );
        }

    }
}
