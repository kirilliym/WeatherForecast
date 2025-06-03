package dev.kirilliym.weatherforecast.mapper;

import dev.kirilliym.weatherforecast.model.dto.PlaceDTO;
import dev.kirilliym.weatherforecast.model.entity.Place;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlaceMapper {
    private final CityMapper cityMapper;

    public PlaceDTO mapToDTO(Place place) {
        return new PlaceDTO(
                place.getId(),
                cityMapper.mapToDTO(place.getCity()),
                place.getName(),
                place.getLat(),
                place.getLon()
        );
    }

    public Place mapToEntity(PlaceDTO dto) {
        return new Place(
                dto.getId(),
                cityMapper.mapToEntity(dto.getCity()),
                dto.getName(),
                dto.getLat(),
                dto.getLon()
        );
    }
}
