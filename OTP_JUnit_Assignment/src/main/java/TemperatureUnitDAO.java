import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TemperatureUnitDAO {

    public List<TemperatureUnit> findAll() throws SQLException {
        String sql = "SELECT id, code, name FROM temperature_unit ORDER BY id";
        List<TemperatureUnit> units = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                units.add(mapRow(rs));
            }
        }
        return units;
    }

    public Optional<TemperatureUnit> findByCode(String code) throws SQLException {
        String sql = "SELECT id, code, name FROM temperature_unit WHERE code = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    private TemperatureUnit mapRow(ResultSet rs) throws SQLException {
        return new TemperatureUnit(
                rs.getInt("id"),
                rs.getString("code"),
                rs.getString("name")
        );
    }
}
