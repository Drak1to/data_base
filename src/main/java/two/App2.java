package two;

import two.DAO.MovieDao;
import two.DAO.RatingDao;
import two.DAO.UserDao;
import two.models.Movie;

import java.sql.SQLException;

public class App2 {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        MovieDao movieDao = new MovieDao();
        UserDao userDao = new UserDao();
        RatingDao ratingDao = new RatingDao();
        movieDao.readAll().forEach(System.out::println);
        System.out.println("=====================================================");
        userDao.readAll().forEach(System.out::println);
        System.out.println("=====================================================");
        ratingDao.readAll().forEach(System.out::println);





    }
}
