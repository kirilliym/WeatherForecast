package dev.kirilliym.weatherforecast.repository;

import dev.kirilliym.weatherforecast.model.entity.Typo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypoRepository extends JpaRepository<Typo, String> {
    Optional<Typo> findByWrong(String wrong);
}
