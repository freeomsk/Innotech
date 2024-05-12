package ru.freeomsk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.freeomsk.util.Utils;
import ru.freeomsk.config.DataSourceConfig;
import ru.freeomsk.model.User;
import ru.freeomsk.service.UserService;

@Slf4j
public class Main {
    public static void main(String[] args) {
        Utils.printFormatted('*', 100);

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DataSourceConfig.class)) {
            UserService userService = context.getBean(UserService.class);
            Utils.printFormatted('*', 100);

            // Создание пользователя
            User newUser = new User(null, "newUsername");
            User createdUser = userService.createUser(newUser);
            System.out.println("Создан пользователь " + createdUser.getUsername() + " с id = " + createdUser.getId());
            Utils.printFormatted('-', 100);

            // Получение и вывод всех пользователей
            System.out.println("Все пользователи:");
            userService.getAllUsers().forEach(user -> System.out.println(user.getUsername()));
            Utils.printFormatted('-', 100);

            // Получение пользователя по id
            userService.getUserById(createdUser.getId()).ifPresent(user ->
                    System.out.println("Получен пользователь " + user.getUsername() + " с id = " + user.getId()));
            Utils.printFormatted('-', 100);

            // Обновление пользователя
            createdUser.setUsername("updatedUsername");
            User updatedUser = userService.updateUser(createdUser);
            System.out.println("Имя пользователя с id = " + createdUser.getId() + " обновлено на " + updatedUser.getUsername());
            Utils.printFormatted('-', 100);

            // Удаление пользователя
            userService.deleteUser(updatedUser.getId());
            System.out.println("Удален пользователь с id = " + updatedUser.getId());
            Utils.printFormatted('*', 100);
        } catch (Exception e) {
            System.err.println("Произошла ошибка при инициализации Spring контекста: " + e.getMessage());
            e.printStackTrace();
        }
        Utils.printFormatted('*', 100);
    }
}