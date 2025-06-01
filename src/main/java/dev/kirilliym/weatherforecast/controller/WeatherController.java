package dev.kirilliym.weatherforecast.controller;

import dev.kirilliym.weatherforecast.mapper.WeatherMapper;
import dev.kirilliym.weatherforecast.model.dto.WeatherDTO;
import dev.kirilliym.weatherforecast.model.response.DatePlaceResponse;
import dev.kirilliym.weatherforecast.service.CityService;
import dev.kirilliym.weatherforecast.service.PlaceService;
import dev.kirilliym.weatherforecast.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;
    private final WeatherMapper weatherMapper;
    private final PlaceService placeService;
    private final CityService cityService;


    @GetMapping("/weather")
    public ResponseEntity<DatePlaceResponse> getWeather(@RequestParam String place, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<WeatherDTO> weatherDTOList = weatherService.getWeather(place, date);

        return ResponseEntity.ok(weatherMapper.mapToResponse(weatherDTOList));
    }
}