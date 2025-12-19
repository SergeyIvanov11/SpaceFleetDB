package com.example.spacefleetdb.repository;

import com.example.spacefleetdb.entity.ShipWeapon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipWeaponRepository extends JpaRepository<ShipWeapon, Long> {

}
