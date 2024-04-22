package ru.freeomsk.task4.exception;

public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}