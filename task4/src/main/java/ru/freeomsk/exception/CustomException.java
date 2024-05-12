package ru.freeomsk.exception;

public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}