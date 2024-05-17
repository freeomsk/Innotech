package ru.freeomsk.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private Long id;
    private Long userId;
    private String accountNumber;
    private BigDecimal balance;
    private String productType;

    public Product() {
    }

    public Product(Long id, Long userId, String accountNumber, BigDecimal balance, String productType) {
        this.id = id;
        this.userId = userId;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.productType = productType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(userId, product.userId) && Objects.equals(accountNumber, product.accountNumber) && Objects.equals(balance, product.balance) && Objects.equals(productType, product.productType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, accountNumber, balance, productType);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", userId=" + userId +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", productType=" + productType +
                '}';
    }
}