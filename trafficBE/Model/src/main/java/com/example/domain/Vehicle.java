package com.example.domain;

import java.sql.Timestamp;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Vehicle {
    private Integer id_vehicle;
    private Integer id_user;
    private String license_plate;
    private String car_brand;
    private String color;
    private Timestamp registration_date;
}
