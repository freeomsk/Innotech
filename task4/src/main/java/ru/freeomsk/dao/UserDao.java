package ru.freeomsk.dao;

import ru.freeomsk.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findById(Long id);
    List<User> findAll();
    User create(User user);
    User update(User user);
    void delete(Long id);
}
