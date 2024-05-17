package ru.freeomsk.repository;

import ru.freeomsk.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> findAll();

    List<Product> findAllByUserId(Long userId);

    Optional<Product> findById(Long id);

    Product save(Product product);

    Product update(Long id, Product product);

    void deleteById(Long id);

}