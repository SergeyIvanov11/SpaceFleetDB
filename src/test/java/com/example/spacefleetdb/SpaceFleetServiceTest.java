package com.example.spacefleetdb;

import com.example.spacefleetdb.service.SpaceFleetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class SpaceFleetServiceTest extends AbstractIntegrationTest {

    @Autowired
    SpaceFleetService service;

    @Test
    void shouldReturnCrewByFleetName() {
        var crew = service.getCrewByFleetName("Orion Legion");
        assertThat(crew).isNotEmpty();
    }

    @Test
    void shouldReturnTop3ShipsByDamage() {
        var ships = service.getTop3ShipsByDamage();
        assertThat(ships).hasSize(3);
    }

    @Test
    void shouldReturnFleetStatistics() {
        var stats = service.getFleetStatistics();
        assertThat(stats).isNotEmpty();
    }
}
