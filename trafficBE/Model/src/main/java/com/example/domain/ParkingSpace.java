package com.example.domain;

import java.sql.Date;
import java.sql.Timestamp;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ParkingSpace {
    private Integer id_parking__space;
    private String location;
    private String status;
    private String type;
}
