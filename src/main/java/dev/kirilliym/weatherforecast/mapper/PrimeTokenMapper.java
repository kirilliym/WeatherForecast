package dev.kirilliym.weatherforecast.mapper;

import dev.kirilliym.weatherforecast.model.dto.PrimeTokenDTO;
import dev.kirilliym.weatherforecast.model.entity.PrimeToken;
import org.springframework.stereotype.Component;

@Component
public class PrimeTokenMapper {

    public PrimeTokenDTO mapToDTO(PrimeToken entity) {
        return new PrimeTokenDTO(
                entity.getId(),
                entity.getToken(),
                entity.getRemainOperations()
        );
    }

    public PrimeToken mapToEntity(PrimeTokenDTO dto) {
        return new PrimeToken(
                dto.getId(),
                dto.getToken(),
                dto.getRemainOperations()
        );
    }
}
