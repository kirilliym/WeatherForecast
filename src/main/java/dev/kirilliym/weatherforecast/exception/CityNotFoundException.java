package dev.kirilliym.weatherforecast.exception;

public class CityNotFoundException extends RuntimeException {
    public CityNotFoundException() { super("Не удалось обозначить город");}
}
