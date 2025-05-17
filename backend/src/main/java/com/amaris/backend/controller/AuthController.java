package com.amaris.backend.controller;

import com.amaris.backend.config.JwtUtils;
import com.amaris.backend.dto.AuthResponse;
import com.amaris.backend.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        System.out.println(">>> LoginController - login() fue llamado");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        String token = jwtUtils.generateToken(request.getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Principal principal) {
        return ResponseEntity.ok().body(principal.getName());
    }
}
