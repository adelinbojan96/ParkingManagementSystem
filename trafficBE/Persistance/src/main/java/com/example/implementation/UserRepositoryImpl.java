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
import java.util.Objects;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Value(value = "${spring.datasource.url}")
    private String connectionString;

    @Override
    public void save(User user) {
        try (
                Connection connection = DriverManager.getConnection(connectionString);
                PreparedStatement preparedStatement = connection.prepareStatement("insert into users(firstname, lastname, username, password) values (?,?,?,?)")
        ) {
            if(!Objects.equals(user.getUsername(), user.getUsername().toLowerCase()))
                throw new InvalidValueException("Username must be lowercase");

            if(getAll().stream().anyMatch(u -> Objects.equals(u.getUsername(), user.getUsername())))
                throw new EntityFoundException("User with username '%s' already exists".formatted(user.getUsername()));

            preparedStatement.setString(1, user.getFirstname());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getPassword());

            int cols = preparedStatement.executeUpdate();

            if (cols == 0)
                throw new RuntimeException("Database error");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(connectionString);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from users")
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                users.add(toEntity(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public User getByUsername(String username) {
        try (
                Connection connection = DriverManager.getConnection(connectionString);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from users where username like ?")
        ) {
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return toEntity(resultSet);
            }

            throw new RuntimeException("User with username = " + username + "not found!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getByFirstname(String firstname) {
        List<User> users = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(connectionString);
                PreparedStatement preparedStatement = connection.prepareStatement("select * from users where firstname like '%'+?+'%'")
        ) {
            preparedStatement.setString(1, firstname);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                users.add(toEntity(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    private User toEntity(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getInt("user_id"));
        user.setFirstname(resultSet.getString("firstname"));
        user.setLastname(resultSet.getString("lastname"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        return user;
    }
}
