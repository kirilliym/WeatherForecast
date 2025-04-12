package dev.kirilliym.weatherforecast.exception;

public class InvalidCityNameException extends RuntimeException {
    public InvalidCityNameException(String message) {
        super(message);
    }
}