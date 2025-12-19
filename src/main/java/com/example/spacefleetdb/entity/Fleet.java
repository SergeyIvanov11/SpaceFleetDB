package com.example.spacefleetdb.entity;

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
@Table(name = "fleet")
public class Fleet {
    /*
     id             SERIAL PRIMARY KEY,
    name           VARCHAR(50) NOT NULL UNIQUE,
    alien_race     VARCHAR(50) NOT NULL,
    established_at DATE        NOT NULL,
    active         BOOLEAN     NOT NULL DEFAULT TRUE
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true, length = 50)
    String name;

    @Column(name = "alien_race", nullable = false, length = 50)
    String alienRace;

    @Column(name = "established_at", nullable = false)
    LocalDate establishedAt;

    @Column(nullable = false)
    Boolean active;
}
