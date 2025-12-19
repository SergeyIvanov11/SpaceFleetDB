package com.example.spacefleetdb.controller;

import com.example.spacefleetdb.dto.CrewMemberDto;
import com.example.spacefleetdb.dto.FleetStatsDto;
import com.example.spacefleetdb.dto.ShipDamageDto;
import com.example.spacefleetdb.entity.ShipWeapon;
import com.example.spacefleetdb.service.SpaceFleetService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/")
@RequiredArgsConstructor
public class SpaceFleetController {
    private final SpaceFleetService service;

    @Operation(summary = "Поиск всех членов экипажа по имени флота")
    @GetMapping("/{fleetName}/crew")
    public ResponseEntity<List<CrewMemberDto>> getCrewByFleet(@PathVariable String fleetName) {
        return ResponseEntity.ok(service.getCrewByFleetName(fleetName));
    }

    @Operation(summary = "Показать топ 3 корабля по max damage вооружения")
    @GetMapping("/top-dmg-ships")
    public ResponseEntity<List<ShipDamageDto>> getTop3ShipsByDamage() {
        return ResponseEntity.ok(service.getTop3ShipsByDamage());
    }

    @Operation(summary = "Показать статистику по флоту: кол-во кораблей, кол-во экипажей и тд")
    @GetMapping("/stats")
    public ResponseEntity<List<FleetStatsDto>> getFleetStats() {
        return ResponseEntity.ok(service.getFleetStatistics());
    }

    @Operation(summary = "Проверка LAZY и EAGER:\n" +
            "@ManyToOne(fetch = FetchType.LAZY)\n" +
            "    @JoinColumn(name = \"ship_id\", nullable = false)\n" +
            "    Ship ship;\n" +
            "\n" +
            "    @ManyToOne(fetch = FetchType.EAGER)\n" +
            "    @JoinColumn(name = \"weapon_type_id\", nullable = false)\n" +
            "    WeaponType weaponType;")
    @GetMapping("/shipweapon")
    public String findShipWeapon(Long id) {
        String res = service.findShipWeapon(id);
        System.out.println(res);
        return res;
    }

}
