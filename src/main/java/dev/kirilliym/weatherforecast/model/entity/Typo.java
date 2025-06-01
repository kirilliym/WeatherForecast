package dev.kirilliym.weatherforecast.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "typo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Typo {

    @Id
    @Column(name = "wrong")
    private String wrong;

    @ManyToOne
    @JoinColumn(name = "place_id", referencedColumnName = "id")
    private Place place;
}
