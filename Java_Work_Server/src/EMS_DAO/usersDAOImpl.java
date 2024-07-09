package EMS_DAO;

import EMS_Util.DatabaseUtil;
import EMS_Util.TimestampIdGenerator;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class usersDAOImpl implements usersDAO {
    private Connection conn;

    public usersDAOImpl() {
        conn = DatabaseUtil.getConnection();
    }

    public User findByName(String name) {
        User u=null;
        String sql = "SELECT * FROM users WHERE user_name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                u=new User();           //防止空指针
                u.setUserName(rs.getString("user_name"));
                u.setUserPassward(rs.getString("user_password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    public void save(User user) {
        String sql = "INSERT INTO users (user_id, user_name, user_password) " +
                "VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            user.setUserId(TimestampIdGenerator.generateId());
            stmt.setInt(1,user.getUserId());
            stmt.setString(2,user.getUserName());
            stmt.setString(3,user.getUserPassward());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(User user) {
        String sql = "UPDATE users SET  user_name = ?, user_password = ? " +
                "WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //todo:增加修改密码功能
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String name) {
        String sql = "DELETE FROM users WHERE user_name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
