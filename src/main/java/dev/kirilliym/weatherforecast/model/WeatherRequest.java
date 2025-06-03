package dev.kirilliym.weatherforecast.model;

import java.time.LocalDate;

public record WeatherRequest(Long placeId, LocalDate date) {
}
