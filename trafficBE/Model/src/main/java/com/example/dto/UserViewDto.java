package com.example.dto;

import lombok.Data;

@Data
public class UserViewDto {
    private Integer userId;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
}
