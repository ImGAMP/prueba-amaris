package com.amaris.backend.controller;

import com.amaris.backend.model.Usuario;
import com.amaris.backend.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private UsuarioController usuarioController;

    private Usuario sample;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sample = new Usuario();
        sample.setId(1L);
        sample.setUsername("admin");
        sample.setPassword("1234");
        sample.setEnabled(true);

        // inyección manual porque el encoder es @Autowired y privado
        setField(usuarioController, "encoder", encoder);

        when(encoder.encode(anyString())).thenReturn("encoded");
    }

    @Test
    void testGetAll() {
        when(usuarioService.findAll()).thenReturn(List.of(sample));
        ResponseEntity<?> response = usuarioController.getAll();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetByIdFound() {
        when(usuarioService.findById(1L)).thenReturn(Optional.of(sample));
        ResponseEntity<?> response = usuarioController.getById(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetByIdNotFound() {
        when(usuarioService.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<?> response = usuarioController.getById(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreate() {
        when(usuarioService.save(any(Usuario.class))).thenReturn(sample);
        ResponseEntity<?> response = usuarioController.create(sample);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testUpdateFound() {
        Usuario modificado = new Usuario();
        modificado.setUsername("nuevo");
        modificado.setPassword("clave");

        when(usuarioService.findById(1L)).thenReturn(Optional.of(sample));
        when(usuarioService.save(any())).thenReturn(sample);

        ResponseEntity<?> response = usuarioController.update(1L, modificado);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testUpdateNotFound() {
        when(usuarioService.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<?> response = usuarioController.update(1L, sample);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDelete() {
        doNothing().when(usuarioService).deleteById(1L);
        ResponseEntity<?> response = usuarioController.delete(1L);
        assertEquals(204, response.getStatusCodeValue());
    }
}
