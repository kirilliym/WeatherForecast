package dev.kirilliym.weatherforecast.exception;

public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException() {
        super("Такого токена не найдено");
    }
}