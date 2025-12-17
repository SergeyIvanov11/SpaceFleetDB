package com.example.spacefleetdb;

import com.example.spacefleetdb.dto.FleetStatsDto;
import com.example.spacefleetdb.repository.FleetJooqRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FleetJooqRepositoryTest extends AbstractIntegrationTest {
    @Autowired
    FleetJooqRepository repo;

    @Test
    void getFleetStatistics() {
        List<FleetStatsDto> stats = repo.getFleetStatistics();
        assertThat(stats).isNotEmpty();
    }

    @Test
    void shouldReturnFleetStatistics() {
        List<FleetStatsDto> stats = repo.getFleetStatistics();

        FleetStatsDto fleet = stats.get(0);
        assertThat(fleet.getFleetId()).isNotNull();
        assertThat(fleet.getFleetName()).isNotBlank();
        assertThat(fleet.getShipCount()).isGreaterThanOrEqualTo(0);
        assertThat(fleet.getCrewCount()).isGreaterThanOrEqualTo(0);
        assertThat(fleet.getTotalDamage()).isGreaterThanOrEqualTo(0);
    }
}
