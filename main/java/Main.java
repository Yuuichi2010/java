import data.DBConnection;
import presentation.frames.LoginFrame;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        // Thiết lập look and feel cho giao diện
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Kiểm tra kết nối database
        checkDatabaseConnection();

        // Khởi động ứng dụng trong EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            try {
                // Tạo và hiển thị cửa sổ đăng nhập
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Không thể khởi động ứng dụng: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * Kiểm tra kết nối đến cơ sở dữ liệu
     */
    private static void checkDatabaseConnection() {
        try {
            System.out.println("Đang kiểm tra kết nối database...");
            Connection conn = DBConnection.getInstance().getConnection();

            if (conn != null && !conn.isClosed()) {
                System.out.println("Kết nối database thành công!");

                // Thử truy vấn đơn giản để kiểm tra dữ liệu
                Statement stmt = conn.createStatement();

                // Kiểm tra bảng Book
                try {
                    ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Book");
                    if (rs.next()) {
                        System.out.println("Số lượng sách trong thư viện: " + rs.getInt(1));
                    }
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Lỗi truy vấn bảng Book: " + e.getMessage());
                }

                // Kiểm tra bảng Reader
                try {
                    ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Reader");
                    if (rs.next()) {
                        System.out.println("Số lượng độc giả trong thư viện: " + rs.getInt(1));
                    }
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Lỗi truy vấn bảng Reader: " + e.getMessage());
                }

                // Kiểm tra bảng Librarian
                try {
                    ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Librarian");
                    if (rs.next()) {
                        System.out.println("Số lượng thủ thư trong thư viện: " + rs.getInt(1));
                    }
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Lỗi truy vấn bảng Librarian: " + e.getMessage());
                }

                stmt.close();
            } else {
                System.err.println("Không thể kết nối đến database!");
                JOptionPane.showMessageDialog(null,
                        "Không thể kết nối đến cơ sở dữ liệu. Vui lòng kiểm tra cấu hình kết nối.",
                        "Lỗi Kết Nối", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Lỗi kiểm tra kết nối database: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Lỗi kết nối cơ sở dữ liệu: " + e.getMessage() +
                            "\nVui lòng kiểm tra lại thông tin kết nối trong file config.properties.",
                    "Lỗi Kết Nối", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
}