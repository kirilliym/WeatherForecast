package dev.kirilliym.weatherforecast.mapper;

import dev.kirilliym.weatherforecast.model.dto.WeatherDTO;
import dev.kirilliym.weatherforecast.model.entity.Weather;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WeatherMapper {
    private final CityMapper cityMapper;
    public WeatherDTO mapToDTO(Weather weather) {
        return new WeatherDTO(weather.getId(), cityMapper.mapToDTO(weather.getCity()), weather.getTemperature());
    }

    public Weather mapToEntity(WeatherDTO weatherDTO) {
        return new Weather(weatherDTO.getId(), cityMapper.mapToEntity(weatherDTO.getCity()), weatherDTO.getTemperature());
    }
}
