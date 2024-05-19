package ru.freeomsk.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.freeomsk.entity.Product;
import ru.freeomsk.exception.ProductNotFoundException;
import ru.freeomsk.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createOrUpdateProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> getProductById(Long id) {
        return Optional.ofNullable(productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found")));
    }

    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found");
        }
        return products;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getProductsByType(String productType) {
        List<Product> products = productRepository.findByProductType(productType);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found with type " + productType);
        }
        return products;
    }

    public List<Product> getProductsByUserId(Long userId) {
        List<Product> products = productRepository.findByUserId(userId);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found for user ID " + userId);
        }
        return products;
    }

    public boolean isBalanceSufficient(Long productId, BigDecimal paymentAmount) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            boolean isSufficient = product.getBalance().compareTo(paymentAmount) >= 0;
            if (!isSufficient) {
                logger.warn("Insufficient balance for product ID {}: required {}, available {}", productId, paymentAmount, product.getBalance());
            }
            return isSufficient;
        } else {
            logger.error("Product with ID {} not found", productId);
            return false;
        }
    }
}