package com.example.domain;

import java.sql.Date;
import java.sql.Timestamp;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Reservation {
    private Integer id_reservation;
    private Integer id_user;
    private Integer id_vehicle;
    private Integer id_space;
    private Date start_date;
    private Date end_date;
    private String status;
    private Timestamp created_at;
}
