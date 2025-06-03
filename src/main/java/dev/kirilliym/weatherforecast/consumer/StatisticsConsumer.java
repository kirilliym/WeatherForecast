package dev.kirilliym.weatherforecast.consumer;

import dev.kirilliym.weatherforecast.avro.UserRequest;
import dev.kirilliym.weatherforecast.model.dto.PlaceDTO;
import dev.kirilliym.weatherforecast.service.PlaceService;
import dev.kirilliym.weatherforecast.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StatisticsConsumer {

    private final PlaceService placeService;
    private final RedisService redisService;

    @KafkaListener(topics = "user_request", groupId = "statistics-consumer-group")
    public void listen(ConsumerRecord<String, UserRequest> record) {
        UserRequest userRequest = record.value();

        PlaceDTO placeDTO = placeService.getPlaceById(userRequest.getPlaceId());
        String cityName = placeDTO.getCity().getName();

        redisService.incrementCityRequest(cityName);
        log.info("Incremented Redis counter for city: {}", cityName);
    }
}
