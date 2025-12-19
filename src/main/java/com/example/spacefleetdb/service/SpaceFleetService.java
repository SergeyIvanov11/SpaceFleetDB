package com.example.spacefleetdb.service;

import com.example.spacefleetdb.dto.CrewMemberDto;
import com.example.spacefleetdb.dto.FleetStatsDto;
import com.example.spacefleetdb.dto.ShipDamageDto;
import com.example.spacefleetdb.entity.ShipWeapon;
import com.example.spacefleetdb.repository.CrewJpaRepository;
import com.example.spacefleetdb.repository.FleetJooqRepository;
import com.example.spacefleetdb.repository.ShipJdbcRepository;
import com.example.spacefleetdb.repository.ShipWeaponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpaceFleetService {
    private final CrewJpaRepository crewJpaRepository;
    private final ShipJdbcRepository shipJdbcRepository;
    private final FleetJooqRepository fleetJooqRepository;
    private final ShipWeaponRepository shipWeaponRepository;

    // Поиск всех членов экипажа по имени флота
    public List<CrewMemberDto> getCrewByFleetName(String fleetName) {
        return crewJpaRepository.findByFleetName(fleetName);
    }

    //Показать топ 3 корабля по max damage вооружения
    public List<ShipDamageDto> getTop3ShipsByDamage() {
        return shipJdbcRepository.findTop3ShipsByDamage();
    }

    //Показать статистику по флоту: кол-во кораблей, кол-во экипажей и тд
    public List<FleetStatsDto> getFleetStatistics() {
        return fleetJooqRepository.getFleetStatistics();
    }

    public String findShipWeapon(Long id) {
        return shipWeaponRepository.findById(id).get().toString();
    }
}
