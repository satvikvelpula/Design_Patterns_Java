import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConversionRecordDAO {

    public ConversionRecord insert(ConversionRecord record) throws SQLException {
        String sql = """
                INSERT INTO conversion_record
                    (input_value, from_unit_id, to_unit_id, result_value, lang)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDouble(1, record.getInputValue());
            ps.setInt(2, record.getFromUnitId());
            ps.setInt(3, record.getToUnitId());
            ps.setDouble(4, record.getResultValue());
            ps.setString(5, record.getLang());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    record.setId(keys.getInt(1));
                }
            }
        }
        return record;
    }

    /**
     * Last {@code limit} conversions with localized unit names for {@code lang}
     * (English fallback via COALESCE).
     */
    public List<ConversionRecord> findRecent(int limit, String lang) throws SQLException {
        String requested = (lang == null || lang.isBlank()) ? LocaleCodes.DEFAULT : lang;
        String sql = """
                SELECT c.id, c.input_value, c.from_unit_id, c.to_unit_id,
                       c.result_value, c.lang, c.recorded_at,
                       COALESCE(tf.name, ef.name) AS from_unit_name,
                       COALESCE(tt.name, et.name) AS to_unit_name
                FROM conversion_record c
                JOIN unit uf ON uf.id = c.from_unit_id
                JOIN unit ut ON ut.id = c.to_unit_id
                JOIN unit_translation ef ON ef.unit_id = uf.id AND ef.lang = ?
                JOIN unit_translation et ON et.unit_id = ut.id AND et.lang = ?
                LEFT JOIN unit_translation tf ON tf.unit_id = uf.id AND tf.lang = ?
                LEFT JOIN unit_translation tt ON tt.unit_id = ut.id AND tt.lang = ?
                ORDER BY c.recorded_at DESC
                LIMIT ?
                """;
        List<ConversionRecord> rows = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, LocaleCodes.DEFAULT);
            ps.setString(2, LocaleCodes.DEFAULT);
            ps.setString(3, requested);
            ps.setString(4, requested);
            ps.setInt(5, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ConversionRecord record = new ConversionRecord();
                    record.setId(rs.getInt("id"));
                    record.setInputValue(rs.getDouble("input_value"));
                    record.setFromUnitId(rs.getInt("from_unit_id"));
                    record.setToUnitId(rs.getInt("to_unit_id"));
                    record.setResultValue(rs.getDouble("result_value"));
                    record.setLang(rs.getString("lang"));
                    record.setRecordedAt(rs.getTimestamp("recorded_at"));
                    record.setFromUnitName(rs.getString("from_unit_name"));
                    record.setToUnitName(rs.getString("to_unit_name"));
                    rows.add(record);
                }
            }
        }
        return rows;
    }
}
