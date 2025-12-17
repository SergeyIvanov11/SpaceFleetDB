package com.example.spacefleetdb.dto;

import com.example.spacefleetdb.entity.Enum.RankEnum;
import com.example.spacefleetdb.entity.Enum.SpecializationEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CrewMemberDto {
    Long id;
    String shipName;
    String fullName;
    RankEnum rank;
    SpecializationEnum specialization;
}
