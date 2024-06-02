package ru.freeomsk.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private BigDecimal dailyLimit = BigDecimal.valueOf(10000.00);
    private BigDecimal currentLimit = BigDecimal.valueOf(10000.00);

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(BigDecimal dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public BigDecimal getCurrentLimit() {
        return currentLimit;
    }

    public void setCurrentLimit(BigDecimal currentLimit) {
        this.currentLimit = currentLimit;
    }
}