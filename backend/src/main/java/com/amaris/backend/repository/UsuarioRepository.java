package com.amaris.backend.repository;

import com.amaris.backend.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {
    List<Usuario> findAll();
    Optional<Usuario> findById(Long id);
    Optional<Usuario> findByUsername(String username);
    Usuario save(Usuario usuario);
    void deleteById(Long id);
}
