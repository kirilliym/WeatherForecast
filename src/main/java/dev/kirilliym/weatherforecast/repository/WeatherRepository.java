package dev.kirilliym.weatherforecast.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class WeatherRepository {
    private final Map<String, Integer> temperature = new HashMap<>();
    private final Random random = new Random();

    public int getTemperature(String city) {
        if (temperature.containsKey(city)) {
            return temperature.get(city);
        }
        return newCityTemperature(city);
    }

    private int newCityTemperature(String city) {
        int newTemperature = random.nextInt(60) - 30; // Диапазон -30..+30
        temperature.put(city, newTemperature);
        return newTemperature;
    }

}
