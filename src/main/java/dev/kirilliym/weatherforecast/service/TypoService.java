package dev.kirilliym.weatherforecast.service;

import com.fasterxml.jackson.databind.JsonNode;
import dev.kirilliym.weatherforecast.exception.CityNotFoundException;
import dev.kirilliym.weatherforecast.exception.InvalidPlaceNameException;
import dev.kirilliym.weatherforecast.mapper.CityMapper;
import dev.kirilliym.weatherforecast.mapper.TypoMapper;
import dev.kirilliym.weatherforecast.model.dto.CityDTO;
import dev.kirilliym.weatherforecast.model.dto.TypoDTO;
import dev.kirilliym.weatherforecast.model.entity.Place;
import dev.kirilliym.weatherforecast.model.entity.Typo;
import dev.kirilliym.weatherforecast.repository.PlaceRepository;
import dev.kirilliym.weatherforecast.repository.TypoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TypoService {
    private final TypoRepository typoRepository;
    private final TypoMapper typoMapper;
    private final GeocodeService geocodeService;
    private final CityService cityService;
    private final CityMapper cityMapper;
    private final PlaceRepository placeRepository;

    public TypoDTO getTypo(String place) {
        TypoDTO typoDTO;

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

            Place placeEntity = placeRepository.findByName(addressLine)
                    .orElseGet(() -> {
                        Place newPlace = new Place();
                        newPlace.setName(addressLine);
                        newPlace.setCity(cityMapper.mapToEntity(cityDTO));
                        newPlace.setLat(lat);
                        newPlace.setLon(lon);
                        return placeRepository.save(newPlace);
                    });

            Typo typo = new Typo();
            typo.setPlace(placeEntity);
            typo.setWrong(place);
            typoRepository.save(typo);

            typoDTO = typoMapper.mapToDTO(typo);
        }
        else {
            typoDTO = typoMapper.mapToDTO(optionalTypo.get());
        }

        return typoDTO;
    }
}
