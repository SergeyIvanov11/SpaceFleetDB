package com.example.spacefleetdb.entity;

import com.example.spacefleetdb.entity.Enum.WeaponClass;
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
@Table(name = "weapon_type")
public class WeaponType {
    /*
     id         SERIAL PRIMARY KEY,
    title      VARCHAR(100) NOT NULL UNIQUE,
    class      weapon_class NOT NULL,
    max_damage INT CHECK (max_damage > 0)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true, length = 100)
    String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "class", nullable = false)
    WeaponClass weaponClass;

    @Min(1)
    @Column(name = "max_damage")
    Integer maxDamage;
}
