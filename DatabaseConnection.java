package utils;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/library_management";
    private static final String USERNAME = "root";
    private static final String PASSWORD = ""; // Thêm mật khẩu MySQL của bạn ở đây

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Ghi log quá trình kết nối
                LoggerUtil.info("Đang tải MySQL JDBC driver...");
                Class.forName("com.mysql.cj.jdbc.Driver");
                LoggerUtil.info("Tải driver thành công");

                // Ghi log thông tin kết nối (không bao gồm mật khẩu)
                LoggerUtil.info("Đang kết nối đến: " + URL + " với username: " + USERNAME);

                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                LoggerUtil.info("Kết nối cơ sở dữ liệu thành công");

            } catch (ClassNotFoundException e) {
                String errorMsg = "Không thể tìm thấy MySQL JDBC Driver";
                LoggerUtil.error(errorMsg, e);

                JOptionPane.showMessageDialog(null,
                        errorMsg + ". Vui lòng thêm MySQL Connector/J vào dự án của bạn.",
                        "Lỗi Kết Nối Cơ Sở Dữ Liệu",
                        JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                String errorMsg = "Không thể kết nối đến cơ sở dữ liệu";
                LoggerUtil.error(errorMsg + ": " + e.getMessage() + ", SQL State: " + e.getSQLState() + ", Error Code: " + e.getErrorCode(), e);

                JOptionPane.showMessageDialog(null,
                        errorMsg + ". Vui lòng kiểm tra máy chủ MySQL và thông tin đăng nhập.\n" + e.getMessage(),
                        "Lỗi Kết Nối Cơ Sở Dữ Liệu",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                LoggerUtil.info("Đã đóng kết nối cơ sở dữ liệu");
            } catch (SQLException e) {
                LoggerUtil.error("Lỗi khi đóng kết nối cơ sở dữ liệu", e);
            }
        }
    }
}