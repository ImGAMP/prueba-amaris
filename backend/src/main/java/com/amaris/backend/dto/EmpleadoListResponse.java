package com.amaris.backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class EmpleadoListResponse {
    private String status;
    private List<EmpleadoResponse> data;
}