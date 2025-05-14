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
        // Secreto de al menos 256 bits (32 bytes). Puedes reemplazar por cualquier string seguro.
        ReflectionTestUtils.setField(jwtUtils, "secret", "mySuperSecureSecretKeyThatIs32Bytes!!");
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
        assertFalse(jwtUtils.validateToken("this.is.an.invalid.token"));
    }
}