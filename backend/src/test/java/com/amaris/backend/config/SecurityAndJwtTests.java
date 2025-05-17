package com.amaris.backend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecurityAndJwtTests {

    // Para SecurityConfig
    @Mock
    private JwtFilter jwtFilter;

    @Mock
    private AuthenticationConfiguration authenticationConfiguration;

    @InjectMocks
    private SecurityConfig securityConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSecurityFilterChainInitialization() throws Exception {
        SecurityFilterChain chain = securityConfig.securityFilterChain(mock(org.springframework.security.config.annotation.web.builders.HttpSecurity.class));
        assertNotNull(chain);
    }


    @Test
    void testAuthenticationManager() throws Exception {
        AuthenticationManager mockManager = mock(AuthenticationManager.class);
        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(mockManager);
        assertEquals(mockManager, securityConfig.authenticationManager(authenticationConfiguration));
    }

    // Para JwtFilter
    @Mock
    private JwtUtils jwtUtils;

    private JwtFilter filter;

    @BeforeEach
    void setUpFilter() {
        filter = new JwtFilter(jwtUtils);
    }

    @Test
    void testDoFilterInternalValidToken() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        String token = "Bearer valid.jwt.token";

        when(request.getHeader("Authorization")).thenReturn(token);
        when(jwtUtils.extractUsername(anyString())).thenReturn("testuser");
        when(jwtUtils.validateToken(anyString())).thenReturn(true);

        filter.doFilterInternal(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testDoFilterInternalNoToken() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn(null);

        filter.doFilterInternal(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
    }
}