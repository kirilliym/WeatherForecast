package dev.kirilliym.weatherforecast.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypoDTO {
    private String wrong;
    private PlaceDTO place;
}
