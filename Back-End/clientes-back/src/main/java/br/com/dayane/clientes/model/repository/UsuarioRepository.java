package br.com.dayane.clientes.model.repository;

import br.com.dayane.clientes.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {

    Optional<Usuario> findByUsername(String username);

    //select na tabela filtrando pelo username de usuarios fazendo um count para saber se é maior que zero
    boolean existsByUsername(String username);
}
