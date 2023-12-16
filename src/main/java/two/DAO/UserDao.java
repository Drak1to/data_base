package two.DAO;

import two.models.Rating;
import two.models.User;
import two.utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements BaseDao<User> {
    private static final String CREATE = "INSERT INTO users (email, name, password) VALUE (?, ?, ?)";

    private final String REED_ALL = "SELECT * FROM users";

    private static final String DELETE_BY_ID = "DELETE FROM users WHERE id = ?";

    private static final String UPDATE = "UPDATE users SET email = ? , password = ?";
    private PreparedStatement preparedStatement;

    private final Connection dbConnection = ConnectionUtils.openConnection();

    public UserDao() throws SQLException, ClassNotFoundException {
    }

    @Override
    public void create(User user) {

    }

    @Override
    public List<User> readAll() throws SQLException {
        List<User> userList = new ArrayList<>();
        try {
            preparedStatement = dbConnection.prepareStatement(REED_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setEmail(resultSet.getString("email"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));

                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            dbConnection.close();
        }
        return userList;
    }

    @Override
    public void update() {

    }

    @Override
    public void deleteById(int id) {

    }
}
