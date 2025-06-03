package dev.kirilliym.weatherforecast.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "prime_token")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrimeToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String token;

    @Column(nullable = false)
    private Integer remainOperations;
}
