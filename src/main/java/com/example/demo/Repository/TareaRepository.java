package com.example.demo.Repository;
import com.example.demo.Modelo.Tarea;
import com.example.demo.Modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TareaRepository extends JpaRepository<Tarea, Long> {
    List<Tarea> findByUsuarioAndActivoTrue(Usuario usuario);
    List<Tarea> findByActivoTrue();
}