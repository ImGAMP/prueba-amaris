package com.amaris.backend.client;

import com.amaris.backend.dto.EmpleadoResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.Map;

@Component
public class EmpleadoClient {

    private final RestTemplate restTemplate;

    public EmpleadoClient() {
        this.restTemplate = new RestTemplate();
    }

    public List<EmpleadoResponse> getAllEmpleados() {
        ResponseEntity<Map<String, List<EmpleadoResponse>>> response = restTemplate.exchange(
                "http://dummy.restapiexample.com/api/v1/employees",
                org.springframework.http.HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody().get("data");
    }

    public EmpleadoResponse getEmpleadoById(int id) {
        return new RestTemplate().getForObject("http://dummy.restapiexample.com/api/v1/employee/" + id, EmpleadoWrapper.class).getData();
    }

    static class EmpleadoWrapper {
        private EmpleadoResponse data;
        public EmpleadoResponse getData() { return data; }
        public void setData(EmpleadoResponse data) { this.data = data; }
    }
}
