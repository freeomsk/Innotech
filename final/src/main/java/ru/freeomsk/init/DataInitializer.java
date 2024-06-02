package ru.freeomsk.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.freeomsk.entity.User;
import ru.freeomsk.repository.UserRepository;

import java.math.BigDecimal;
import java.util.stream.LongStream;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        LongStream.rangeClosed(1, 100).forEach(id -> {
            User user = new User();
            user.setUserId(id);
            user.setDailyLimit(BigDecimal.valueOf(10000.00));
            user.setCurrentLimit(BigDecimal.valueOf(10000.00));
            userRepository.save(user);
        });
    }
}