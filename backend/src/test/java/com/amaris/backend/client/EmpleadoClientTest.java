package com.amaris.backend.client;

import com.amaris.backend.dto.EmpleadoListResponse;
import com.amaris.backend.dto.EmpleadoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class EmpleadoClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private EmpleadoClient empleadoClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEmpleadosReturnsList() {
        EmpleadoResponse mockEmpleado = new EmpleadoResponse();
        mockEmpleado.setId(1);
        mockEmpleado.setEmployee_name("Test Employee");

        EmpleadoListResponse wrapper = new EmpleadoListResponse();
        wrapper.setData(List.of(mockEmpleado));

        when(restTemplate.getForObject(anyString(), eq(EmpleadoListResponse.class))).thenReturn(wrapper);

        List<EmpleadoResponse> result = empleadoClient.getAllEmpleados();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Employee", result.get(0).getEmployee_name());
    }

    @Test
    void testGetEmpleadoByIdReturnsData() {
        EmpleadoResponse emp = new EmpleadoResponse();
        emp.setId(1);
        emp.setEmployee_name("John Doe");

        EmpleadoClient.EmpleadoWrapper wrapper = new EmpleadoClient.EmpleadoWrapper();
        wrapper.setData(emp);

        when(restTemplate.getForObject(contains("/employee/"), eq(EmpleadoClient.EmpleadoWrapper.class))).thenReturn(wrapper);

        EmpleadoResponse result = empleadoClient.getEmpleadoById(1);
        assertNotNull(result);
        assertEquals("John Doe", result.getEmployee_name());
    }

    @Test
    void testGetAllEmpleadosReturnsEmptyWhenNull() {
        when(restTemplate.getForObject(anyString(), eq(EmpleadoListResponse.class))).thenReturn(null);
        List<EmpleadoResponse> result = empleadoClient.getAllEmpleados();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetEmpleadoByIdReturnsNullWhenEmpty() {
        when(restTemplate.getForObject(contains("/employee/"), eq(EmpleadoClient.EmpleadoWrapper.class))).thenReturn(null);
        EmpleadoResponse result = empleadoClient.getEmpleadoById(999);
        assertNull(result);
    }
}
