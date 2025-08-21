package com.example.repository;

import com.example.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(User user);
    Optional<User> findById(String id);
    List<User> findAll();
    void deleteById(String id);
}
