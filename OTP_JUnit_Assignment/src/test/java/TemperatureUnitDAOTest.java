import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assumptions;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TemperatureUnitDAOTest {
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
    void findAllReturnsSeededUnits() throws SQLException {
        Assumptions.assumeTrue(dbAvailable, "MariaDB not reachable — start: docker compose up -d db");
        List<TemperatureUnit> units = new TemperatureUnitDAO().findAll();
        assertFalse(units.isEmpty());
        assertTrue(units.stream().anyMatch(u -> "C".equals(u.getCode())));
    }

    @Test
    void findByCodeReturnsCelsius() throws SQLException {
        Assumptions.assumeTrue(dbAvailable, "MariaDB not reachable — start: docker compose up -d db");
        Optional<TemperatureUnit> unit = new TemperatureUnitDAO().findByCode("C");
        assertTrue(unit.isPresent());
        assertTrue(unit.get().getName().toLowerCase().contains("celsius"));
    }
}
