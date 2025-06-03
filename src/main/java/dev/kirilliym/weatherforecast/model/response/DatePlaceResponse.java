package dev.kirilliym.weatherforecast.model.response;

import java.time.LocalDate;
import java.util.List;

public record DatePlaceResponse(String cityName, String placeName, LocalDate date, List<WeatherResponse> weather) {
}
