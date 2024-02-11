package Four.Utils;

import Four.models.Client;
import lombok.Getter;

import java.sql.*;

@Getter
public class DataBaseHandler implements AutoCloseable {
    private static final String jdbcURL = "jdbc:mysql://localhost/sportclubdb";
    private static final String username = "root";
    private static final String password = "root";
    private static final String CREATE_CLIENT = "INSERT INTO clients (first_name, last_name, phone_number, visits_count, day_of_visits, award_count ) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String GET_CLIENT_BY_PHONENUMBER = "SELECT * FROM clients WHERE phone_number = ?";
    private static final String ADD_CLIENT_VISIT = "UPDATE clients SET visits_count = visits_count + 1 WHERE phone_number = ?";
    private static final String ADD_CLIENT_AWARD = "UPDATE clients SET award_count = award_count + 1 WHERE phone_number = ?";
    private static final String GET_REGISTERED_CLIENTS_COUNT = "SELECT COUNT(*) FROM clients";
    private static final String ADD_CLIENT_VISIT_DAY = "UPDATE clients SET day_of_visits = ?  WHERE phone_number = ?";
    private static final String ADD_CLIENT_COMMENTS = "UPDATE clients SET comments = ?  WHERE phone_number = ?";
    private Connection connection;

    public DataBaseHandler() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(true);
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
            preparedStatement.setString(5, client.getDayVisits());
            preparedStatement.setInt(6, client.getAwardCount());
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
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("phone_number"),
                        resultSet.getInt("visits_count"),
                        resultSet.getString("day_of_visits"),
                        resultSet.getString("comments"),
                        resultSet.getInt("award_count")


                );
            }
            System.out.println(GET_CLIENT_BY_PHONENUMBER);
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

    public boolean updateClientByVisits(String phoneNumber) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_CLIENT_VISIT);
            preparedStatement.setString(1, phoneNumber);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public int getRegisteredClientsCount() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_REGISTERED_CLIENTS_COUNT);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public boolean saveDayOfVisits(String phoneNumber, String dayVisits) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_CLIENT_VISIT_DAY);
            preparedStatement.setString(1, dayVisits);
            preparedStatement.setString(2, phoneNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;

    }

    public boolean saveComments(String phoneNumber, String comments) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_CLIENT_COMMENTS);
            preparedStatement.setString(1, comments);
            preparedStatement.setString(2, phoneNumber);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    public boolean updateClientByAward(String phoneNumber) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_CLIENT_AWARD );
            preparedStatement.setString(1, phoneNumber);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
