package dev.kirilliym.weatherforecast.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeocodeService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${GEO_TOKEN}")
    private String apiKey;

    private final String baseUrl = "https://geocode-maps.yandex.ru/v1/?apikey=%s&geocode=%s&format=json";

    public JsonNode geocode(String location) {
        String url = String.format(baseUrl, apiKey, location);
        return restTemplate.getForObject(url, JsonNode.class);
    }
}
