package dev.kirilliym.weatherforecast.service;

import dev.kirilliym.weatherforecast.mapper.HistoryMapper;
import dev.kirilliym.weatherforecast.model.dto.HistoryDTO;
import dev.kirilliym.weatherforecast.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;
    private final HistoryMapper historyMapper;

    public Map<String, Long> getRequestsCountPerHourLastMonth() {
        LocalDate today = LocalDate.now();
        LocalDate monthAgo = today.minusMonths(1);

        var historyEntities = historyRepository.findAllByDateBetween(monthAgo, today);

        Map<String, Long> counts = new HashMap<>();

        for (var entity : historyEntities) {
            int hour = entity.getTime().getHour();
            counts.put(Integer.toString(hour), counts.getOrDefault(Integer.toString(hour), 0L) + 1);
        }

        return counts;
    }
    public void save(HistoryDTO historyDTO) {
        historyRepository.save(historyMapper.mapToEntity(historyDTO));
    }
}
