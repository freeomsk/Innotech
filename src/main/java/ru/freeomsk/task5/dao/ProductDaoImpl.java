package ru.freeomsk.task5.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.freeomsk.task5.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class ProductDaoImpl implements ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final class ProductMapper implements RowMapper<Product> {
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Product(
                    rs.getLong("id"),
                    rs.getLong("user_id"),
                    rs.getString("account_number"),
                    rs.getBigDecimal("balance"),
                    rs.getString("product_type")
            );
        }
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM products";
        try {
            return jdbcTemplate.query(sql, new ProductMapper());
        } catch (DataAccessException e) {
            log.error("Error accessing data to find all products", e);
            throw new RuntimeException("Failed to find all products", e);
        }
    }

    @Override
    public List<Product> findAllByUserId(Long userId) {
        String sql = "SELECT * FROM products WHERE user_id = ?";
        try {
            return jdbcTemplate.query(sql, new ProductMapper(), userId);
        } catch (DataAccessException e) {
            log.error("Error accessing data for products with user ID: {}", userId, e);
            throw new RuntimeException("Failed to find products for user ID: " + userId, e);
        }
    }

    @Override
    public Optional<Product> findById(Long id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        try {
            Product product = jdbcTemplate.queryForObject(sql, new ProductMapper(), id);
            return Optional.ofNullable(product);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        } catch (DataAccessException e) {
            log.error("Error accessing data for product with ID: {}", id, e);
            throw new RuntimeException("Failed to find product with ID: " + id, e);
        }
    }

    @Override
    public Product save(Product product) {
        log.info("Saving product with userID: {}", product.getUserId());
        if (product.getUserId() == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        String sql = "INSERT INTO products (user_id, account_number, balance, product_type) VALUES (?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, product.getUserId(), product.getAccountNumber(), product.getBalance(), product.getProductType());
        } catch (DataAccessException e) {
            log.error("Error saving product with userID: {}", product.getUserId(), e);
            throw new RuntimeException("Failed to save product", e);
        }
        return product;
    }

    @Override
    public Product update(Long id, Product product) {
        String sql = "UPDATE products SET user_id = ?, account_number = ?, balance = ?, product_type = ? WHERE id = ?";
        try {
            jdbcTemplate.update(sql, product.getUserId(), product.getAccountNumber(), product.getBalance(), product.getProductType(), product.getId());
        } catch (DataAccessException e) {
            log.error("Error updating product with ID: {}", product.getId(), e);
            throw new RuntimeException("Failed to update product with ID: " + product.getId(), e);
        }
        return product;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM products WHERE id = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            log.error("Error deleting product with ID: {}", id, e);
            throw new RuntimeException("Failed to delete product with ID: " + id, e);
        }
    }
}