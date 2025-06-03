package dev.kirilliym.weatherforecast.exception;

public class NoTokenOperationsException extends RuntimeException {
    public NoTokenOperationsException() {
        super("У токена не осталось использований, купите новый");
    }
}
