package com.amaris.backend.controller;

import com.amaris.backend.client.EmpleadoClient;
import com.amaris.backend.dto.EmpleadoResponse;
import com.amaris.backend.service.EmpleadoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmpleadoControllerTest {

    @Mock
    private EmpleadoClient empleadoClient;

    @Mock
    private EmpleadoService empleadoService;

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
        e.setEmployee_salary(1000);
        e.setEmployee_age(30);

        when(empleadoClient.getAllEmpleados()).thenReturn(List.of(e));
        doAnswer(invocation -> {
            EmpleadoResponse emp = invocation.getArgument(0);
            emp.setSalary_anual(emp.getEmployee_salary() * 12);
            return null;
        }).when(empleadoService).calcularSalarioAnual(any());

        ResponseEntity<?> response = empleadoController.getAll();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetById() {
        EmpleadoResponse e = new EmpleadoResponse();
        e.setId(1);
        e.setEmployee_name("Test Employee");
        e.setEmployee_salary(2000);
        e.setEmployee_age(28);

        when(empleadoClient.getEmpleadoById(1)).thenReturn(e);
        doAnswer(invocation -> {
            EmpleadoResponse emp = invocation.getArgument(0);
            emp.setSalary_anual(emp.getEmployee_salary() * 12);
            return null;
        }).when(empleadoService).calcularSalarioAnual(any());

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
