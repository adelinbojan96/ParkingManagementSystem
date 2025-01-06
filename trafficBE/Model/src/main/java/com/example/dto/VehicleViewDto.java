package com.example.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class VehicleViewDto {
    private Integer id_vehicle;
    private Integer id_user;
    private String license_plate;
    private String car_brand;
    private String color;
    private Timestamp registration_date;
}
