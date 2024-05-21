package ru.freeomsk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.freeomsk.entity.Product;

@Configuration
public class DataSourceConfig {

    @Bean
    public Product product() {
        return new Product();
    }

}