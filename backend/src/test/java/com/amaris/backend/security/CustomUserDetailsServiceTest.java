package com.amaris.backend.security;

import com.amaris.backend.model.Usuario;
import com.amaris.backend.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private CustomUserDetailsService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsernameExists() {
        Usuario u = new Usuario();
        u.setUsername("admin");
        u.setPassword("secret");
        when(usuarioRepository.findByUsername("admin")).thenReturn(Optional.of(u));

        UserDetails user = service.loadUserByUsername("admin");
        assertEquals("admin", user.getUsername());
    }

    @Test
    void testLoadUserByUsernameNotFound() {
        when(usuarioRepository.findByUsername("ghost")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("ghost"));
    }
}
