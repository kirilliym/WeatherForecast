package dev.kirilliym.weatherforecast.exception;

public class InvalidPlaceNameException extends RuntimeException {
    public InvalidPlaceNameException() {
        super("Указан неправильный адрес");
    }
}