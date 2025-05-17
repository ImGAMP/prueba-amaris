package com.amaris.backend.repository;

import com.amaris.backend.model.Usuario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final JdbcTemplate jdbcTemplate;

    public UsuarioRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Usuario> rowMapper = (rs, rowNum) -> {
        Usuario u = new Usuario();
        u.setId(rs.getLong("id"));
        u.setUsername(rs.getString("username"));
        u.setPassword(rs.getString("password"));
        u.setEnabled(rs.getBoolean("enabled"));
        return u;
    };

    @Override
    public List<Usuario> findAll() {
        return jdbcTemplate.query("SELECT * FROM usuario", rowMapper);
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM usuario WHERE id = ?", rowMapper, id)
                .stream().findFirst();
    }

    @Override
    public Optional<Usuario> findByUsername(String username) {
        return jdbcTemplate.query("SELECT * FROM usuario WHERE username = ?", rowMapper, username)
                .stream().findFirst();
    }

    @Override
    public Usuario save(Usuario usuario) {
        if (usuario.getId() == null) {
            jdbcTemplate.update(
                "INSERT INTO usuario (username, password, enabled) VALUES (?, ?, ?)",
                usuario.getUsername(), usuario.getPassword(), usuario.getEnabled());
        } else {
            jdbcTemplate.update(
                "UPDATE usuario SET username = ?, password = ?, enabled = ? WHERE id = ?",
                usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), usuario.getId());
        }
        return usuario;
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM usuario WHERE id = ?", id);
    }
}
