package dev.kirilliym.weatherforecast.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import dev.kirilliym.weatherforecast.model.WeatherRequest;
import dev.kirilliym.weatherforecast.model.dto.PlaceDTO;
import dev.kirilliym.weatherforecast.model.dto.WeatherDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;

@Configuration
public class CacheConfig {

    @Bean
    public Cache<String, String> typosCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(1))
                .maximumSize(5)
                .build();
    }

    @Bean
    public Cache<String, PlaceDTO> placeCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofHours(1))
                .maximumSize(3)
                .build();
    }

    @Bean
    public Cache<WeatherRequest, List<WeatherDTO>> weatherCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofHours(1))
                .maximumSize(10_000)
                .build();
    }
}
