package com.example.mapper;

import com.example.domain.User;
import com.example.dto.UserCreateDto;
import com.example.dto.UserViewDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", implementationPackage = "<PACKAGE_NAME>.generated", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User toEntity(UserCreateDto userCreateDto);
    UserViewDto toViewDto(User user);
}
