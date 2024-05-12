package ru.freeomsk.service;

import org.springframework.stereotype.Service;
import ru.freeomsk.dao.UserDao;
import ru.freeomsk.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User createUser(User user) {
        return userDao.create(user);
    }

    @Override
    public void deleteUser(Long id) {
        userDao.delete(id);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public User updateUser(User user) {
        return userDao.update(user);
    }
}