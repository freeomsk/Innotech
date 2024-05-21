package ru.freeomsk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.freeomsk.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductType(String productType);
    List<Product> findByUserId(Long userId);
}