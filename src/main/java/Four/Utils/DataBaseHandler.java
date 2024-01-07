package Four.Utils;

import Four.models.Client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBaseHandler {
    private static final String jdbcURL = "jdbc:mysql://localhost/sportclubdb";
    private static final String username = "root";
    private static final String password = "root";
    private static final String CREATE_CLIENT = "INSERT INTO clients (first_name, last_name, phone_number, visits_count) VALUES (?, ?, ?, ?)";
    private static final String CREATE_FILM = "INSERT INTO films (rating, title, user_id ) VALUE (?, ?, ?)";
private Connection connection;

    public  Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, username, password);
    }

    public DataBaseHandler() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveClient(Client client) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CLIENT);
            preparedStatement.setString(1, client.getFirstName());
            preparedStatement.setString(2, client.getLastName());
            preparedStatement.setString(3, client.getPhoneNumber());
            preparedStatement.setInt(4, client.getVisitsCount() );
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
