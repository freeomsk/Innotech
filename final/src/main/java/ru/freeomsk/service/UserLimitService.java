package ru.freeomsk.service;

import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.freeomsk.entity.User;
import ru.freeomsk.exception.UserSaveException;
import ru.freeomsk.repository.UserRepository;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UserLimitService {

    private final UserRepository repository;

    public UserLimitService(UserRepository repository) {
        this.repository = repository;
    }

    public User getUserLimit(Long userId) {
        try {
            return repository.findById(userId).orElseGet(() -> {
                User newUserLimit = new User();
                newUserLimit.setUserId(userId);
                return repository.save(newUserLimit);
            });
        } catch (Exception e) {
            throw new UserSaveException("Failed to save new user limit", e);
        }
    }

    @Transactional
    public void processPayment(Long userId, BigDecimal amount) {
        Optional<User> userOptional = repository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getCurrentLimit().compareTo(amount) >= 0) {
                user.setCurrentLimit(user.getCurrentLimit().subtract(amount));
                repository.save(user);
            } else {
                throw new IllegalArgumentException("Payment amount exceeds current limit");
            }
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    // Вариант без @Transaction с симуляцией ошибки во время платежа, если userId = 10
    public void processPaymentWithError(Long userId, BigDecimal amount) {
        Optional<User> userOptional = repository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getCurrentLimit().compareTo(amount) >= 0) {
                user.setCurrentLimit(user.getCurrentLimit().subtract(amount));
                repository.save(user);
                // Симуляция ошибки работы платежного сервиса
                try {
                    simulationProcessPaymentWithError(userId, amount);
                } catch (Exception e) {
                    // Восстановление лимита в случае ошибки во время платежа
                    user.setCurrentLimit(user.getCurrentLimit().add(amount));
                    repository.save(user);
                    throw new RuntimeException("Payment processing failed: " + e.getMessage(), e);
                }
            } else {
                throw new IllegalArgumentException("Payment amount exceeds current limit");
            }
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    private void simulationProcessPaymentWithError(Long userId, BigDecimal amount) throws Exception {
        // Симуляции ошибки работы платежного сервиса, если userId = 10
        if (userId == 10) {
            throw new Exception("Payment service error");
        }
    }

    // В 00.00 каждого дня лимит для всех пользователей должен быть сброшен
    @Scheduled(cron = "0 0 0 * * ?")
    public void resetLimits() {
        repository.findAll().forEach(userLimit -> {
            userLimit.setCurrentLimit(userLimit.getDailyLimit());
            repository.save(userLimit);
        });
    }
}