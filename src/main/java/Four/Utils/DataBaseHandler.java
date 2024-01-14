package Four.Utils;

import Four.models.Client;
import lombok.Getter;

import java.sql.*;

@Getter
public class DataBaseHandler {
    private static final String jdbcURL = "jdbc:mysql://localhost/sportclubdb";
    private static final String username = "root";
    private static final String password = "root";
    private static final String CREATE_CLIENT = "INSERT INTO clients (first_name, last_name, phone_number, visits_count) VALUES (?, ?, ?, ?)";

    private static final String GET_CLIENT_BY_PHONENUMBER = "SELECT * FROM clients WHERE phone_number = ? ";
    private Connection connection;

    public DataBaseHandler() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveClient(Client client) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CLIENT);
            preparedStatement.setString(1, client.getFirstName());
            preparedStatement.setString(2, client.getLastName());
            preparedStatement.setString(3, client.getPhoneNumber());
            preparedStatement.setInt(4, client.getVisitsCount());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Client getClientByNumber(String phoneNumber) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_CLIENT_BY_PHONENUMBER);
            preparedStatement.setString(1, phoneNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Client(
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("phone_number"),
                        resultSet.getInt("visits_count")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Client has not found");
    }
}