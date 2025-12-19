package com.example.spacefleetdb.repository;

import com.example.spacefleetdb.dto.ShipDamageDto;
import com.example.spacefleetdb.entity.Enum.ShipCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ShipJdbcRepository {
    private final DataSource dataSource;

    public List<ShipDamageDto> findTop3ShipsByDamage() {

        String sql = """
            SELECT
                            s.id AS ship_id,
                            s.name AS ship_name,
                            f.name AS fleet_name,
                            s.ship_type AS ship_type,
                            SUM(w.max_damage) AS total_damage
                        FROM ship s
                        JOIN fleet f ON f.id = s.fleet_id
                        JOIN ship_weapon sw ON sw.ship_id = s.id
                        JOIN weapon_type w ON w.id = sw.weapon_type_id
                        GROUP BY s.id, s.name, f.name, s.ship_type
                        ORDER BY total_damage DESC
                        LIMIT 3
        """;

        List<ShipDamageDto> result = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.add(new ShipDamageDto(
                        rs.getLong("ship_id"),
                        rs.getString("ship_name"),
                        rs.getString("fleet_name"),
                        ShipCategory.valueOf(rs.getString("ship_type")),
                        rs.getInt("total_damage")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
