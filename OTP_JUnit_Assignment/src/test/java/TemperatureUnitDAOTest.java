import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Legacy filename kept for continuity; covers temperature units via {@link UnitDAO}.
 */
class TemperatureUnitDAOTest {
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
    void temperatureCategoryHasSeededUnits() throws SQLException {
        Assumptions.assumeTrue(dbAvailable, "MariaDB not reachable — start: docker compose up -d");
        List<Unit> units = new UnitDAO().findTemperatureUnits("en");
        assertFalse(units.isEmpty());
    }
}
