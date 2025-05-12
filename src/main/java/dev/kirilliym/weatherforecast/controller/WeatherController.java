package dev.kirilliym.weatherforecast.controller;

import dev.kirilliym.weatherforecast.exception.InvalidCityNameException;
import dev.kirilliym.weatherforecast.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/temperature")
    public ResponseEntity<String> getTemperature(@RequestParam String city) {
        try {
            int temperature = weatherService.getCityTemperature(city).getTemperature();
            return ResponseEntity.ok(String.format("Temperature in %s: %d°C", city, temperature));
        } catch (InvalidCityNameException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Unexpected error: " + e.getMessage());
        }
    }
}