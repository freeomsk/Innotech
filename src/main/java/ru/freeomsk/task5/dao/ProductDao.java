package ru.freeomsk.task5.dao;

import ru.freeomsk.task5.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {

    List<Product> findAll();

    List<Product> findAllByUserId(Long userId);

    Optional<Product> findById(Long id);

    Product save(Product product);

    Product update(Long id, Product product);

    void deleteById(Long id);

}