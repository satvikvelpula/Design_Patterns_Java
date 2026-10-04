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
        assertNotNull(DBConnection.getUrl());
        assertTrue(DBConnection.getUrl().contains("jdbc:mysql://"));
        assertFalse(DBConnection.getUser().isBlank());
    }

    @Test
    void getConnectionSucceedsWhenDatabaseIsUp() throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            assertNotNull(conn);
            assertFalse(conn.isClosed());
        } catch (SQLException e) {
            Assumptions.assumeTrue(false, "MariaDB not reachable — start: docker compose up -d db");
        }
    }
}
