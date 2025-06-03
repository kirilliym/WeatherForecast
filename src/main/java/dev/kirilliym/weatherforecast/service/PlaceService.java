package dev.kirilliym.weatherforecast.service;

import com.fasterxml.jackson.databind.JsonNode;
import dev.kirilliym.weatherforecast.exception.CityNotFoundException;
import dev.kirilliym.weatherforecast.exception.InvalidPlaceNameException;
import dev.kirilliym.weatherforecast.mapper.CityMapper;
import dev.kirilliym.weatherforecast.mapper.PlaceMapper;
import dev.kirilliym.weatherforecast.mapper.TypoMapper;
import dev.kirilliym.weatherforecast.model.dto.CityDTO;
import dev.kirilliym.weatherforecast.model.dto.PlaceDTO;
import dev.kirilliym.weatherforecast.model.entity.Place;
import dev.kirilliym.weatherforecast.model.entity.Typo;
import dev.kirilliym.weatherforecast.repository.PlaceRepository;
import dev.kirilliym.weatherforecast.repository.TypoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceMapper placeMapper;
    private final PlaceRepository placeRepository;
    private final TypoRepository typoRepository;
    private final TypoMapper typoMapper;
    private final GeocodeService geocodeService;
    private final CityService cityService;
    private final CityMapper cityMapper;
    public PlaceDTO getPlace(String place) {
        PlaceDTO placeDTO;

        Optional<Typo> optionalTypo = typoRepository.findByWrong(place);
        if (optionalTypo.isEmpty()) {
            JsonNode response = geocodeService.geocode(place);

            int found = response
                    .path("response")
                    .path("GeoObjectCollection")
                    .path("metaDataProperty")
                    .path("GeocoderResponseMetaData")
                    .path("found")
                    .asInt();

            if (found == 0) {
                throw new InvalidPlaceNameException();
            }

            JsonNode geoObject = response
                    .path("response")
                    .path("GeoObjectCollection")
                    .path("featureMember")
                    .get(0)
                    .path("GeoObject");

            String cityName = geoObject
                    .path("metaDataProperty")
                    .path("GeocoderMetaData")
                    .path("AddressDetails")
                    .path("Country")
                    .path("AdministrativeArea")
                    .path("SubAdministrativeArea")
                    .path("Locality")
                    .path("LocalityName")
                    .asText();

            if (cityName.isEmpty()) {
                cityName = geoObject
                        .path("metaDataProperty")
                        .path("GeocoderMetaData")
                        .path("AddressDetails")
                        .path("Country")
                        .path("AdministrativeArea")
                        .path("AdministrativeAreaName")
                        .asText();
            }

            if (cityName.isEmpty()) {
                throw new CityNotFoundException();
            }

            CityDTO cityDTO = cityService.getCity(cityName);

            String addressLine = geoObject
                    .path("metaDataProperty")
                    .path("GeocoderMetaData")
                    .path("AddressDetails")
                    .path("Country")
                    .path("AddressLine")
                    .asText();

            String pos = geoObject.path("Point").path("pos").asText();
            String[] parts = pos.split(" ");
            double lon = Double.parseDouble(parts[0]);
            double lat = Double.parseDouble(parts[1]);

            Place placeEntity = new Place();
            placeEntity.setName(addressLine);
            placeEntity.setCity(cityMapper.mapToEntity(cityDTO));
            placeEntity.setLat(lat);
            placeEntity.setLon(lon);
            placeRepository.save(placeEntity);

            Typo typo = new Typo();
            typo.setPlace(placeEntity);
            typo.setWrong(place);
            typoRepository.save(typo);

            placeDTO = placeMapper.mapToDTO(placeEntity);
        }
        else {
            placeDTO = optionalTypo.map(typoMapper::mapToDTO).get().getPlace();
        }

        return placeDTO;
    }

    public PlaceDTO getPlaceById(Long id) {
        return placeRepository.findById(id).map(placeMapper::mapToDTO).get();
    }
}
