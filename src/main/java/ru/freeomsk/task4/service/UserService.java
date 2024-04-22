package ru.freeomsk.task4.service;

import ru.freeomsk.task4.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    void deleteUser(Long id);
    Optional<User> getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(User user);
}
