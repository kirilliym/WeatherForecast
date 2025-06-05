package dev.kirilliym.weatherforecast.service;

import com.github.benmanes.caffeine.cache.Cache;
import dev.kirilliym.weatherforecast.model.WeatherRequest;
import dev.kirilliym.weatherforecast.model.dto.PlaceDTO;
import dev.kirilliym.weatherforecast.model.dto.WeatherDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CacheService {

    private final Cache<String, String> typosCache;
    private final Cache<String, PlaceDTO> placeCache;
    private final Cache<WeatherRequest, List<WeatherDTO>> weatherCache;
    private final Cache<String, List<String>> hotCitiesCache;

    public Map<String, String> getAllTypos() {
        return typosCache.asMap();
    }

    public Map<String, PlaceDTO> getAllPlaces() {
        return placeCache.asMap();
    }

    public Map<WeatherRequest, List<WeatherDTO>> getAllWeather() {
        return weatherCache.asMap();
    }

    public List<String> getHotCities() {
        return hotCitiesCache.getIfPresent("hot_cities") != null
                ? hotCitiesCache.getIfPresent("hot_cities")
                : List.of();
    }
}
