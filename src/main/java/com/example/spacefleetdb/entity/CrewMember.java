package com.example.spacefleetdb.entity;

import com.example.spacefleetdb.entity.Enum.RankEnum;
import com.example.spacefleetdb.entity.Enum.SpecializationEnum;
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
@Table(name = "crew_member")
public class CrewMember {
    /*
    id             SERIAL PRIMARY KEY,
    ship_id        INT                 NOT NULL REFERENCES ship (id) ON DELETE CASCADE,
    full_name      VARCHAR(100)        NOT NULL,
    rank           rank_enum           NOT NULL,
    specialization specialization_enum NOT NULL,
    birth_date     DATE                NOT NULL,
    is_active      BOOLEAN             NOT NULL DEFAULT TRUE
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ship_id", nullable = false)
    Ship ship;

    @Column(name = "full_name", nullable = false, length = 100)
    String fullName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    RankEnum rank;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    SpecializationEnum specialization;

    @Column(name = "birth_date", nullable = false)
    LocalDate birthDate;

    @Column(name = "is_active", nullable = false)
    Boolean isActive;
}
