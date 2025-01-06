package com.example.Controller;

import com.example.dto.VehicleCreateDto;
import com.example.dto.VehicleViewDto;
import com.example.implementation.VehicleServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
@CrossOrigin(origins = "http://localhost:3000")
public class VehicleController {
    private final VehicleServiceImpl service;

    public VehicleController(VehicleServiceImpl service) {
        this.service = service;
    }

    @PostMapping(path = "")
    public void save(@RequestBody VehicleCreateDto vehicle) {
        service.save(vehicle);
    }

    @GetMapping(path = "")
    public List<VehicleViewDto> getAllVehicles() {
        return service.getAllVehicles();
    }
}
