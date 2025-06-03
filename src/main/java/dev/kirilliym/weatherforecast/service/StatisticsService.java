package dev.kirilliym.weatherforecast.service;

import dev.kirilliym.weatherforecast.model.entity.City;
import dev.kirilliym.weatherforecast.repository.CityRepository;
import dev.kirilliym.weatherforecast.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final RedisService redisService;
    private final CityRepository cityRepository;
    private final PrimeTokenService primeTokenService;
    private final HistoryService historyService;

    @Scheduled(fixedRate = 60_000)
    public void updateCityStats() {
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

    public Map<String, Long> getLastMonthHourStatistics(String token) {
        primeTokenService.useToken(token);
        return historyService.getRequestsCountPerHourLastMonth();
    }

    public Map<String, Long> getTopCities(int topN) {
        return redisService.getTopCities(topN);
    }
}
