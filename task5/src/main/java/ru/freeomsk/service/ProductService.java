package ru.freeomsk.service;

import org.springframework.stereotype.Service;
import ru.freeomsk.model.Product;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {
    Product createProduct(Product product);
     Optional<Product> getProductById(Long id);
    List<Product> getAllProductsByUserId(Long userId);
    List<Product> getAllProducts();
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
}