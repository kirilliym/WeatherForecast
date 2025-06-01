package dev.kirilliym.weatherforecast.service;

import dev.kirilliym.weatherforecast.mapper.CityMapper;
import dev.kirilliym.weatherforecast.model.dto.CityDTO;
import dev.kirilliym.weatherforecast.model.entity.City;
import dev.kirilliym.weatherforecast.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;
    private final CityMapper cityMapper;
    public CityDTO getCity(String city) {
        CityDTO cityDTO;

        Optional<City> cityOptional = cityRepository.findByName(city);

        if (cityOptional.isEmpty()) {
            City cityEntity = new City();
            cityEntity.setName(city);
            cityRepository.save(cityEntity);

            cityDTO = cityMapper.mapToDTO(cityEntity);
        }
        else {
            cityDTO = cityOptional.map(cityMapper::mapToDTO).get();
        }

        return cityDTO;
    }
}
