package ru.freeomsk.task4.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.freeomsk.task4.exception.CustomException;
import ru.freeomsk.task4.model.User;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<User> rowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        return user;
    };

    @Override
    public Optional<User> findById(Long id) {
        try {
            User user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?", rowMapper, id);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            log.error("Не найден пользователь с id = {}", id, e);
            return Optional.empty();
        } catch (DataAccessException e) {
            log.error("Ошибка доступа к данным при поиске пользователя с id = {}", id, e);
            throw new CustomException("Ошибка доступа к данным при поиске пользователя с id " + id + ": " + e.getMessage());
        } catch (Exception e) {
            log.error("Произошла неизвестная ошибка при поиске пользователя с id = {}", id, e);
            throw new CustomException("Произошла неизвестная ошибка при поиске пользователя с id " + id + ": " + e.getMessage());
        }
    }

    @Override
    public List<User> findAll() {
        try {
            List<User> users = jdbcTemplate.query("SELECT * FROM users", rowMapper);
            if (users.isEmpty()) {
                log.info("В базе данных нет пользователей");
                throw new CustomException("В базе данных нет пользователей");
            }
            return users;
        } catch (DataAccessException e) {
            log.error("Ошибка доступа к данным при попытке получить всех пользователей", e);
            throw new CustomException("Ошибка доступа к данным при попытке получить всех пользователей: " + e.getMessage());
        } catch (Exception e) {
            log.error("Произошла неизвестная ошибка при попытке получить всех пользователей", e);
            throw new CustomException("Произошла неизвестная ошибка при попытке получить всех пользователей: " + e.getMessage());
        }
    }

    @Override
    public User create(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO users (username) VALUES (?)", new String[]{"id"});
                ps.setString(1, user.getUsername());
                return ps;
            }, keyHolder);

            Number key = keyHolder.getKey();
            if (key != null) {
                user.setId(key.longValue());
            } else {
                // Обработка ситуации, когда ключ не был сгенерирован
                log.error("Не удалось получить сгенерированный id для пользователя");
                throw new CustomException("Не удалось получить сгенерированный id для пользователя");
            }
        } catch (DataAccessException e) {
            log.error("Ошибка доступа к данным при создании пользователя", e);
            throw new CustomException("Ошибка доступа к данным при создании пользователя: " + e.getMessage());
        } catch (Exception e) {
            log.error("Произошла неизвестная ошибка при создании пользователя", e);
            throw new CustomException("Произошла неизвестная ошибка при создании пользователя: " + e.getMessage());
        }
        return user;
    }

    @Override
    public User update(User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("id пользователя для обновления не может быть null");
        }
        try {
            int updatedRows = jdbcTemplate.update("UPDATE users SET username = ? WHERE id = ?", user.getUsername(), user.getId());
            if (updatedRows > 0) {
                return user;
            } else {
                log.error("Пользователь с id {} не найден", user.getId());
                throw new CustomException("Пользователь с id " + user.getId() + " не найден");
            }
        } catch (DataAccessException e) {
            log.error("Ошибка доступа к данным при обновлении пользователя с id {}", user.getId(), e);
            throw new CustomException("Ошибка доступа к данным при обновлении пользователя с id " + user.getId() + ": " + e.getMessage());
        } catch (Exception e) {
            log.error("Произошла неизвестная ошибка при обновлении пользователя с id {}", user.getId(), e);
            throw new CustomException("Произошла неизвестная ошибка при обновлении пользователя с id " + user.getId() + ": " + e.getMessage());
        }
    }

    public void delete(Long id) {
        try {
            int deletedRows = jdbcTemplate.update("DELETE FROM users WHERE id = ?", id);
            if (deletedRows == 0) {
                // Проверяем, есть ли пользователи в базе данных
                Integer userCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
                if (userCount != null && userCount == 0) {
                    log.info("В базе данных нет пользователей");
                    throw new CustomException("В базе данных нет пользователей");
                } else {
                    log.error("Пользователь с id {} не найден", id);
                    throw new CustomException("Пользователь с id " + id + " не найден");
                }
            }
        } catch (DataAccessException e) {
            log.error("Ошибка доступа к данным при попытке удалить пользователя с id {}", id, e);
            throw new CustomException("Ошибка доступа к данным при попытке удалить пользователя с id " + id + ": " + e.getMessage());
        } catch (Exception e) {
            log.error("Произошла неизвестная ошибка при попытке удалить пользователя с id {}", id, e);
            throw new CustomException("Произошла неизвестная ошибка при попытке удалить пользователя с id " + id + ": " + e.getMessage());
        }
    }
}