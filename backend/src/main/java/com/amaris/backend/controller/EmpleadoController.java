package com.amaris.backend.controller;

import com.amaris.backend.client.EmpleadoClient;
import com.amaris.backend.dto.EmpleadoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final EmpleadoClient empleadoClient;

    public EmpleadoController(EmpleadoClient empleadoClient) {
        this.empleadoClient = empleadoClient;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<EmpleadoResponse> empleados = empleadoClient.getAllEmpleados();
        List<Map<String, Object>> dataList = empleados.stream().map(this::toJsonApi).toList();
        Map<String, Object> wrapper = new HashMap<>();
        wrapper.put("data", dataList);
        return ResponseEntity.ok(wrapper);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        EmpleadoResponse emp = empleadoClient.getEmpleadoById(id);
        if (emp == null) return ResponseEntity.notFound().build();
        Map<String, Object> wrapper = new HashMap<>();
        wrapper.put("data", toJsonApi(emp));
        return ResponseEntity.ok(wrapper);
    }

    private Map<String, Object> toJsonApi(EmpleadoResponse emp) {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "empleados");
        data.put("id", emp.getId());

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("employee_name", emp.getEmployee_name());
        attributes.put("employee_salary", emp.getEmployee_salary());
        attributes.put("employee_age", emp.getEmployee_age());
        attributes.put("profile_image", emp.getProfile_image());

        data.put("attributes", attributes);
        return data;
    }
}