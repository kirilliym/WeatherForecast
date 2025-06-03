package dev.kirilliym.weatherforecast.repository;

import dev.kirilliym.weatherforecast.model.entity.PrimeToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrimeTokenRepository extends JpaRepository<PrimeToken, Long> {
    Optional<PrimeToken> findByToken(String token);
}
