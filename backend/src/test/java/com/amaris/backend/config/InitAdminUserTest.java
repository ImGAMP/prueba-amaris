package com.amaris.backend.config;

import com.amaris.backend.model.Usuario;
import com.amaris.backend.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class InitAdminUserTest {

    private UsuarioRepository usuarioRepository;
    private PasswordEncoder encoder;
    private InitAdminUser initAdminUser;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        encoder = mock(PasswordEncoder.class);
        initAdminUser = new InitAdminUser(usuarioRepository, encoder);
    }

    @Test
    void shouldCreateAdminUserIfNotExists() {
        when(usuarioRepository.findByUsername("admin")).thenReturn(Optional.empty());
        when(encoder.encode("1234")).thenReturn("encodedPassword");
        when(usuarioRepository.findAll()).thenReturn(List.of());

        initAdminUser.initAdmin();

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(captor.capture());

        Usuario saved = captor.getValue();
        assert "admin".equals(saved.getUsername());
        assert "encodedPassword".equals(saved.getPassword());
        assert Boolean.TRUE.equals(saved.getEnabled());
    }

    @Test
    void shouldNotCreateAdminUserIfAlreadyExists() {
        Usuario existing = new Usuario();
        existing.setUsername("admin");

        when(usuarioRepository.findByUsername("admin")).thenReturn(Optional.of(existing));
        when(usuarioRepository.findAll()).thenReturn(Collections.singletonList(existing));

        initAdminUser.initAdmin();

        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void shouldHandleExceptionGracefully() {
        when(usuarioRepository.findByUsername("admin")).thenThrow(new RuntimeException("DB error"));
        initAdminUser.initAdmin();
        // no throw = pass
    }
}
