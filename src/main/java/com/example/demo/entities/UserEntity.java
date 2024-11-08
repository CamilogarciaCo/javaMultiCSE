package com.example.demo.entities;

import java.util.UUID;

public class UserEntity {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private String role;

    // Constructor
    public UserEntity(String name, String email, String password, String role) {
        this.id = UUID.randomUUID(); // Genera un nuevo UUID
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
