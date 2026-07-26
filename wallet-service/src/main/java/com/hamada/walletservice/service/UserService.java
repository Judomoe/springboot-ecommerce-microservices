package com.hamada.walletservice.service;

import com.hamada.walletservice.entity.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    List<User> getAllUsers();
    User getUserById(Long id);
    void deleteUser(Long id);
    User updateUser(Long id, User user);
    User withdraw(Long id, Double amount);
    User deposit(Long id, Double amount);
}
