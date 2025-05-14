package com.amaris.backend.client;

import com.amaris.backend.dto.EmpleadoListResponse;
import com.amaris.backend.dto.EmpleadoResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class EmpleadoClient {

    private final RestTemplate restTemplate;

    public EmpleadoClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<EmpleadoResponse> getAllEmpleados() {
        EmpleadoListResponse response = restTemplate.getForObject(
                "http://dummy.restapiexample.com/api/v1/employees",
                EmpleadoListResponse.class
        );
        return response != null ? response.getData() : List.of();
    }

    public EmpleadoResponse getEmpleadoById(int id) {
        EmpleadoWrapper response = restTemplate.getForObject(
                "http://dummy.restapiexample.com/api/v1/employee/" + id,
                EmpleadoWrapper.class
        );
        return response != null ? response.getData() : null;
    }

    static class EmpleadoWrapper {
        private EmpleadoResponse data;
        public EmpleadoResponse getData() { return data; }
        public void setData(EmpleadoResponse data) { this.data = data; }
    }
}