package dev.kirilliym.weatherforecast.repository;

import dev.kirilliym.weatherforecast.model.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findAllByDateBetween(LocalDate start, LocalDate end);
}
