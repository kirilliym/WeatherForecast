package dev.kirilliym.weatherforecast.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final StringRedisTemplate redisTemplate;
    private final CityService cityService;
    private static final String REDIS_KEY = "city:requests";

    @PostConstruct
    public void init() {
        Map<String, Long> cityCounts = cityService.getCityRequestCounts();
        for (Map.Entry<String, Long> entry : cityCounts.entrySet()) {
            redisTemplate.opsForZSet().add(REDIS_KEY, entry.getKey(), entry.getValue());
        }
    }

    public void incrementCityRequest(String cityName) {
        incrementCityRequest(cityName, 1);
    }
    public void incrementCityRequest(String cityName, int delta) {
        redisTemplate.opsForZSet().incrementScore(REDIS_KEY, cityName, delta);
    }

    public Map<String, Long> getTopCities(int topN) {
        Set<String> cities = redisTemplate.opsForZSet()
                .reverseRange(REDIS_KEY, 0, topN - 1);

        if (cities == null) return Collections.emptyMap();

        return cities.stream().collect(Collectors.toMap(
                city -> city,
                city -> Optional.ofNullable(redisTemplate.opsForZSet().score(REDIS_KEY, city))
                        .map(Double::longValue)
                        .orElse(0L),
                (a, b) -> b,
                LinkedHashMap::new
        ));
    }
}
