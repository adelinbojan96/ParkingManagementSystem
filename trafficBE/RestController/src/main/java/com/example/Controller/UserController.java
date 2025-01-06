package com.example.Controller;

import com.example.UserService;
import com.example.dto.UserCreateDto;
import com.example.dto.UserViewDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping(path = "")
    public void save(@RequestBody UserCreateDto user) {
        service.save(user);
    }

    @GetMapping(path = "")
    public List<UserViewDto> getAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping(path = "/{username}")
    public UserViewDto getByUsername(@PathVariable String username) {
        return service.getByUsername(username);
    }

    @GetMapping(path = "/byFirstname")
    public List<UserViewDto> getByFirstname(@RequestParam(required = false) String firstname) {
        return service.getUsersByFirstname(firstname);
    }

}
