package EMS_DAO;
import Model.*;

import java.util.List;

public interface usersDAO {
    User findByName(String name);
    void save(User u);
    void update(User u);
    void delete(String name);
}
