package com.example.demo.services;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.example.demo.entities.UserEntity;

@Service
public class UserService {
    private List<UserEntity> users;

    public UserService() {
        this.users = new ArrayList<>();

        users.add(new UserEntity("Juan Pérez", "user@user.com", "user123", "USER"));
        users.add(new UserEntity("Ana Gómez", "admin@admin.com", "admin123", "ADMIN"));
    }

    public List<UserEntity> getAllUsers() {
        return users;
    }

    public Optional<UserEntity> getUserById(UUID id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    public UserEntity addUser(UserEntity user) {
        users.add(user);
        return user;
    }

    public Optional<UserEntity> updateUser(UUID id, UserEntity updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                updatedUser.setId(id);
                users.set(i, updatedUser);
                return Optional.of(updatedUser);
            }
        }
        return Optional.empty();
    }

    public boolean deleteUser(UUID id) {
        return users.removeIf(u -> u.getId().equals(id));
    }
}
