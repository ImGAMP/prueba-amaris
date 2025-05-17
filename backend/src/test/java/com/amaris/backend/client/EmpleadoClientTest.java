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

        // Simula respuesta del API al cargar empleados
        EmpleadoResponse mockEmpleado = new EmpleadoResponse();
        mockEmpleado.setId(1);
        mockEmpleado.setEmployee_name("Test Employee");

        EmpleadoListResponse wrapper = new EmpleadoListResponse();
        wrapper.setData(List.of(mockEmpleado));

        when(restTemplate.getForObject(anyString(), eq(EmpleadoListResponse.class))).thenReturn(wrapper);

        // Simula @PostConstruct
        empleadoClient.cargarEmpleados();
    }

    @Test
    void testGetAllEmpleadosReturnsListFromCache() {
        List<EmpleadoResponse> result = empleadoClient.getAllEmpleados();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Employee", result.get(0).getEmployee_name());
    }

    @Test
    void testGetEmpleadoByIdReturnsFromCache() {
        EmpleadoResponse result = empleadoClient.getEmpleadoById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Test Employee", result.getEmployee_name());
    }

    @Test
    void testGetEmpleadoByIdReturnsNullIfNotCached() {
        EmpleadoResponse result = empleadoClient.getEmpleadoById(999);
        assertNull(result);
    }

    @Test
    void testGetAllEmpleadosEmptyIfApiFails() {
        // Simula llamada fallida
        when(restTemplate.getForObject(anyString(), eq(EmpleadoListResponse.class))).thenReturn(null);

        EmpleadoClient emptyClient = new EmpleadoClient(restTemplate);
        emptyClient.cargarEmpleados();

        List<EmpleadoResponse> result = emptyClient.getAllEmpleados();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
