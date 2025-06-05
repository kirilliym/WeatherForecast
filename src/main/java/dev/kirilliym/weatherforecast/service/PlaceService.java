package dev.kirilliym.weatherforecast.service;

import com.github.benmanes.caffeine.cache.Cache;
import dev.kirilliym.weatherforecast.mapper.PlaceMapper;
import dev.kirilliym.weatherforecast.model.dto.PlaceDTO;
import dev.kirilliym.weatherforecast.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceMapper placeMapper;
    private final PlaceRepository placeRepository;
    private final TypoService typoService;
    private final CacheService cacheService;


    private final Cache<String, String> typoCache;
    private final Cache<String, PlaceDTO> placeCache;

    public PlaceDTO getPlace(String place) {
        String placeWithoutTypo = typoCache.getIfPresent(place);

        if (placeWithoutTypo == null) {
            PlaceDTO cachedPlace = placeCache.getIfPresent(place);
            if (cachedPlace != null) {
                return cachedPlace;
            }

            PlaceDTO resolvedPlace = typoService.getTypo(place).getPlace();
            placeWithoutTypo = resolvedPlace.getName();
            String cityName = resolvedPlace.getCity().getName();

            if (cacheService.getHotCities().contains(cityName)) {
                typoCache.put(place, placeWithoutTypo);
                placeCache.put(placeWithoutTypo, resolvedPlace);
            }

            return resolvedPlace;
        }

        PlaceDTO cachedCorrect = placeCache.getIfPresent(placeWithoutTypo);
        if (cachedCorrect != null) {
            return cachedCorrect;
        }

        PlaceDTO resolvedPlace = typoService.getTypo(place).getPlace();
        String cityName = resolvedPlace.getCity().getName();

        if (cacheService.getHotCities().contains(cityName)) {
            placeCache.put(placeWithoutTypo, resolvedPlace);
        }

        return resolvedPlace;
    }

    public PlaceDTO getPlaceById(Long id) {
        return placeRepository.findById(id)
                .map(placeMapper::mapToDTO)
                .orElseThrow(() -> new IllegalArgumentException("Place with id " + id + " not found"));
    }

}
