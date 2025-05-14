package com.amaris.backend.client;

import com.amaris.backend.dto.EmpleadoResponse;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class EmpleadoClientTest {

    @Test
    void testGetAllEmpleadosReturnsList() {
        EmpleadoClient client = new EmpleadoClient();
        assertDoesNotThrow(() -> {
            var list = client.getAllEmpleados();
            assertNotNull(list);
            assertTrue(list.size() > 0);
        });
    }

    @Test
    void testGetEmpleadoByIdReturnsData() {
        EmpleadoClient client = new EmpleadoClient();
        EmpleadoResponse emp = client.getEmpleadoById(1);
        assertNotNull(emp);
        assertEquals(1, emp.getId());
    }
}
