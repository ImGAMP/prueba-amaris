package com.amaris.backend.config;

import com.amaris.backend.model.Usuario;
import com.amaris.backend.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitAdminUser {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;

    public InitAdminUser(UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
    }

    @PostConstruct
    public void initAdmin() {
        System.out.println("🛠 Ejecutando InitAdminUser...");
        try {
            if (usuarioRepository.findByUsername("admin").isEmpty()) {
                System.out.println("🟢 No existe admin, creando...");
                Usuario u = new Usuario();
                u.setUsername("admin");
                u.setPassword(encoder.encode("1234"));
                u.setEnabled(true);
                usuarioRepository.save(u);
                System.out.println("✅ Usuario admin creado");
            } else {
                System.out.println("🟡 Usuario admin ya existe");
            }
        } catch (Exception e) {
            System.out.println("❌ Error en InitAdminUser:");
            e.printStackTrace();
        }
        System.out.println("🔍 Usuarios en BD: " + usuarioRepository.findAll().size());
    }

}
