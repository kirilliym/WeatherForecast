package dev.kirilliym.weatherforecast.service;

import dev.kirilliym.weatherforecast.model.dto.WeatherDTO;
import dev.kirilliym.weatherforecast.model.entity.City;
import dev.kirilliym.weatherforecast.model.entity.Weather;
import dev.kirilliym.weatherforecast.exception.InvalidCityNameException;
import dev.kirilliym.weatherforecast.mapper.WeatherMapper;
import dev.kirilliym.weatherforecast.repository.CityRepository;
import dev.kirilliym.weatherforecast.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final CityRepository cityRepository;
    private final WeatherMapper weatherMapper;
    private final Random random = new Random();

    @Transactional
    public WeatherDTO getCityTemperature(String cityName) {
        validateCityName(cityName);

        City city = cityRepository.findByName(cityName)
                .orElseGet(() -> {
                    City newCity = new City();
                    newCity.setName(cityName);
                    return cityRepository.save(newCity);
                });

        return weatherRepository.findByCity(city)
                .map(weatherMapper::mapToDTO)
                .orElseGet(() -> createNewWeatherEntry(city));
    }

    private WeatherDTO createNewWeatherEntry(City city) {
        int newTemperature = random.nextInt(60) - 30;
        Weather weather = new Weather();
        weather.setCity(city);
        weather.setTemperature(newTemperature);
        weatherRepository.save(weather);
        return weatherMapper.mapToDTO(weather);
    }

    private void validateCityName(String city) {
        if (city == null || city.isEmpty()) {
            throw new InvalidCityNameException("City name cannot be empty");
        }

        if (!city.matches("[А-Яа-яЁё]+")) {
            throw new InvalidCityNameException("City name should contain only Russian letters");
        }
    }
}
