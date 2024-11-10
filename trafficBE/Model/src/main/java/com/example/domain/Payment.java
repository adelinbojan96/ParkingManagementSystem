package com.example.domain;

import java.sql.Date;
import java.sql.Timestamp;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Payment {
    private Integer id_payment;
    private Integer id_reservation;
    private Integer amount;
    private String status;
    private Date payment_date;
    private String method;
}
