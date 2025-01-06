package com.example;

import com.example.dto.UserCreateDto;
import com.example.dto.UserViewDto;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface UserService {
    void save(UserCreateDto userCreateDto);
    List<UserViewDto> getAllUsers();
    UserViewDto getByUsername(String username);
    List<UserViewDto> getUsersByFirstname(String firstname);
}
