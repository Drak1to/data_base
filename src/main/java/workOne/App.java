package workOne;

import java.sql.*;

/**
 * Hello world!
 *
 */
public class App {
    private static final String jdbcURL = "jdbc:mysql://localhost/cinema";
    private static final String username = "root";
    private static final String password = "root";

    private static final String REED_ALL_FROM_MOVIE = "SELECT * FROM movie";
    private static final String DELETE_MOVIE_BY_ID = "DELETE FROM movie WHERE id = ?";
    public static void main( String[] args )
    {

        getAllFromMovie();
        deleteMovieById(6);
        deleteMovieById(5);
        deleteMovieById(4);
        deleteMovieById(3);
        System.out.println("Clearing");
        getAllFromMovie();
    }

    public static void getAllFromMovie(){
        try {
            // Завантаження драйверу jdbc
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(REED_ALL_FROM_MOVIE);

            ResultSet resultSet =  preparedStatement.executeQuery();

            while (resultSet.next()){
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name_movie");
                Integer id_rating = resultSet.getInt("id_rating");
                String description = resultSet.getString("desraption");

                System.out.println("Id: " + id +"\nName: " + name + "; \nRating: " + id_rating + "; \nDescription: " + description + "\n------");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void deleteMovieById(int id){
        try {
            // Завантаження драйверу jdbc
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Робимо конект с бд (DB)
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);

            // Робимо запит до бд
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_MOVIE_BY_ID);
            preparedStatement.setInt(1, id);


            preparedStatement.execute();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}