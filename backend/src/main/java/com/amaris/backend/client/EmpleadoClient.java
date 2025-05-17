package com.amaris.backend.client;

import com.amaris.backend.dto.EmpleadoListResponse;
import com.amaris.backend.dto.EmpleadoResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;
import java.util.*;

@Component
public class EmpleadoClient {

    private final RestTemplate restTemplate;

    // Cache local en memoria
    private final Map<Integer, EmpleadoResponse> empleadoMap = new HashMap<>();

    public EmpleadoClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    public void cargarEmpleados() {
        try {
            EmpleadoListResponse response = restTemplate.getForObject(
                    "http://dummy.restapiexample.com/api/v1/employees",
                    EmpleadoListResponse.class
            );
            if (response != null && response.getData() != null) {
                for (EmpleadoResponse emp : response.getData()) {
                    empleadoMap.put(emp.getId(), emp);
                }
            }
        } catch (Exception ex) {
            System.err.println("Error cargando empleados: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public List<EmpleadoResponse> getAllEmpleados() {
        return new ArrayList<>(empleadoMap.values());
    }

    public EmpleadoResponse getEmpleadoById(int id) {
        return empleadoMap.get(id);
    }
}
