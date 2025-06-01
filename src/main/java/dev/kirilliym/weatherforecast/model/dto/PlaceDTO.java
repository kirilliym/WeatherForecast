package dev.kirilliym.weatherforecast.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDTO {
    private Long id;
    private CityDTO city;
    private String name;
    private Long requestCnt;
    private Double lat;
    private Double lon;
}
