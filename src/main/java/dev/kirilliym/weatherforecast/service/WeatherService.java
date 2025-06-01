package dev.kirilliym.weatherforecast.service;

import com.fasterxml.jackson.databind.JsonNode;
import dev.kirilliym.weatherforecast.mapper.WeatherMapper;
import dev.kirilliym.weatherforecast.model.dto.PlaceDTO;
import dev.kirilliym.weatherforecast.model.dto.WeatherDTO;
import dev.kirilliym.weatherforecast.model.entity.Weather;
import dev.kirilliym.weatherforecast.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherRepository weatherRepository;
    private final PlaceService placeService;
    private final WeatherMapper weatherMapper;
    private final EolService eolService;

    private static final Duration MAX_DATA_AGE = Duration.ofHours(6);

    @Transactional
    public List<WeatherDTO> getWeather(String place, LocalDate date) {
        PlaceDTO placeDTO = placeService.getPlace(place);
        Long placeId = placeDTO.getId();

        List<Weather> existing = weatherRepository.findAllByPlaceIdAndDate(placeId, date);
        if (!existing.isEmpty()) {
            LocalDateTime latestUpdate = existing.stream()
                    .map(Weather::getUpdated)
                    .max(LocalDateTime::compareTo)
                    .orElse(LocalDateTime.MIN);

            if (latestUpdate.isAfter(LocalDateTime.now().minus(MAX_DATA_AGE))) {
                return existing.stream().map(weatherMapper::mapToDTO).toList();
            }

            weatherRepository.deleteAll(existing);
        }

        JsonNode jsonNode = eolService.getWeatherByCoordinates(placeDTO.getLat(), placeDTO.getLon(), date.toString());

        List<WeatherDTO> result = new ArrayList<>();
        for (JsonNode node : jsonNode) {
            WeatherDTO dto = new WeatherDTO();
            dto.setPlace(placeDTO);
            dto.setTemp2Cel((long) node.get("temp_2_cel").asDouble());
            dto.setTempFeelsCel((long) node.get("temp_feels_cel").asDouble());
            dto.setWindSpeed10((long) node.get("wind_speed_10").asDouble());
            dto.setPresSurf((long) node.get("pres_surf").asDouble());
            dto.setVlaga2f((long) node.get("vlaga_2").asDouble());

            String dateTimeStr = node.get("dt_forecast").asText();
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr);
            dto.setDate(dateTime.toLocalDate());
            dto.setTime(dateTime.toLocalTime());
            dto.setUpdated(LocalDateTime.now());

            Weather weather = weatherRepository.save(weatherMapper.mapToEntity(dto));
            result.add(weatherMapper.mapToDTO(weather));
        }

        return result;
    }
}

