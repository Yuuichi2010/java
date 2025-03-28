package dao;

import dto.ThuThuDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ThuThuDAO {
    private Connection conn;

    public ThuThuDAO(Connection conn) {
        this.conn = conn;
    }

    public List<ThuThuDTO> getAll() throws SQLException {
        List<ThuThuDTO> list = new ArrayList<>();
        String query = "SELECT * FROM ThuThu";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                ThuThuDTO t = new ThuThuDTO(
                    rs.getInt("thuThuID"),
                    rs.getString("hoTen"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email")
                );
                list.add(t);
            }
        }
        return list;
    }

    public ThuThuDTO getByID(int thuThuID) throws SQLException {
        String query = "SELECT * FROM ThuThu WHERE thuThuID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, thuThuID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new ThuThuDTO(
                        rs.getInt("thuThuID"),
                        rs.getString("hoTen"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email")
                    );
                }
            }
        }
        return null;
    }

    public List<ThuThuDTO> getByKeyword(String keyword) throws SQLException {
        List<ThuThuDTO> list = new ArrayList<>();
        String query = "SELECT * FROM ThuThu WHERE hoTen LIKE ? OR username LIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            String likeKeyword = "%" + keyword + "%";
            stmt.setString(1, likeKeyword);
            stmt.setString(2, likeKeyword);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ThuThuDTO t = new ThuThuDTO(
                        rs.getInt("thuThuID"),
                        rs.getString("hoTen"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email")
                    );
                    list.add(t);
                }
            }
        }
        return list;
    }

    public boolean addThuThu(ThuThuDTO thuThu) throws SQLException {
        String query = "INSERT INTO ThuThu (hoTen, username, password, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, thuThu.getHoTen());
            stmt.setString(2, thuThu.getUsername());
            stmt.setString(3, thuThu.getPassword());
            stmt.setString(4, thuThu.getEmail());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateThuThu(ThuThuDTO thuThu) throws SQLException {
        String query = "UPDATE ThuThu SET hoTen = ?, username = ?, password = ?, email = ? WHERE thuThuID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, thuThu.getHoTen());
            stmt.setString(2, thuThu.getUsername());
            stmt.setString(3, thuThu.getPassword());
            stmt.setString(4, thuThu.getEmail());
            stmt.setInt(5, thuThu.getThuThuID());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteThuThu(int thuThuID) throws SQLException {
        String query = "DELETE FROM ThuThu WHERE thuThuID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, thuThuID);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean login(String username, String password) throws SQLException {
        String query = "SELECT * FROM ThuThu WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean changePassword(String username, String oldPassword, String newPassword) throws SQLException {
        String query = "UPDATE ThuThu SET password = ? WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, username);
            stmt.setString(3, oldPassword);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean changeEmail(String username, String password, String newEmail) throws SQLException {
        String query = "UPDATE ThuThu SET email = ? WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newEmail);
            stmt.setString(2, username);
            stmt.setString(3, password);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean changeUsername(String oldUsername, String password, String newUsername) throws SQLException {
        String query = "UPDATE ThuThu SET username = ? WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newUsername);
            stmt.setString(2, oldUsername);
            stmt.setString(3, password);
            return stmt.executeUpdate() > 0;
        }
    }
}
