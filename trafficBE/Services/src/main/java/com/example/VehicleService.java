package com.example;

import com.example.dto.VehicleCreateDto;
import com.example.dto.VehicleViewDto;
import java.util.List;

public interface VehicleService {
    void save(VehicleCreateDto vehicleCreateDto);
    List<VehicleViewDto> getAllVehicles();
}
