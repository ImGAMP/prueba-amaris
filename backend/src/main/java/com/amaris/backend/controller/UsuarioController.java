package com.amaris.backend.controller;

import com.amaris.backend.dto.UsuarioResponse;
import com.amaris.backend.model.Usuario;
import com.amaris.backend.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder encoder;
    
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Usuario> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(jsonApiList(usuarios));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return usuarioService.findById(id)
                .map(usuario -> ResponseEntity.ok(jsonApiOne(usuario)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Usuario usuario) {
        Usuario saved = usuarioService.save(usuario);
        return ResponseEntity.ok(jsonApiOne(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Usuario updated) {
        return usuarioService.findById(id)
                .map(u -> {
                    u.setUsername(updated.getUsername());
                    u.setPassword(encoder.encode(updated.getPassword()));
                    return ResponseEntity.ok(jsonApiOne(usuarioService.save(u)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private Map<String, Object> jsonApiOne(Usuario usuario) {
        UsuarioResponse dto = new UsuarioResponse(usuario.getId(), usuario.getUsername(), usuario.getEnabled());

        Map<String, Object> data = new HashMap<>();
        data.put("type", "usuarios");
        data.put("id", dto.getId());

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("username", dto.getUsername());
        attributes.put("enabled", dto.isEnabled());
        data.put("attributes", attributes);

        Map<String, Object> wrapper = new HashMap<>();
        wrapper.put("data", data);
        return wrapper;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> jsonApiList(List<Usuario> usuarios) {
        List<Map<String, Object>> dataList = usuarios.stream()
                .map(this::jsonApiOne)
                .map(m -> (Map<String, Object>) m.get("data"))
                .toList();

        Map<String, Object> wrapper = new HashMap<>();
        wrapper.put("data", dataList);
        return wrapper;
    }
}

