package ru.freeomsk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<String> handleInsufficientFunds(InsufficientFundsException ex) {
        // Клиент получит ответ с кодом 400 и сообщением об ошибке, указывающим на проблему с недостатком средств
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> handleServiceException(ServiceException ex) {
        // Возвращаем статус 404 Not Found, если ресурс не найден
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex) {
        // Возвращаем статус 500 Internal Server Error для всех остальных исключений
        return new ResponseEntity<>("An error occurred " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}