import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UnitDAOTest {
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
    void findTemperatureUnitsReturnsLocalizedFinnishNames() throws SQLException {
        Assumptions.assumeTrue(dbAvailable, "MariaDB not reachable — start: docker compose up -d");
        UnitDAO dao = new UnitDAO();
        List<Unit> units = dao.findTemperatureUnits("fi");
        assertFalse(units.isEmpty());
        Optional<Unit> celsius = units.stream().filter(u -> "C".equals(u.getCode())).findFirst();
        assertTrue(celsius.isPresent());
        assertEquals("Celsius", celsius.get().getName()); // Finnish seed uses same word
        assertEquals("fi", celsius.get().getLang());
    }

    @Test
    void findTemperatureUnitsFallsBackToEnglishForUnknownLanguage() throws SQLException {
        Assumptions.assumeTrue(dbAvailable, "MariaDB not reachable — start: docker compose up -d");
        UnitDAO dao = new UnitDAO();
        List<Unit> units = dao.findTemperatureUnits("zz");
        assertFalse(units.isEmpty());
        Optional<Unit> fahrenheit = units.stream().filter(u -> "F".equals(u.getCode())).findFirst();
        assertTrue(fahrenheit.isPresent());
        assertEquals("Fahrenheit", fahrenheit.get().getName());
        assertEquals("en", fahrenheit.get().getLang());
    }

    @Test
    void findByCodeReturnsArabicName() throws SQLException {
        Assumptions.assumeTrue(dbAvailable, "MariaDB not reachable — start: docker compose up -d");
        UnitDAO dao = new UnitDAO();
        Optional<Unit> kelvin = dao.findByCode("K", "ar");
        assertTrue(kelvin.isPresent());
        assertEquals("كلفن", kelvin.get().getName());
        assertEquals("ar", kelvin.get().getLang());
    }
}
