package com.amaris.backend.service;

import com.amaris.backend.dto.EmpleadoResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmpleadoServiceTest {

    @Test
    void testCalcularSalarioAnual() {
        EmpleadoResponse emp = new EmpleadoResponse();
        emp.setEmployee_salary(5000);
        EmpleadoService service = new EmpleadoService();

        service.calcularSalarioAnual(emp);

        assertEquals(60000, emp.getSalary_anual());
    }

    @Test
    void testCalcularSalarioAnualWhenZero() {
        EmpleadoResponse emp = new EmpleadoResponse();
        emp.setEmployee_salary(0);
        EmpleadoService service = new EmpleadoService();

        service.calcularSalarioAnual(emp);

        assertEquals(0, emp.getSalary_anual());
    }

    @Test
    void testCalcularSalarioAnualWhenNegative() {
        EmpleadoResponse emp = new EmpleadoResponse();
        emp.setEmployee_salary(-1000);
        EmpleadoService service = new EmpleadoService();

        service.calcularSalarioAnual(emp);

        assertEquals(-12000, emp.getSalary_anual());
    }
}