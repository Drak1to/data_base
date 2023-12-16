package two.DAO;

import two.models.Movie;
import two.utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieDao implements BaseDao<Movie> {
    private static final String CREATE = "INSERT INTO movies (descriptions, id_rating, name_movie) VALUE (?, ?, ?)";

    private final String REED_ALL = "SELECT * FROM movies";

    private static final String DELETE_BY_ID = "DELETE FROM movies WHERE id = ?";

    private static final String UPDATE = "UPDATE movies SET name_movie = ? , id_rating = ? ";
    private PreparedStatement preparedStatement;

    private final Connection dbConnection = ConnectionUtils.openConnection();

    public MovieDao() throws SQLException, ClassNotFoundException {
    }

    @Override
    public void create(Movie movie) {


    }

    @Override
    public List<Movie> readAll() throws SQLException {
        List<Movie> movieList = new ArrayList<>();
        try {
            preparedStatement = dbConnection.prepareStatement(REED_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Movie movie = new Movie();
                movie.setId(resultSet.getInt("id"));
                movie.setName_movie(resultSet.getString("name_movie"));
                movie.setDescriptions(resultSet.getString("descriptions"));

                movieList.add(movie);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            dbConnection.close();
        }
        return movieList;
    }

    @Override
    public void update() {

    }

    @Override
    public void deleteById(int id) {

    }
}
