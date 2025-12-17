package com.example.spacefleetdb.entity;

import com.example.spacefleetdb.entity.Enum.ShipCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ship")
public class Ship {
    /*
     id              SERIAL PRIMARY KEY,
    name            VARCHAR(50)   NOT NULL UNIQUE,
    fleet_id        INT           NOT NULL REFERENCES fleet (id) ON DELETE CASCADE,
    ship_type       ship_category NOT NULL,
    crew_capacity   INT           NOT NULL CHECK (crew_capacity > 0),
    commissioned_at DATE          NOT NULL,
    is_operational  BOOLEAN       NOT NULL DEFAULT TRUE
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true, length = 50)
    String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fleet_id", nullable = false)
    Fleet fleet;

    @Enumerated(EnumType.STRING)
    @Column(name = "ship_type", nullable = false)
    ShipCategory shipType;

    @Column(name = "crew_capacity", nullable = false)
    Integer crewCapacity;

    @Column(name = "commissioned_at", nullable = false)
    LocalDate commissionedAt;

    @Column(name = "is_operational", nullable = false)
    Boolean isOperational;
}
