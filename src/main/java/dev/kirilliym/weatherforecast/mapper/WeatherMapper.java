package dev.kirilliym.weatherforecast.mapper;

import dev.kirilliym.weatherforecast.exception.OutOfWeatherRangeException;
import dev.kirilliym.weatherforecast.model.dto.CityDTO;
import dev.kirilliym.weatherforecast.model.dto.PlaceDTO;
import dev.kirilliym.weatherforecast.model.dto.WeatherDTO;
import dev.kirilliym.weatherforecast.model.entity.Weather;
import dev.kirilliym.weatherforecast.model.response.DatePlaceResponse;
import dev.kirilliym.weatherforecast.model.response.WeatherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WeatherMapper {
    private final PlaceMapper placeMapper;


    public WeatherDTO mapToDTO(Weather weather) {
        return new WeatherDTO(
                weather.getId(),
                placeMapper.mapToDTO(weather.getPlace()),
                weather.getTemp2Cel(),
                weather.getTempFeelsCel(),
                weather.getWindSpeed10(),
                weather.getPresSurf(),
                weather.getVlaga2f(),
                weather.getUpdated(),
                weather.getDate(),
                weather.getTime()
        );
    }

    public Weather mapToEntity(WeatherDTO dto) {
        return new Weather(
                dto.getId(),
                placeMapper.mapToEntity(dto.getPlace()),
                dto.getTemp2Cel(),
                dto.getTempFeelsCel(),
                dto.getWindSpeed10(),
                dto.getPresSurf(),
                dto.getVlaga2f(),
                dto.getUpdated(),
                dto.getDate(),
                dto.getTime()
        );
    }

    public WeatherResponse mapToWeatherResponse(WeatherDTO dto) {
        return new WeatherResponse(
                dto.getTemp2Cel(),
                dto.getTempFeelsCel(),
                dto.getWindSpeed10(),
                dto.getPresSurf(),
                dto.getVlaga2f(),
                dto.getTime()
        );
    }

    public DatePlaceResponse mapToResponse(List<WeatherDTO> weatherList) {
        if (weatherList.isEmpty()) {
            throw new OutOfWeatherRangeException();
        }

        PlaceDTO placeDTO = weatherList.get(0).getPlace();
        CityDTO cityDTO = placeDTO.getCity();

        List<WeatherResponse> weatherResponses = weatherList.stream()
                .map(this::mapToWeatherResponse)
                .collect(Collectors.toList());

        return new DatePlaceResponse(cityDTO.getName(), placeDTO.getName(), weatherList.get(0).getDate(), weatherResponses);
    }
}
