import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * JDBC connector. Credentials from environment variables (Java: System.getenv),
 * with local MySQL root defaults.
 */
public class DBConnection {
    private static final String URL = getenv(
            "DB_URL",
            "jdbc:mysql://localhost:3306/temperature_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
    );
    private static final String USER = getenv("DB_USER", "root");
    private static final String PASSWORD = getenv("DB_PASSWORD", "root");

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static String getUrl() {
        return URL;
    }

    public static String getUser() {
        return USER;
    }

    private static String getenv(String key, String defaultValue) {
        String value = System.getenv(key);
        return (value == null || value.isBlank()) ? defaultValue : value;
    }
}
