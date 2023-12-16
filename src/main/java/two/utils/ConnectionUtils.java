package two.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtils {
    private static final String jdbcURL = "jdbc:mysql://localhost/cinema";
    private static final String username = "root";
    private static final String password = "root";
    public static Connection openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(jdbcURL, username, password);
    }
}
