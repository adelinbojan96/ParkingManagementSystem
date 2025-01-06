package com.example.mapper;

import com.example.domain.User;
import com.example.domain.Vehicle;
import com.example.dto.VehicleCreateDto;
import com.example.dto.VehicleViewDto;
import com.example.dto.VehicleViewDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", implementationPackage = "<PACKAGE_NAME>.generated", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VehicleMapper {
    Vehicle toEntity(VehicleCreateDto vehicleCreateDto);
    VehicleViewDto toViewDto(Vehicle vehicle);
}
