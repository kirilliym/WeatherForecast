package dev.kirilliym.weatherforecast.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(name = "weather")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "place_id", referencedColumnName = "id")
    private Place place;

    @Column(name = "temp_2_cel")
    private Long temp2Cel;

    @Column(name = "temp_feels_cel")
    private Long tempFeelsCel;

    @Column(name = "wind_speed_10")
    private Long windSpeed10;

    @Column(name = "pres_surf")
    private Long presSurf;

    @Column(name = "vlaga_2f")
    private Long vlaga2f;

    @Column(name = "oblachnost_atmo")
    private Long oblachnost_atmo;

    @Column
    private LocalDateTime updated;

    @Column
    private LocalDate date;

    @Column
    private LocalTime time;
}
