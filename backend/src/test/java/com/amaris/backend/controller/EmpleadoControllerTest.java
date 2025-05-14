package com.amaris.backend.controller;

import com.amaris.backend.client.EmpleadoClient;
import com.amaris.backend.dto.EmpleadoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmpleadoControllerTest {

    @Mock
    private EmpleadoClient empleadoClient;

    @InjectMocks
    private EmpleadoController empleadoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        EmpleadoResponse e = new EmpleadoResponse();
        e.setId(1);
        e.setEmployee_name("Test Employee");
        when(empleadoClient.getAllEmpleados()).thenReturn(List.of(e));

        ResponseEntity<?> response = empleadoController.getAll();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetById() {
        EmpleadoResponse e = new EmpleadoResponse();
        e.setId(1);
        e.setEmployee_name("Test Employee");
        when(empleadoClient.getEmpleadoById(1)).thenReturn(e);

        ResponseEntity<?> response = empleadoController.getById(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetByIdNotFound() {
        when(empleadoClient.getEmpleadoById(99)).thenReturn(null);
        ResponseEntity<?> response = empleadoController.getById(99);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetAllHandlesEmptyList() {
        when(empleadoClient.getAllEmpleados()).thenReturn(List.of());
        ResponseEntity<?> response = empleadoController.getAll();
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(((List<?>) ((java.util.Map<?, ?>) response.getBody()).get("data")).isEmpty());
    }

    @Test
    void testGetByIdThrowsException() {
        when(empleadoClient.getEmpleadoById(2)).thenThrow(new RuntimeException("API error"));
        assertThrows(RuntimeException.class, () -> empleadoController.getById(2));
    }

    @Test
    void testGetAllThrowsException() {
        when(empleadoClient.getAllEmpleados()).thenThrow(new RuntimeException("API down"));
        assertThrows(RuntimeException.class, () -> empleadoController.getAll());
    }
}
