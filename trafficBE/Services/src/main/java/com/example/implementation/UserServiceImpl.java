package com.example.implementation;

import com.example.UserRepository;
import com.example.UserService;
import com.example.domain.User;
import com.example.dto.UserCreateDto;
import com.example.dto.UserViewDto;
import com.example.mapper.UserMapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;


import java.sql.Timestamp;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    //private final PasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        //this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = false)
    public void save(UserCreateDto userCreateDto) {
        userCreateDto.setCreate_time(new Timestamp(System.currentTimeMillis()));
        User user = userMapper.toEntity(userCreateDto);
        //user.setPassword(passwordEncoder.encode(user.getPassword())); daca ma stiu cu decoding ul e bine
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserViewDto> getAllUsers() {
        return userRepository.getAll().stream().map(userMapper::toViewDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserViewDto getByUsername(String username) {
        User user = userRepository.getByUsername(username);
        System.out.println(user.getId_user());
        return userMapper.toViewDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserViewDto> getUsersByFirstname(String firstname) {
        List<User> users = firstname == null ? userRepository.getAll() : userRepository.getByFirstname(firstname);
        return users.stream().map(userMapper::toViewDto).toList();
    }

}
