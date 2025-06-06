package dev.kirilliym.weatherforecast.controller;

import dev.kirilliym.weatherforecast.model.request.WeatherRequest;
import dev.kirilliym.weatherforecast.model.dto.PlaceDTO;
import dev.kirilliym.weatherforecast.model.dto.PrimeTokenDTO;
import dev.kirilliym.weatherforecast.model.dto.WeatherDTO;
import dev.kirilliym.weatherforecast.service.CacheService;
import dev.kirilliym.weatherforecast.service.PrimeTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final PrimeTokenService primeTokenService;
    private final CacheService cacheService;

    @PostMapping("/create-token")
    public ResponseEntity<String> createToken(@RequestParam String token, @RequestParam Integer ops) {
        primeTokenService.createToken(token, ops);
        return ResponseEntity.ok("Token created");
    }

    @GetMapping("/get-all-tokens")
    public ResponseEntity<List<PrimeTokenDTO>> getAllTokens() {
        return ResponseEntity.ok(primeTokenService.getAllTokens());
    }

    @GetMapping("/cache/typos")
    public ResponseEntity<Map<String, String>> getTypoCache() {
        return ResponseEntity.ok(cacheService.getAllTypos());
    }

    @GetMapping("/cache/places")
    public ResponseEntity<Map<String, PlaceDTO>> getPlaceCache() {
        return ResponseEntity.ok(cacheService.getAllPlaces());
    }

    @GetMapping("/cache/weather")
    public ResponseEntity<Map<WeatherRequest, List<WeatherDTO>>> getWeatherCache() {
        return ResponseEntity.ok(cacheService.getAllWeather());
    }
}
