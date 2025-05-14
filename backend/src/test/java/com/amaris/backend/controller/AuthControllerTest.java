package com.amaris.backend.controller;

import com.amaris.backend.config.JwtUtils;
import com.amaris.backend.dto.LoginRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private Authentication authentication;

    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authController = new AuthController(authenticationManager, jwtUtils);
    }

    @Test
    void testLoginSuccess() {
        LoginRequest login = new LoginRequest();
        login.setUsername("admin");
        login.setPassword("1234");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtUtils.generateToken("admin")).thenReturn("token.jwt");

        ResponseEntity<?> response = authController.login(login);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testLoginFailure() {
        LoginRequest login = new LoginRequest();
        login.setUsername("admin");
        login.setPassword("wrong");

        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Bad credentials"));

        assertThrows(BadCredentialsException.class, () -> authController.login(login));
    }

    @Test
    void testMe() {
        SecurityContext context = mock(SecurityContext.class);
        User user = new User("admin", "", Collections.emptyList());
        when(context.getAuthentication()).thenReturn(new UsernamePasswordAuthenticationToken(user, null));
        SecurityContextHolder.setContext(context);

        ResponseEntity<?> response = authController.me();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("admin", response.getBody());
    }
}
