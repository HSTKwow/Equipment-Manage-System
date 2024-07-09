package EMS_Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/equipment_managment_system";
    private static final String DATABASE_USER = "admin";
    private static final String DATABASE_PASSWORD = "123456";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            // 加载JDBC驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 创建数据库连接
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection to MySQL failed.");
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
