package com.amaris.backend.dto;

import lombok.Data;

@Data
public class EmpleadoResponse {
    private int id;
    private String employee_name;
    private int employee_salary;
    private int employee_age;
    private String profile_image;
    private int salary_anual;
}
