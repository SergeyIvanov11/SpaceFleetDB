package com.example.spacefleetdb.repository;

import com.example.spacefleetdb.dto.FleetStatsDto;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.spacefleetdb.jooq.Tables.SHIP;
import static com.example.spacefleetdb.jooq.tables.CrewMember.CREW_MEMBER;
import static com.example.spacefleetdb.jooq.tables.Fleet.FLEET;
import static com.example.spacefleetdb.jooq.tables.ShipWeapon.SHIP_WEAPON;
import static com.example.spacefleetdb.jooq.tables.WeaponType.WEAPON_TYPE;

@Repository
@RequiredArgsConstructor
public class FleetJooqRepository {
    private final DSLContext dsl;

    public List<FleetStatsDto> getFleetStatistics() {
        return dsl
                .select(
                        FLEET.ID.as("fleetId"),
                        FLEET.NAME.as("fleetName"),
                        DSL.countDistinct(SHIP.ID).as("shipCount"),
                        DSL.countDistinct(CREW_MEMBER.ID).as("crewCount"),
                        DSL.coalesce(DSL.sum(WEAPON_TYPE.MAX_DAMAGE), 0).as("totalDamage")
                )
                .from(FLEET)
                .leftJoin(SHIP).on(SHIP.FLEET_ID.eq(FLEET.ID))
                .leftJoin(CREW_MEMBER).on(CREW_MEMBER.SHIP_ID.eq(SHIP.ID))
                .leftJoin(SHIP_WEAPON).on(SHIP_WEAPON.SHIP_ID.eq(SHIP.ID))
                .leftJoin(WEAPON_TYPE).on(WEAPON_TYPE.ID.eq(SHIP_WEAPON.WEAPON_TYPE_ID))
                .groupBy(FLEET.ID, FLEET.NAME)
                .fetchInto(FleetStatsDto.class);
    }


}
