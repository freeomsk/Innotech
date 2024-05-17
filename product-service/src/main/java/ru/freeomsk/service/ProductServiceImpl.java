package ru.freeomsk.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.freeomsk.exception.ServiceException;
import ru.freeomsk.model.Product;
import ru.freeomsk.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> getAllProductsByUserId(Long userId) {
        return productRepository.findAllByUserId(userId);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Product not found for this id :: " + id));

        product.setUserId(productDetails.getUserId());
        product.setAccountNumber(productDetails.getAccountNumber());
        product.setBalance(productDetails.getBalance());
        product.setProductType(productDetails.getProductType());

        return productRepository.update(id, product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public boolean isProductValidAndSufficient(Long productId, BigDecimal amount) {
        Optional<Product> productOpt = getProductById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            // Проверка, что баланс продукта больше или равен сумме платежа
            return product.getBalance().compareTo(amount) >= 0;
        }
        return false;
    }
}