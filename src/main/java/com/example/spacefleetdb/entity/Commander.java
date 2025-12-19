package com.example.spacefleetdb.entity;

import com.example.spacefleetdb.entity.Enum.RankEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "commander")
public class Commander {
    /*
        id                SERIAL PRIMARY KEY,
        full_name         VARCHAR(100) NOT NULL,
        rank              rank_enum    NOT NULL,
        assigned_fleet_id INT REFERENCES fleet (id),
        assigned_ship_id  INT REFERENCES ship (id),
        CHECK (
            (assigned_fleet_id IS NOT NULL AND assigned_ship_id IS NULL)
        OR
                (assigned_fleet_id IS NULL AND assigned_ship_id IS NOT NULL)
            )

     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "full_name", nullable = false, length = 100)
    String fullName;

    @Enumerated(EnumType.STRING)
    @Column(name = "rank", nullable = false)
    RankEnum rank;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_fleet_id")
    Fleet assignedFleet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_ship_id")
    Ship assignedShip;

}
