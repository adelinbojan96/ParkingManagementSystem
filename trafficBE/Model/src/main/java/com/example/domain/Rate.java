package com.example.domain;

import java.sql.Date;
import java.sql.Timestamp;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Rate {
    private Integer id_rate;
    private String type;
    private Double hourly_rate;
    private Double daily_rate;
    private Double monthly_rate;
}
