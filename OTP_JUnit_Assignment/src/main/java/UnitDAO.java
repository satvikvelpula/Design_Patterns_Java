import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Loads units with localized name/description via translation table.
 * Falls back to English when the requested language row is missing.
 */
public class UnitDAO {

    private static final String LOCALIZED_UNITS_SQL = """
            SELECT u.id, u.code, u.category, u.symbol,
                   COALESCE(t.name, en.name) AS name,
                   COALESCE(t.description, en.description) AS description,
                   COALESCE(t.lang, en.lang) AS lang
            FROM unit u
            JOIN unit_translation en
              ON en.unit_id = u.id AND en.lang = ?
            LEFT JOIN unit_translation t
              ON t.unit_id = u.id AND t.lang = ?
            WHERE u.category = ?
            ORDER BY u.id
            """;

    public List<Unit> findByCategory(String category, String lang) throws SQLException {
        String requested = (lang == null || lang.isBlank()) ? LocaleCodes.DEFAULT : lang;
        List<Unit> units = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(LOCALIZED_UNITS_SQL)) {
            ps.setString(1, LocaleCodes.DEFAULT);
            ps.setString(2, requested);
            ps.setString(3, category);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    units.add(mapRow(rs));
                }
            }
        }
        return units;
    }

    public List<Unit> findTemperatureUnits(String lang) throws SQLException {
        return findByCategory("temperature", lang);
    }

    public Optional<Unit> findByCode(String code, String lang) throws SQLException {
        String requested = (lang == null || lang.isBlank()) ? LocaleCodes.DEFAULT : lang;
        String sql = """
                SELECT u.id, u.code, u.category, u.symbol,
                       COALESCE(t.name, en.name) AS name,
                       COALESCE(t.description, en.description) AS description,
                       COALESCE(t.lang, en.lang) AS lang
                FROM unit u
                JOIN unit_translation en
                  ON en.unit_id = u.id AND en.lang = ?
                LEFT JOIN unit_translation t
                  ON t.unit_id = u.id AND t.lang = ?
                WHERE u.code = ?
                """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, LocaleCodes.DEFAULT);
            ps.setString(2, requested);
            ps.setString(3, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    private Unit mapRow(ResultSet rs) throws SQLException {
        return new Unit(
                rs.getInt("id"),
                rs.getString("code"),
                rs.getString("category"),
                rs.getString("symbol"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getString("lang")
        );
    }
}
