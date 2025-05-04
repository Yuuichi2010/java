package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.InputStream;
import java.util.Properties;

public class DBConnection {
    private static DBConnection instance;
    private Connection connection;
    private String url;
    private String username;
    private String password;

    private DBConnection() {
        try {
            Properties prop = new Properties();
            // Đọc tệp cấu hình sử dụng classloader
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");

            if (inputStream != null) {
                prop.load(inputStream);

                this.url = prop.getProperty("db.url");
                this.username = prop.getProperty("db.username");
                this.password = prop.getProperty("db.password");

                // Load JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");
            } else {
                System.err.println("Không tìm thấy tệp config.properties!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, username, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}