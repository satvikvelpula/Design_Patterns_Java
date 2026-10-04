import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TempRecordDAO {

    public TempRecord insert(TempRecord record) throws SQLException {
        String sql = "INSERT INTO temp_record (value, unit_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDouble(1, record.getValue());
            ps.setInt(2, record.getUnitId());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    record.setId(keys.getInt(1));
                }
            }
        }
        return record;
    }

    public List<TempRecord> findRecent(int limit) throws SQLException {
        String sql = """
                SELECT r.id, r.value, r.unit_id, u.code AS unit_code, r.recorded_at
                FROM temp_record r
                JOIN temperature_unit u ON u.id = r.unit_id
                ORDER BY r.recorded_at DESC
                LIMIT ?
                """;
        List<TempRecord> records = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TempRecord record = new TempRecord();
                    record.setId(rs.getInt("id"));
                    record.setValue(rs.getDouble("value"));
                    record.setUnitId(rs.getInt("unit_id"));
                    record.setUnitCode(rs.getString("unit_code"));
                    record.setRecordedAt(rs.getTimestamp("recorded_at"));
                    records.add(record);
                }
            }
        }
        return records;
    }
}
