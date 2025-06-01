package dev.kirilliym.weatherforecast.repository;

import dev.kirilliym.weatherforecast.model.entity.City;
import dev.kirilliym.weatherforecast.model.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
}
