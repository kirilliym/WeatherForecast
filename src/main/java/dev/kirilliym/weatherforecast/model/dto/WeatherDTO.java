package dev.kirilliym.weatherforecast.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDTO {
    private Long id;
    private PlaceDTO place;
    private Long temp2Cel;
    private Long tempFeelsCel;
    private Long windSpeed10;
    private Long presSurf;
    private Long vlaga2f;
    private Long oblachnost_atmo;
    private LocalDateTime updated;
    private LocalDate date;
    private LocalTime time;
}
