package dev.kirilliym.weatherforecast.consumer;

import dev.kirilliym.weatherforecast.avro.UserRequest;
import dev.kirilliym.weatherforecast.model.dto.HistoryDTO;
import dev.kirilliym.weatherforecast.model.dto.PlaceDTO;
import dev.kirilliym.weatherforecast.repository.HistoryRepository;
import dev.kirilliym.weatherforecast.service.HistoryService;
import dev.kirilliym.weatherforecast.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class HistoryConsumer {

    private final PlaceService placeService;
    private final HistoryService historyService;

    @KafkaListener(topics = "user_request", groupId = "weather-consumer-group")
    public void listen(ConsumerRecord<String, UserRequest> record) {
        UserRequest userRequest = record.value();

        String request = userRequest.getRequest().toString();
        PlaceDTO placeDTO = placeService.getPlaceById(userRequest.getPlaceId());
        LocalDate requestDate = LocalDate.parse(userRequest.getRequestDate());
        LocalDate date = LocalDate.parse(userRequest.getDate());
        LocalTime time = LocalTime.parse(userRequest.getTime());

        HistoryDTO historyDTO = new HistoryDTO(
                null,
                request,
                requestDate,
                placeDTO,
                date,
                time
        );

        historyService.save(historyDTO);
    }
}
