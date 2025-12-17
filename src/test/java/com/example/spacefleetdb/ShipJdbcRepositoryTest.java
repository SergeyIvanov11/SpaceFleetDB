package com.example.spacefleetdb;

import com.example.spacefleetdb.dto.ShipDamageDto;
import com.example.spacefleetdb.repository.ShipJdbcRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class ShipJdbcRepositoryTest extends AbstractIntegrationTest {
    @Autowired
    ShipJdbcRepository repo;

    @Test
    void findTop3ShipsByDamage() {
        List<ShipDamageDto> ships = repo.findTop3ShipsByDamage();
        assertThat(ships).hasSize(3);
    }

    @Test
    void shouldReturnExactlyTop3ShipsOrderedByDamage() {
        List<ShipDamageDto> ships = repo.findTop3ShipsByDamage();

        assertThat(ships)
                .extracting(ShipDamageDto::getTotalDamage)
                .isSortedAccordingTo(Comparator.reverseOrder());
    }
}
