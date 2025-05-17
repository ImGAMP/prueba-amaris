package com.amaris.backend.config;

import com.amaris.backend.security.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecurityAndJwtTests {

    @Mock private JwtFilter jwtFilter;
    @Mock private JwtUtils jwtUtils;
    @Mock private CustomUserDetailsService userDetailsService;
    @Mock private AuthenticationConfiguration authenticationConfiguration;
    @Mock private FilterChain filterChain;

    @InjectMocks private SecurityConfig securityConfig;

    private JwtFilter filter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        filter = new JwtFilter(jwtUtils);
        SecurityContextHolder.clearContext();
    }

    @Test
    void testAuthenticationManagerBean() throws Exception {
        AuthenticationManager mockManager = mock(AuthenticationManager.class);
        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(mockManager);
        AuthenticationManager result = securityConfig.authenticationManager(authenticationConfiguration);
        assertNotNull(result);
        assertEquals(mockManager, result);
    }

    @Test
    void testCorsFilterConfiguration() {
        assertNotNull(securityConfig.corsFilter());
    }

    @Test
    void testPasswordEncoder() {
        assertNotNull(securityConfig.passwordEncoder());
        assertTrue(securityConfig.passwordEncoder().matches("test", securityConfig.passwordEncoder().encode("test")));
    }

    @Test
    void testShouldNotFilterTrueForAuthRoute() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/auth/login");
        assertTrue(filter.shouldNotFilter(request));
    }

    @Test
    void testShouldNotFilterFalseForProtectedRoute() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/usuarios");
        assertFalse(filter.shouldNotFilter(request));
    }

    @Test
    void testDoFilterInternalWithValidToken() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/secure");
        request.addHeader("Authorization", "Bearer valid.jwt.token");

        MockHttpServletResponse response = new MockHttpServletResponse();

        when(jwtUtils.extractUsername("valid.jwt.token")).thenReturn("testuser");
        when(jwtUtils.validateToken("valid.jwt.token")).thenReturn(true);

        filter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternalWithInvalidToken() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/secure");
        request.addHeader("Authorization", "Bearer invalid.token");

        MockHttpServletResponse response = new MockHttpServletResponse();

        when(jwtUtils.extractUsername("invalid.token")).thenReturn("user");
        when(jwtUtils.validateToken("invalid.token")).thenReturn(false);

        filter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternalWithNoAuthorizationHeader() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/secure");

        MockHttpServletResponse response = new MockHttpServletResponse();

        filter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response);
    }
}
