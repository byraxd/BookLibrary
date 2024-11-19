package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;

public interface UserService {
    User addUser(User user);
    List<User> getAllUsers();
    User getUserById(Long id);
    User updateUser(Long id, User user);
    void deleteUserById(Long id);
    Double getTotalFineAmountFromUser(Long userId);
}
