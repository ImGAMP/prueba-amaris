package com.amaris.backend.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilsTest {

    private JwtUtils jwtUtils;

    @BeforeEach
    void setUp() {
        jwtUtils = new JwtUtils();
        ReflectionTestUtils.setField(jwtUtils, "secret", "mySecretKeyJWT-am@ris2025");
        ReflectionTestUtils.setField(jwtUtils, "expiration", 3600000L);
    }

    @Test
    void testGenerateAndValidateToken() {
        String token = jwtUtils.generateToken("admin");
        assertNotNull(token);
        assertTrue(jwtUtils.validateToken(token));
        assertEquals("admin", jwtUtils.extractUsername(token));
    }

    @Test
    void testInvalidToken() {
        assertFalse(jwtUtils.validateToken("invalid.token.value"));
    }
}