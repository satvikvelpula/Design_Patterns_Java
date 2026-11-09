import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConversionRecordDAOTest {
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
    void insertAndFindRecentReturnsAtMostFive() throws SQLException {
        Assumptions.assumeTrue(dbAvailable, "MariaDB not reachable — start: docker compose up -d");
        UnitDAO unitDAO = new UnitDAO();
        Unit from = unitDAO.findByCode("C", "en").orElseThrow();
        Unit to = unitDAO.findByCode("F", "en").orElseThrow();

        ConversionRecordDAO dao = new ConversionRecordDAO();
        dao.insert(new ConversionRecord(100.0, from.getId(), to.getId(), 212.0, "en"));

        List<ConversionRecord> recent = dao.findRecent(5, "en");
        assertFalse(recent.isEmpty());
        assertTrue(recent.size() <= 5);
        assertEquals("Celsius", recent.get(0).getFromUnitName());
        assertEquals("Fahrenheit", recent.get(0).getToUnitName());
    }
}
