package com.amaris.backend.repository;

import com.amaris.backend.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioRepositoryImplTest {

    private JdbcTemplate jdbcTemplate;
    private UsuarioRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        jdbcTemplate = mock(JdbcTemplate.class);
        repository = new UsuarioRepositoryImpl(jdbcTemplate);
    }

    @Test
    void testFindAll() {
        Usuario u = new Usuario();
        u.setId(1L);
        u.setUsername("admin");
        u.setPassword("1234");
        u.setEnabled(true);

        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenReturn(List.of(u));

        List<Usuario> result = repository.findAll();
        assertEquals(1, result.size());
        assertEquals("admin", result.get(0).getUsername());
    }

    @Test
    void testFindById() {
        Usuario u = new Usuario();
        u.setId(1L);
        u.setUsername("admin");

        when(jdbcTemplate.query(eq("SELECT * FROM usuario WHERE id = ?"), any(RowMapper.class), eq(1L)))
                .thenReturn(List.of(u));

        Optional<Usuario> result = repository.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("admin", result.get().getUsername());
    }

    @Test
    void testFindByUsername() {
        Usuario u = new Usuario();
        u.setId(2L);
        u.setUsername("tester");

        when(jdbcTemplate.query(eq("SELECT * FROM usuario WHERE username = ?"), any(RowMapper.class), eq("tester")))
                .thenReturn(List.of(u));

        Optional<Usuario> result = repository.findByUsername("tester");
        assertTrue(result.isPresent());
        assertEquals(2L, result.get().getId());
    }

    @Test
    void testSaveNewUser() {
        Usuario u = new Usuario();
        u.setUsername("nuevo");
        u.setPassword("clave");
        u.setEnabled(true);

        repository.save(u);

        verify(jdbcTemplate).update(
                eq("INSERT INTO usuario (username, password, enabled) VALUES (?, ?, ?)"),
                eq("nuevo"), eq("clave"), eq(true));
    }

    @Test
    void testSaveExistingUser() {
        Usuario u = new Usuario();
        u.setId(10L);
        u.setUsername("actualizado");
        u.setPassword("1234");
        u.setEnabled(false);

        repository.save(u);

        verify(jdbcTemplate).update(
                eq("UPDATE usuario SET username = ?, password = ?, enabled = ? WHERE id = ?"),
                eq("actualizado"), eq("1234"), eq(false), eq(10L));
    }

    @Test
    void testDeleteById() {
        repository.deleteById(3L);

        verify(jdbcTemplate).update("DELETE FROM usuario WHERE id = ?", 3L);
    }
}
