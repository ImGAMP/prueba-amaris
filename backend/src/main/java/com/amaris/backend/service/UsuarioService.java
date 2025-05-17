package com.amaris.backend.service;

import com.amaris.backend.model.Usuario;
import com.amaris.backend.repository.UsuarioRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepositoryImpl usuarioRepository;

    public UsuarioService(UsuarioRepositoryImpl usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Long id){
        return usuarioRepository.findById(id);
    }

    public Usuario save(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
}
