package com.example.implementation;

import com.example.UserRepository;
import com.example.domain.User;
import com.example.errors.EntityFoundException;
import com.example.errors.InvalidValueException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Value("${spring.datasource.url}")
    private String connectionString;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Override
    public void save(User user) {
        try (
                Connection connection = DriverManager.getConnection(connectionString, dbUsername, dbPassword);
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user(username, password, email, create_time, phone) VALUES (?, ?, ?, ?, ?)")
        )
        {
            if (!user.getUsername().equals(user.getUsername().toLowerCase())) {
                throw new InvalidValueException("Username must be lowercase");
            }

            if (getAll().stream().anyMatch(u -> u.getUsername().equals(user.getUsername()))) {
                throw new EntityFoundException("User with username '%s' already exists".formatted(user.getUsername()));
            }

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setTimestamp(4, user.getCreate_time());
            preparedStatement.setString(5, user.getPhone());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Failed to insert user.");
            }
            else
            {
                System.out.println("User successfully inserted");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(connectionString, dbUsername, dbPassword);
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user")
        )
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                users.add(toEntity(resultSet));

        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
        return users;
    }

    @Override
    public User getByUsername(String username) {
        try (
                Connection connection = DriverManager.getConnection(connectionString, dbUsername, dbPassword);
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE username = ?")
        ) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return toEntity(resultSet);
            }
            throw new RuntimeException("User with username = " + username + " not found!");
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    @Override
    public List<User> getByFirstname(String firstname) {
        List<User> users = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(connectionString, dbUsername, dbPassword);
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE firstname LIKE ?")
        )
        {
            preparedStatement.setString(1, "%" + firstname + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(toEntity(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
        return users;
    }

    private User toEntity(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId_user(resultSet.getInt("id_user"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setEmail(resultSet.getString("email"));
        user.setCreate_time(resultSet.getTimestamp("create_time"));
        user.setPhone(resultSet.getString("phone"));
        return user;
    }
}
