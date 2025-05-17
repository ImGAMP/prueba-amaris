package com.amaris.backend.service;

import com.amaris.backend.model.Usuario;
import com.amaris.backend.repository.UsuarioRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepositoryImpl usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() throws java.sql.SQLException {
        Usuario u1 = new Usuario();
        Usuario u2 = new Usuario();
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(u1, u2));
        List<Usuario> result = usuarioService.findAll();
        assertEquals(2, result.size());
    }

    @Test
    void testFindById() throws java.sql.SQLException {
        Usuario u = new Usuario();
        u.setId(1L);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(u));
        Optional<Usuario> found = usuarioService.findById(1L);
        assertTrue(found.isPresent());
        assertEquals(1L, found.get().getId());
    }

    @Test
    void testSave() throws java.sql.SQLException {
        Usuario u = new Usuario();
        u.setUsername("test");
        when(usuarioRepository.save(any())).thenReturn(u);
        Usuario saved = usuarioService.save(u);
        assertEquals("test", saved.getUsername());
    }

    @Test
    void testDeleteById() throws java.sql.SQLException {
        usuarioService.deleteById(1L);
        verify(usuarioRepository, times(1)).deleteById(1L);
    }
}
