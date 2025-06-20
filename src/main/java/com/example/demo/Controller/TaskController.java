package com.example.demo.Controller;

import com.example.demo.Modelo.Tarea;
import com.example.demo.Modelo.Usuario;
import com.example.demo.Service.TareaService;
import com.example.demo.Service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Tareas", description = "CRUD completo de tareas")
public class TaskController {
    private final TareaService tareaService;
    private final UsuarioService usuarioService;

    @Operation(summary = "Obtener todas las tareas activas")
    @GetMapping
    public ResponseEntity<List<Tarea>> obtenerTodasActivas() {
        return ResponseEntity.ok(tareaService.obtenerTodasActivas());
    }

    @Operation(summary = "Obtener tareas activas por email de usuario")
    @GetMapping("/usuario/{email}")
    public ResponseEntity<List<Tarea>> getTasks(@PathVariable String email) {
        Usuario usuario = usuarioService.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return ResponseEntity.ok(tareaService.obtenerTareas(usuario));
    }

    @Operation(summary = "Obtener tarea por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Tarea> obtenerPorId(@PathVariable Long id) {
        return tareaService.obtenerTareaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nueva tarea para un usuario")
    @PostMapping("/usuario/{email}")
    public ResponseEntity<Tarea> addTask(@PathVariable String email, @RequestBody TaskRequest request) {
        Usuario usuario = usuarioService.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return ResponseEntity.ok(tareaService.agregarTarea(request.getTitle(), usuario));
    }

    @Operation(summary = "Actualizar tarea existente")
    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizarTarea(@PathVariable Long id, @RequestBody TaskRequest request) {
        return tareaService.actualizarTarea(id, request.getTitle())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar tarea lógicamente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLogicamente(@PathVariable Long id) {
        return tareaService.eliminarTareaLogica(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Eliminar tarea físicamente")
    @DeleteMapping("/{id}/permanente")
    public ResponseEntity<Void> eliminarFisicamente(@PathVariable Long id) {
        tareaService.eliminarTareaFisica(id);
        return ResponseEntity.ok().build();
    }

    @Data
    static class TaskRequest {
        private String title;
    }
}