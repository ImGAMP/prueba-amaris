package com.amaris.backend.config;

import com.amaris.backend.model.Usuario;
import com.amaris.backend.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class InitAdminUser {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner initAdmin(UsuarioRepository usuarioRepository, BCryptPasswordEncoder encoder) {
        return args -> {
            String username = "admin";
            if (usuarioRepository.findByUsername(username).isEmpty()) {
                Usuario usuario = new Usuario();
                usuario.setUsername(username);
                usuario.setPassword(encoder.encode("admin123"));
                usuario.setEnabled(true);
                usuarioRepository.save(usuario);
                System.out.println("Usuario admin creado con contraseña: admin123");
            }
        };
    }
}
