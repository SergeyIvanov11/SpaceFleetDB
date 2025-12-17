package com.example.spacefleetdb.repository;

import com.example.spacefleetdb.dto.CrewMemberDto;
import com.example.spacefleetdb.entity.CrewMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrewJpaRepository extends JpaRepository<CrewMember, Long> {
    @Query("""
                SELECT new com.example.spacefleetdb.dto.CrewMemberDto(
                    cm.id,
                    s.name,
                    cm.fullName,
                    cm.rank,
                    cm.specialization
                )
                FROM CrewMember cm
                JOIN cm.ship s
                JOIN s.fleet f
                WHERE f.name = :fleetName
            """)
    List<CrewMemberDto> findByFleetName(@Param("fleetName") String fleetName);
}
