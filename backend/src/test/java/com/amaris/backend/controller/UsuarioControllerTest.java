package com.amaris.backend.controller;

import com.amaris.backend.model.Usuario;
import com.amaris.backend.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetByIdFound() {
        Usuario u = new Usuario();
        u.setId(1L);
        u.setUsername("test");
        u.setEnabled(true);
        when(usuarioService.findById(1L)).thenReturn(Optional.of(u));

        ResponseEntity<?> response = usuarioController.getById(1L);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testGetByIdNotFound() {
        when(usuarioService.findById(99L)).thenReturn(Optional.empty());
        ResponseEntity<?> response = usuarioController.getById(99L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreateUser() {
        Usuario u = new Usuario();
        u.setId(1L);
        u.setUsername("nuevo");
        u.setEnabled(true);

        when(usuarioService.save(any())).thenReturn(u);
        ResponseEntity<?> response = usuarioController.create(u);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testDeleteUser() {
        doNothing().when(usuarioService).deleteById(1L);
        ResponseEntity<?> response = usuarioController.delete(1L);
        assertEquals(204, response.getStatusCodeValue());
    }
}
