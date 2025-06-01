package dev.kirilliym.weatherforecast.mapper;

import dev.kirilliym.weatherforecast.model.dto.TypoDTO;
import dev.kirilliym.weatherforecast.model.entity.Typo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TypoMapper {

    private final PlaceMapper placeMapper;

    public TypoDTO mapToDTO(Typo typo) {
        return new TypoDTO(
                typo.getWrong(),
                placeMapper.mapToDTO(typo.getPlace())
        );
    }

    public Typo mapToEntity(TypoDTO dto) {
        return new Typo(
                dto.getWrong(),
                placeMapper.mapToEntity(dto.getPlace())
        );
    }
}
