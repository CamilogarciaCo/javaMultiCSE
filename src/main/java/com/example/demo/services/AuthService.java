package com.example.demo.services;

import org.springframework.stereotype.Service;
import com.example.demo.entities.UserEntity;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public Optional<UserEntity> login(String email, String password) {
        return userService.getAllUsers().stream()
                .filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password))
                .findFirst();
    }

    public Optional<UserEntity> loginWithRole(String email, String password, String role) {
        return userService.getAllUsers().stream()
                .filter(user -> user.getEmail().equals(email) &&
                        user.getPassword().equals(password) &&
                        user.getRole().equalsIgnoreCase(role))
                .findFirst();
    }
}
