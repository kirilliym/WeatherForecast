package dev.kirilliym.weatherforecast.service;

import com.github.benmanes.caffeine.cache.Cache;
import dev.kirilliym.weatherforecast.model.entity.City;
import dev.kirilliym.weatherforecast.repository.CityRepository;
import dev.kirilliym.weatherforecast.repository.HistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final RedisService redisService;
    private final CityRepository cityRepository;
    private final PrimeTokenService primeTokenService;
    private final HistoryService historyService;
    private final Cache<String, List<String>> hotCitiesCache;

    @Scheduled(fixedRate = 60_000)
    private void updateCityStats() {
        Map<String, Long> allCities = redisService.getTopCities(0);

        for (Map.Entry<String, Long> entry : allCities.entrySet()) {
            String cityName = entry.getKey();
            Long redisCount = entry.getValue();

            cityRepository.findByName(cityName).ifPresentOrElse(city -> {
                Long dbCount = city.getRequestCnt();
                if (dbCount > redisCount) {
                    long delta = dbCount - redisCount;
                    redisService.incrementCityRequest(cityName, (int) delta);
                } else {
                    city.setRequestCnt(redisCount);
                    cityRepository.save(city);
                }
            }, () -> {
                City newCity = new City();
                newCity.setName(cityName);
                newCity.setRequestCnt(redisCount);
                cityRepository.save(newCity);
            });
        }
    }

    @Scheduled(fixedRate = 6_000)
    private void updateHotCities() {
        Map<String, Long> topCities = redisService.getTopCities(5);
        List<String> cityNames = topCities.keySet().stream().toList();

        hotCitiesCache.invalidate("hot_cities");

        hotCitiesCache.put("hot_cities", cityNames);
    }

    public Map<String, Long> getLastMonthHourStatistics(String token) {
        primeTokenService.useToken(token);
        return historyService.getRequestsCountPerHourLastMonth();
    }

    public Map<String, Long> getTopCities(int topN) {
        return redisService.getTopCities(topN);
    }
}
