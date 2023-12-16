package two.DAO;

import two.models.Movie;
import two.models.Rating;
import two.utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RatingDao implements BaseDao<Rating> {
    private static final String CREATE = "INSERT INTO ratings (rating_Id, rating, user_Id) VALUE (?, ?, ?)";

    private final String REED_ALL = "SELECT * FROM ratings";

    private static final String DELETE_BY_ID = "DELETE FROM ratings WHERE id = ?";

    private static final String UPDATE = "UPDATE ratings SET rating = ?";
    private PreparedStatement preparedStatement;

    private final Connection dbConnection = ConnectionUtils.openConnection();

    public RatingDao() throws SQLException, ClassNotFoundException {
    }

    @Override
    public void create(Rating rating) {

    }

    @Override
    public List<Rating> readAll() throws SQLException {
        List<Rating> ratingList = new ArrayList<>();
        try {
            preparedStatement = dbConnection.prepareStatement(REED_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Rating rating = new Rating();
                rating.setRating_Id(resultSet.getInt("rating_Id"));
                rating.setRating(resultSet.getInt("rating"));
                rating.setUser_Id(resultSet.getInt("user_Id"));

                ratingList.add(rating);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            dbConnection.close();
        }
        return ratingList;
    }

    @Override
    public void update() {

    }

    @Override
    public void deleteById(int id) {

    }
}
