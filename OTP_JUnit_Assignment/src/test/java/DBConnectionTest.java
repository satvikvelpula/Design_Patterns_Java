import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DBConnectionTest {
    @Test
    void defaultsPointAtLocalMysqlAsRoot() {
        assertTrue(DBConnection.getUrl().contains("temperature_db"));
        assertTrue(DBConnection.getUrl().contains("characterEncoding=UTF-8"));
        assertEqualsIgnoringBlank("root", DBConnection.getUser());
    }

    @Test
    void getConnectionSucceedsWhenDatabaseIsUp() throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            assertNotNull(conn);
            assertFalse(conn.isClosed());
        } catch (SQLException ex) {
            Assumptions.assumeTrue(false, "MariaDB not reachable — start: docker compose up -d");
        }
    }

    private static void assertEqualsIgnoringBlank(String expected, String actual) {
        assertTrue(actual != null && !actual.isBlank());
        org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
    }
}
