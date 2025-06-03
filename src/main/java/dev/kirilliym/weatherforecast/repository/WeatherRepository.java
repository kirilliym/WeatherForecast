package dev.kirilliym.weatherforecast.repository;

import dev.kirilliym.weatherforecast.model.entity.Place;
import dev.kirilliym.weatherforecast.model.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {
    Optional<Weather> findByPlace(Place place);

    List<Weather> findAllByPlaceIdAndDate(Long placeId, LocalDate date);
}
