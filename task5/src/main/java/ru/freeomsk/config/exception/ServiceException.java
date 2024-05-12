package ru.freeomsk.config.exception;

public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
}