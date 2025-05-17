package com.amaris.backend.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class PreflightController {

    // Esto acepta explícitamente solicitudes OPTIONS en /api/auth/login
    @RequestMapping(value = "/login", method = RequestMethod.OPTIONS)
    public void handlePreflight() {
        // No hace nada, solo permite OPTIONS
    }
}
