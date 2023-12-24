package Three.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBaseHandler {
    private static final String jdbcURL = "jdbc:mysql://localhost/telegrambot";
    private static final String username = "root";
    private static final String password = "root";
     private static final  String CREATE_USER = "INSERT INTO users (username, chat_id) VALUE (?, ?)";
    private static final  String CREATE_FILM = "INSERT INTO films (rating, title, user_id ) VALUE (?, ?, ?)";


    public Connection openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(jdbcURL, username, password);
    }

    public void registerUser(String name, long chatId, String email) {
        try {
           Connection connection = openConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USER);
            preparedStatement.setString(1,name);
            preparedStatement.setLong(2, chatId);
            preparedStatement.setString(3, email);
            preparedStatement.executeUpdate();


        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public void createFilm(int rating, String title, long chat_id) {
        try {
            Connection connection = openConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_FILM);
            preparedStatement.setInt(1,rating);
            preparedStatement.setString(2, title);
            preparedStatement.setLong(3, chat_id);
            preparedStatement.executeUpdate();


        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


}
