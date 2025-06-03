package dev.kirilliym.weatherforecast.controller;

import dev.kirilliym.weatherforecast.mapper.StatisticsMapper;
import dev.kirilliym.weatherforecast.model.response.StatisticsResponse;
import dev.kirilliym.weatherforecast.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;
    private final StatisticsMapper statisticsMapper;

    @GetMapping("/hourly")
    public ResponseEntity<List<StatisticsResponse>> getHourlyStats(@RequestParam String token) {
        return ResponseEntity.ok(statisticsMapper.map(statisticsService.getLastMonthHourStatistics(token)));
    }

    @GetMapping("/city-top")
    public ResponseEntity<List<StatisticsResponse>> getCityTop(@RequestParam(defaultValue = "10") Integer topN) {
        return ResponseEntity.ok(statisticsMapper.map(statisticsService.getTopCities(topN)));
    }
}
