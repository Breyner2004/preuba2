package com.example.demo.Repository;
import com.example.demo.Modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRpository extends JpaRepository <Usuario, Long > {
    Optional<Usuario> findByEmail(String email);
}
