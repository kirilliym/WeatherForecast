package dev.kirilliym.weatherforecast.model.response;

import java.time.LocalTime;

public record WeatherResponse(Long temperature, Long feelsTemperature, Long windSpeed, Long pres, Long vlaga, Long oblachnost_atmo, LocalTime time) {
}
