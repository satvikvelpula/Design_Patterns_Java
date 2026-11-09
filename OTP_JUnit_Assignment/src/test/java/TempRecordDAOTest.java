import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Legacy filename; conversion history is covered via {@link ConversionRecordDAO}.
 */
class TempRecordDAOTest {
    private static boolean dbAvailable;

    @BeforeAll
    static void checkDatabase() {
        try (Connection ignored = DBConnection.getConnection()) {
            dbAvailable = true;
        } catch (SQLException ex) {
            dbAvailable = false;
        }
    }

    @Test
    void recentHistoryQueryRuns() throws SQLException {
        Assumptions.assumeTrue(dbAvailable, "MariaDB not reachable — start: docker compose up -d");
        List<ConversionRecord> recent = new ConversionRecordDAO().findRecent(5, "en");
        assertTrue(recent.size() <= 5);
    }
}
