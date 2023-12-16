package two.DAO;
import java.sql.SQLException;
import java.util.List;
public interface BaseDao<T> {
     void create(T t);

     List<T> readAll() throws SQLException;

     void update();

     void deleteById(int id);

}
