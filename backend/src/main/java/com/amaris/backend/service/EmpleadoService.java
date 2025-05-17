package com.amaris.backend.service;

import com.amaris.backend.dto.EmpleadoResponse;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoService {

    public void calcularSalarioAnual(EmpleadoResponse emp) {
        if (emp != null) {
            emp.setSalary_anual(emp.getEmployee_salary() * 12);
        }
    }
}
