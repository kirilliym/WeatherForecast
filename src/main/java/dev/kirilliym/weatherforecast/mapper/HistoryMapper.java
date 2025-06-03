package dev.kirilliym.weatherforecast.mapper;

import dev.kirilliym.weatherforecast.model.dto.HistoryDTO;
import dev.kirilliym.weatherforecast.model.entity.History;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HistoryMapper {
    private final PlaceMapper placeMapper;

    public HistoryDTO mapToDTO(History history) {
        return new HistoryDTO(
                history.getId(),
                history.getRequest(),
                history.getRequest_date(),
                placeMapper.mapToDTO(history.getPlace()),
                history.getDate(),
                history.getTime()
        );
    }

    public History mapToEntity(HistoryDTO dto) {
        return new History(
                dto.getId(),
                dto.getRequest(),
                dto.getRequest_date(),
                placeMapper.mapToEntity(dto.getPlace()),
                dto.getDate(),
                dto.getTime()
        );
    }
}
