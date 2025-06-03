package dev.kirilliym.weatherforecast.mapper;

import dev.kirilliym.weatherforecast.model.response.StatisticsResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class StatisticsMapper {

    public List<StatisticsResponse> map(Map<?, Long> stats) {
        return stats.entrySet().stream()
                .map(entry -> new StatisticsResponse(entry.getKey().toString(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
