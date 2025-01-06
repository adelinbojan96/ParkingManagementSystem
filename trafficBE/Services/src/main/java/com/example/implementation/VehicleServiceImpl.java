package com.example.implementation;
import com.example.VehicleRepository;
import com.example.VehicleService;
import com.example.domain.Vehicle;
import com.example.dto.VehicleCreateDto;
import com.example.dto.VehicleViewDto;
import com.example.mapper.VehicleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, VehicleMapper vehicleMapper) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
    }

    @Override
    @Transactional(readOnly = false)
    public void save(VehicleCreateDto vehicleCreateDto) {
        vehicleCreateDto.setRegistration_date(new Timestamp(System.currentTimeMillis()));
        Vehicle vehicle = vehicleMapper.toEntity(vehicleCreateDto);
        vehicleRepository.save(vehicle);
    }

    @Override
    @Transactional(readOnly = false)
    public List<VehicleViewDto> getAllVehicles() {
        return List.of();
    }
}
