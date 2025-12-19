package com.example.spacefleetdb.entity;

import com.example.spacefleetdb.entity.Enum.WeaponStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "ship_weapon",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"ship_id", "weapon_type_id"}
        )
)
public class ShipWeapon {
    /*
    id             SERIAL PRIMARY KEY,
    ship_id        INT NOT NULL REFERENCES ship (id) ON DELETE CASCADE,
    weapon_type_id INT NOT NULL REFERENCES weapon_type (id),
    status         weapon_status DEFAULT 'ready',
    ammo_count     INT           DEFAULT 0 CHECK (ammo_count >= 0)
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ship_id", nullable = false)
    Ship ship;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "weapon_type_id", nullable = false)
    WeaponType weaponType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    WeaponStatus status;

    @Min(0)
    @Column(name = "ammo_count", nullable = false)
    Integer ammoCount;
}
