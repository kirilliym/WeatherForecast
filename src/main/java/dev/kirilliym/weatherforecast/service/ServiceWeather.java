package dev.kirilliym.weatherforecast.service;

import dev.kirilliym.weatherforecast.exception.InvalidCityNameException;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ServiceWeather {
    private final Map<String, Integer> temperature = new HashMap<>();
    private final Random random = new Random();

    public int getCityTemperature(String city) {
        validateCityName(city);
        if (temperature.containsKey(city)) {
            return temperature.get(city);
        }

        int newTemperature = random.nextInt(60) - 30; // Диапазон -30..+30
        temperature.put(city, newTemperature);
        return newTemperature;
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