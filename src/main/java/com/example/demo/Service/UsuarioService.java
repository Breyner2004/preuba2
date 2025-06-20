package com.example.demo.Service;
import com.example.demo.Modelo.Usuario;
import com.example.demo.Repository.UsuarioRpository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRpository usuarioRepository;

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerPorId(int id) {
        return usuarioRepository.findById((long) id);
    }

    public Usuario registrar(String email, String password) {
        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email ya registrado");
        }
        var usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(password);
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> actualizarUsuario(int id, String email, String password) {
        return usuarioRepository.findById((long) id).map(usuario -> {
            usuario.setEmail(email);
            if (password != null && !password.isEmpty()) {
                usuario.setPassword(password);
            }
            return usuarioRepository.save(usuario);
        });
    }

    public boolean eliminarUsuario(int id) {
        if (usuarioRepository.existsById((long) id)) {
            usuarioRepository.deleteById((long) id);
            return true;
        }
        return false;
    }

    public Optional<Usuario> login(String email, String password) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (usuario.isPresent() && usuario.get().getPassword().equals(password)) {
            return usuario;
        }
        return Optional.empty();
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}