package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleCreateDto {

    private Integer id_user;
    private String license_plate;
    private String car_brand;
    private String color;
    private Timestamp registration_date;
}
