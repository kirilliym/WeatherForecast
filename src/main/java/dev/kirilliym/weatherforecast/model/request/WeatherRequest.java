package dev.kirilliym.weatherforecast.model.request;

import java.time.LocalDate;

public record WeatherRequest(Long placeId, LocalDate date) {
}
