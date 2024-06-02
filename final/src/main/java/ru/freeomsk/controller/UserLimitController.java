package ru.freeomsk.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.freeomsk.entity.User;
import ru.freeomsk.exception.UserNotFoundException;
import ru.freeomsk.exception.UserSaveException;
import ru.freeomsk.service.UserLimitService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/")
public class UserLimitController {

    private final UserLimitService service;

    public UserLimitController(UserLimitService service) {
        this.service = service;
    }

    @GetMapping("/{userId}/limit")
    public ResponseEntity<User> getUserLimit(@PathVariable Long userId) {
        try {
            User userLimit = service.getUserLimit(userId);
            return ResponseEntity.ok(userLimit);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (UserSaveException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/{userId}/payment")
    public ResponseEntity<String> processPayment(@PathVariable Long userId, @RequestParam BigDecimal amount) {
        try {
            service.processPayment(userId, amount);
            return ResponseEntity.ok("Payment processed successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/{userId}/payment-error")
    public ResponseEntity<String> processPaymentWithError(@PathVariable Long userId, @RequestParam BigDecimal amount) {
        try {
            service.processPaymentWithError(userId, amount);
            return ResponseEntity.ok("Payment processed successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}