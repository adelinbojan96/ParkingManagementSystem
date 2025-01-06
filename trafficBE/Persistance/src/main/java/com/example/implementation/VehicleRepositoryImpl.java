package com.example.implementation;

import com.example.VehicleRepository;
import com.example.domain.Vehicle;
import com.example.errors.EntityFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class VehicleRepositoryImpl implements VehicleRepository {
    @Value("${spring.datasource.url}")
    private String connectionString;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Override
    public void save(Vehicle vehicle) {
        try (
                Connection connection = DriverManager.getConnection(connectionString, dbUsername, dbPassword);
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO vehicle(id_user, license_plate, car_brand, color, registration_date) VALUES (?, ?, ?, ?, ?)")
        )
        {

            if (getAll().stream().anyMatch(u -> u.getLicense_plate().equals(vehicle.getLicense_plate()))) {
                throw new EntityFoundException("Vehicle with license plate '%s' already exists".formatted(vehicle.getLicense_plate()));
            }

            preparedStatement.setInt(1, vehicle.getId_user());
            preparedStatement.setString(2, vehicle.getLicense_plate());
            preparedStatement.setString(3, vehicle.getCar_brand());
            preparedStatement.setString(4, vehicle.getColor());
            preparedStatement.setTimestamp(5, vehicle.getRegistration_date());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Failed to insert vehicle.");
            }
            else
            {
                System.out.println("Vehicle successfully inserted");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Vehicle> getAll() {
        return List.of();
    }
}
