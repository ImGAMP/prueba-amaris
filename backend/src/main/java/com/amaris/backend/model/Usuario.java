package com.amaris.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
public class Usuario {
    private Long id;
    private String username;
    private String password;
    private Boolean enabled;
}

