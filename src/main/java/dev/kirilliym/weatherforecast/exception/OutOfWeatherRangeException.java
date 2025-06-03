package dev.kirilliym.weatherforecast.exception;

public class OutOfWeatherRangeException extends RuntimeException {
    public OutOfWeatherRangeException() {
        super("Информации о погоде для данной даты нет");
    }
}
