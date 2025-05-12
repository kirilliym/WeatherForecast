package dev.kirilliym.weatherforecast.mapper;

import dev.kirilliym.weatherforecast.model.dto.CityDTO;
import dev.kirilliym.weatherforecast.model.entity.City;
import org.springframework.stereotype.Component;

@Component
public class CityMapper {

    public CityDTO mapToDTO(City city) {
        return new CityDTO(city.getId(), city.getName());
    }

    public City mapToEntity(CityDTO cityDTO) {
        return new City(cityDTO.getId(), cityDTO.getName());
    }

}
