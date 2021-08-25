package br.com.dayane.clientes;

import br.com.dayane.clientes.model.entity.Cliente;
import br.com.dayane.clientes.model.repository.ClienteRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ClienteApplication {
    @Bean
    public CommandLineRunner run(@Autowired ClienteRepository repository){
        return args -> {
            Cliente cliente = Cliente.builder().cpf("03823493183").nome("Day de Oliveira").build();
            repository.save(cliente);
        };
    }
    public static void main(String[] args){
        SpringApplication.run(ClienteApplication.class, args);

    }
}

