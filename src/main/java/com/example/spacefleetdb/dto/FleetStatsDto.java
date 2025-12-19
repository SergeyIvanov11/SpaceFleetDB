package com.example.spacefleetdb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FleetStatsDto {
    Long fleetId;
    String fleetName;
    Integer shipCount;
    Integer crewCount;
    Integer totalDamage;
}
