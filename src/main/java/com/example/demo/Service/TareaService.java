package com.example.demo.Service;

import com.example.demo.Modelo.Tarea;
import com.example.demo.Modelo.Usuario;
import com.example.demo.Repository.TareaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TareaService {
    private final TareaRepository tareaRepository;

    public List<Tarea> obtenerTodasActivas() {
        return tareaRepository.findByActivoTrue();
    }

    public List<Tarea> obtenerTareas(Usuario usuario) {
        return tareaRepository.findByUsuarioAndActivoTrue(usuario);
    }

    public Optional<Tarea> obtenerTareaPorId(Long id) {
        return tareaRepository.findById(id)
                .filter(Tarea::isActivo);
    }

    public Tarea agregarTarea(String title, Usuario usuario) {
        var tarea = new Tarea();
        tarea.setTitle(title);
        tarea.setUsuario(usuario);
        return tareaRepository.save(tarea);
    }

    public Optional<Tarea> actualizarTarea(Long id, String title) {
        return tareaRepository.findById(id)
                .filter(Tarea::isActivo)
                .map(tarea -> {
                    tarea.setTitle(title);
                    tarea.setFechaActualizacion(LocalDateTime.now());
                    return tareaRepository.save(tarea);
                });
    }

    public boolean eliminarTareaLogica(Long id) {
        return tareaRepository.findById(id)
                .filter(Tarea::isActivo)
                .map(tarea -> {
                    tarea.setActivo(false);
                    tarea.setFechaActualizacion(LocalDateTime.now());
                    tareaRepository.save(tarea);
                    return true;
                })
                .orElse(false);
    }

    public void eliminarTareaFisica(Long id) {
        tareaRepository.deleteById(id);
    }
}