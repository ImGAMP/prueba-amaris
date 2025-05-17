package com.amaris.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class UsuarioResponse {
    private Long id;
    private String username;
    private boolean enabled;
}
