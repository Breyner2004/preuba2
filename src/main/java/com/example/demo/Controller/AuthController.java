package com.example.demo.Controller;

import com.example.demo.Modelo.Usuario;
import com.example.demo.Service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación y Usuarios", description = "CRUD completo de usuarios y autenticación")
public class AuthController {
    private final UsuarioService usuarioService;

    @Operation(summary = "Obtener todos los usuarios")
    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> obtenerTodos() {
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }

    @Operation(summary = "Obtener usuario por ID")
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable int id) {
        return usuarioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Registrar nuevo usuario")
    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(
            usuarioService.registrar(request.getEmail(), request.getPassword())
        );
    }

    @Operation(summary = "Actualizar usuario existente")
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable int id, @RequestBody AuthRequest request) {
        return usuarioService.actualizarUsuario(id, request.getEmail(), request.getPassword())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar usuario")
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        return usuarioService.eliminarUsuario(id) 
                ? ResponseEntity.ok().build() 
                : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Iniciar sesión")
    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody AuthRequest request) {
        return usuarioService.login(request.getEmail(), request.getPassword())
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar usuario por email")
    @GetMapping("/buscar")
    public ResponseEntity<Usuario> buscarPorEmail(@RequestParam String email) {
        return usuarioService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Data
    static class AuthRequest {
        private String email;
        private String password;
    }
}