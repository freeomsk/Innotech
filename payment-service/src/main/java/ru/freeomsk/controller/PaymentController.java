package ru.freeomsk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.freeomsk.entity.Product;
import ru.freeomsk.service.ProductServiceClient;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final ProductServiceClient productServiceClient;

    @Autowired
    public PaymentController(ProductServiceClient productServiceClient) {
        this.productServiceClient = productServiceClient;
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productServiceClient.getAllProducts();
    }

}