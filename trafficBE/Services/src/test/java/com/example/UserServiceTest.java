package com.example;

import com.example.domain.User;
import com.example.dto.UserCreateDto;
import com.example.errors.InvalidValueException;
import com.example.implementation.UserRepositoryImpl;
import com.example.implementation.UserServiceImpl;
import com.example.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.TemplateEngine;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;  // Mock repository

    @Mock
    private UserMapper userMapper;  // Mock mapper

    @Mock
    private JavaMailSender mailSender;  // Mock mail sender

    @Mock
    private TemplateEngine templateEngine;  // Mock template engine

    @Mock
    private PasswordEncoder passwordEncoder;  // Mock password encoder

    @InjectMocks
    private UserServiceImpl userService;  // Inject mocks into the service

    @Test
    void whenSave_validationError() {
        UserCreateDto invalidUser = new UserCreateDto("Firstname","Lastname","USERNAME","password");
        User invalidUserEntity = new User(1, "Firstname","Lastname","USERNAME","password");

        when(passwordEncoder.encode(any(String.class))).thenReturn("encoded password");
        when(userMapper.toEntity(invalidUser)).thenReturn(invalidUserEntity);

        doThrow(new InvalidValueException("Username must be lowercase")).when(userRepository).save(any(User.class));

        InvalidValueException exception = assertThrows(InvalidValueException.class, () -> {
            userService.save(invalidUser);
        });

        assertEquals("Username must be lowercase",exception.getMessage());
    }

    @Test
    void getAllUsers() {

    }

    @Test
    void getByUsername() {
    }

    @Test
    void getUsersByFirstname() {
    }

    @Test
    void sendMail() {
    }
}