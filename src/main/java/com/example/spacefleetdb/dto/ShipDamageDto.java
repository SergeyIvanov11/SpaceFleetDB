package com.example.spacefleetdb.dto;

import com.example.spacefleetdb.entity.Enum.ShipCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShipDamageDto {
    Long shipId;
    String shipName;
    String fleetName;
    ShipCategory shipType;
    Integer totalDamage;
}
