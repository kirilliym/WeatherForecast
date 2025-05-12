package dev.kirilliym.weatherforecast.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDTO {
    private Integer id;
    private CityDTO city;
    private int temperature;
}
