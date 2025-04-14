package dev.kirilliym.weatherforecast.service;

import dev.kirilliym.weatherforecast.exception.InvalidCityNameException;
import dev.kirilliym.weatherforecast.repository.WeatherRepository;

public class WeatherService {
    private final WeatherRepository weatherRepository = new WeatherRepository();

    public int getCityTemperature(String city) {
        validateCityName(city);
        return weatherRepository.getTemperature(city);
    }

    private static void validateCityName(String city) throws InvalidCityNameException {
        if (city == null || city.isEmpty()) {
            throw new InvalidCityNameException("City name cannot be empty");
        }

        if (!city.matches("[А-Яа-яЁё]+")) {
            throw new InvalidCityNameException("City name should contain only Russian letters");
        }
    }
}