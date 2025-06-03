package dev.kirilliym.weatherforecast.service;

import com.fasterxml.jackson.databind.JsonNode;
import dev.kirilliym.weatherforecast.exception.OutOfWeatherRangeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class EolService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${WEATHER_TOKEN}")
    private String apiToken;

    private final String baseUrl = "https://projecteol.ru/api/weather/?lat=%s&lon=%s&date=%s&token=%s";

    public JsonNode getWeatherByCoordinates(double lat, double lon, String date) {
        String url = String.format(baseUrl, lat, lon, date, apiToken);
        try {
            return restTemplate.getForObject(url, JsonNode.class);
        }
        catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new OutOfWeatherRangeException();
            }
            throw e;
        }
    }
}
