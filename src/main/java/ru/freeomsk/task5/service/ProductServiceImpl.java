package ru.freeomsk.task5.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.freeomsk.task5.config.exception.ServiceException;
import ru.freeomsk.task5.dao.ProductDao;
import ru.freeomsk.task5.model.Product;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public Product createProduct(Product product) {
        return productDao.save(product);
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productDao.findById(id);
    }

    @Override
    public List<Product> getAllProductsByUserId(Long userId) {
        return productDao.findAllByUserId(userId);
    }

    @Override
    public List<Product> getAllProducts() {
        return productDao.findAll();
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = productDao.findById(id)
                .orElseThrow(() -> new ServiceException("Product not found for this id :: " + id));

        product.setUserId(productDetails.getUserId());
        product.setAccountNumber(productDetails.getAccountNumber());
        product.setBalance(productDetails.getBalance());
        product.setProductType(productDetails.getProductType());

        return productDao.update(id, product);
    }

    @Override
    public void deleteProduct(Long id) {
        productDao.deleteById(id);
    }
}