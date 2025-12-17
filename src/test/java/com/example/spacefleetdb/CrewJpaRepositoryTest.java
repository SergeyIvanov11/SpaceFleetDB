package com.example.spacefleetdb;

import com.example.spacefleetdb.dto.CrewMemberDto;
import com.example.spacefleetdb.repository.CrewJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CrewJpaRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    CrewJpaRepository repo;

    @Test
    void findByFleetName() {
        List<CrewMemberDto> crew = repo.findByFleetName("Orion Legion");
        assertThat(crew).isNotEmpty();
    }

    @Test
    void shouldFindCrewByFleetName() {
        List<CrewMemberDto> crew = repo.findByFleetName("Orion Legion");

        CrewMemberDto member = crew.get(0);
        assertThat(member.getShipName()).isNotBlank();
        assertThat(member.getFullName()).isNotBlank();
        assertThat(member.getRank()).isNotNull();
    }
}
