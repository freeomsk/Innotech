package ru.freeomsk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.freeomsk.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}