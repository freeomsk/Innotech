package ru.freeomsk.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.freeomsk.dao.UserDao;
import ru.freeomsk.dao.UserDaoImpl;
import ru.freeomsk.service.UserService;
import ru.freeomsk.service.UserServiceImpl;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        config.setUsername("postgres");
        config.setPassword("postgres");
        config.setDriverClassName("org.postgresql.Driver");

        return new HikariDataSource(config);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
    @Bean
    public UserDao userDao(JdbcTemplate jdbcTemplate) {
        return new UserDaoImpl(jdbcTemplate);
    }

    @Bean
    public UserService userService(UserDao userDao) {
        return new UserServiceImpl(userDao);
    }
}