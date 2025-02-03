package compiler;

import java.sql.*;

/**
 * SQLHandler - Executes SQL queries within COBOL EXEC SQL statements.
 */
public class SQLHandler {
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    /**
     * Executes a SELECT query and returns a result.
     */
    public static String executeQuery(String query, Object... params) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        }
        return null;
    }

    /**
     * Executes an INSERT, UPDATE, or DELETE query.
     */
    public static void executeUpdate(String query, Object... params) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            stmt.executeUpdate();
        }
    }
}
