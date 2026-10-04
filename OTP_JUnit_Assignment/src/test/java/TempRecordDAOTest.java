import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TempRecordDAOTest {
    private static boolean dbAvailable;

    @BeforeAll
    static void checkDatabase() {
        try (Connection ignored = DBConnection.getConnection()) {
            dbAvailable = true;
        } catch (SQLException e) {
            dbAvailable = false;
        }
    }

    @Test
    void insertAndFindRecent() throws SQLException {
        Assumptions.assumeTrue(dbAvailable, "MariaDB not reachable — start: docker compose up -d db");

        TemperatureUnit celsius = new TemperatureUnitDAO().findByCode("C")
                .orElseThrow();
        TempRecordDAO dao = new TempRecordDAO();

        TempRecord saved = dao.insert(new TempRecord(21.5, celsius.getId()));
        assertTrue(saved.getId() > 0);

        List<TempRecord> recent = dao.findRecent(10);
        assertTrue(recent.stream().anyMatch(r -> r.getId() == saved.getId()));
        TempRecord match = recent.stream().filter(r -> r.getId() == saved.getId()).findFirst().orElseThrow();
        assertEquals(21.5, match.getValue(), 0.0001);
        assertEquals("C", match.getUnitCode());
    }
}
