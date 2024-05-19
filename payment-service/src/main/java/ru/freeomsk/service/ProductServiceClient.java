package ru.freeomsk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.freeomsk.entity.Product;

import java.util.List;

@Service
public class ProductServiceClient {
    private final RestTemplate restTemplate;
    private final String productServiceUrl;
    static Product product;

    @Autowired
    public ProductServiceClient(RestTemplate restTemplate, @Value("${product-service.url}") String productServiceUrl, Product product) {
        this.restTemplate = restTemplate;
        this.productServiceUrl = productServiceUrl;
        ProductServiceClient.product = product;
    }

    public List<Product> getAllProducts() {
        ResponseEntity<List<Product>> response = restTemplate.exchange(
                productServiceUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }

}