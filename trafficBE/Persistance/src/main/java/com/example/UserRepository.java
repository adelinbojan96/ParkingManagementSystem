package com.example;

import com.example.domain.User;

import java.util.List;

public interface UserRepository extends CrudRepository<Integer, User>{
    User getByUsername(String username);

    List<User> getByFirstname(String firstname);
}
