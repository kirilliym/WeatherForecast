package dev.kirilliym.weatherforecast.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrimeTokenDTO {
    private Long id;
    private String token;
    private Integer remainOperations;
}
