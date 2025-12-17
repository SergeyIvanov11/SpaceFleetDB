package com.example.spacefleetdb.repository;

import com.example.spacefleetdb.dto.FleetStatsDto;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FleetJooqRepository {
    private final DSLContext dsl;

    public List<FleetStatsDto> getFleetStatistics() {
        return dsl.fetch("""
            SELECT 
                f.id as fleet_id,
                f.name as fleet_name,
                COUNT(DISTINCT s.id) as ship_count,
                COUNT(DISTINCT cm.id) as crew_count,
                COALESCE(SUM(w.max_damage), 0) as total_damage
            FROM fleet f
            LEFT JOIN ship s ON s.fleet_id = f.id
            LEFT JOIN crew_member cm ON cm.ship_id = s.id
            LEFT JOIN ship_weapon sw ON sw.ship_id = s.id
            LEFT JOIN weapon_type w ON w.id = sw.weapon_type_id
            GROUP BY f.id, f.name
            """)
            .into(FleetStatsDto.class);
    }
}
