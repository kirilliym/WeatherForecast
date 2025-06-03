package dev.kirilliym.weatherforecast.producer;

import dev.kirilliym.weatherforecast.avro.UserRequest;
import dev.kirilliym.weatherforecast.model.dto.PlaceDTO;
import dev.kirilliym.weatherforecast.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class UserRequestProducer {

    private final KafkaTemplate<String, SpecificRecord> kafkaTemplate;
    private final PlaceService placeService;

    private static final String TOPIC = "user_request";

    public void send(String request, LocalDate date) {
        PlaceDTO placeDTO = placeService.getPlace(request);

        UserRequest userRequest = UserRequest.newBuilder()
                .setRequest(request)
                .setPlaceId(placeDTO.getId())
                .setRequestDate(date.toString())
                .setDate(LocalDate.now().toString())
                .setTime(LocalTime.now().toString())
                .build();

        kafkaTemplate.send(TOPIC, userRequest);
    }
}
