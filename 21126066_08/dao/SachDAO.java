package dao;

import dto.SachDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SachDAO {
    private Connection conn;

    public SachDAO(Connection conn) {
        this.conn = conn;
    }

    public List<SachDTO> getAll() throws SQLException {
        List<SachDTO> list = new ArrayList<>();
        String query = "SELECT * FROM Sach";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                SachDTO s = new SachDTO(
                    rs.getInt("sachID"),
                    rs.getString("ISBN"),
                    rs.getString("tenSach"),
                    rs.getString("tacGia"),
                    rs.getString("nhaXuatBan"),
                    rs.getInt("namXuatBan"),
                    rs.getString("theLoai"),
                    rs.getDouble("gia"),
                    rs.getInt("soLuong")
                );
                list.add(s);
            }
        }
        return list;
    }

    public SachDTO getByID(int sachID) throws SQLException {
        String query = "SELECT * FROM Sach WHERE sachID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, sachID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new SachDTO(
                        rs.getInt("sachID"),
                        rs.getString("ISBN"),
                        rs.getString("tenSach"),
                        rs.getString("tacGia"),
                        rs.getString("nhaXuatBan"),
                        rs.getInt("namXuatBan"),
                        rs.getString("theLoai"),
                        rs.getDouble("gia"),
                        rs.getInt("soLuong")
                    );
                }
            }
        }
        return null;
    }

    public List<SachDTO> getByKeyword(String keyword) throws SQLException {
        List<SachDTO> list = new ArrayList<>();
        String query = "SELECT * FROM Sach WHERE tenSach LIKE ? OR ISBN LIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            String likeKeyword = "%" + keyword + "%";
            stmt.setString(1, likeKeyword);
            stmt.setString(2, likeKeyword);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    SachDTO s = new SachDTO(
                        rs.getInt("sachID"),
                        rs.getString("ISBN"),
                        rs.getString("tenSach"),
                        rs.getString("tacGia"),
                        rs.getString("nhaXuatBan"),
                        rs.getInt("namXuatBan"),
                        rs.getString("theLoai"),
                        rs.getDouble("gia"),
                        rs.getInt("soLuong")
                    );
                    list.add(s);
                }
            }
        }
        return list;
    }

    public boolean addSach(SachDTO sach) throws SQLException {
        String query = "INSERT INTO Sach (ISBN, tenSach, tacGia, nhaXuatBan, namXuatBan, theLoai, gia, soLuong) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, sach.getISBN());
            stmt.setString(2, sach.getTenSach());
            stmt.setString(3, sach.getTacGia());
            stmt.setString(4, sach.getNhaXuatBan());
            stmt.setInt(5, sach.getNamXuatBan());
            stmt.setString(6, sach.getTheLoai());
            stmt.setDouble(7, sach.getGia());
            stmt.setInt(8, sach.getSoLuong());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateSach(SachDTO sach) throws SQLException {
        String query = "UPDATE Sach SET ISBN = ?, tenSach = ?, tacGia = ?, nhaXuatBan = ?, namXuatBan = ?, theLoai = ?, gia = ?, soLuong = ? WHERE sachID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, sach.getISBN());
            stmt.setString(2, sach.getTenSach());
            stmt.setString(3, sach.getTacGia());
            stmt.setString(4, sach.getNhaXuatBan());
            stmt.setInt(5, sach.getNamXuatBan());
            stmt.setString(6, sach.getTheLoai());
            stmt.setDouble(7, sach.getGia());
            stmt.setInt(8, sach.getSoLuong());
            stmt.setInt(9, sach.getSachID());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteSach(int sachID) throws SQLException {
        String query = "DELETE FROM Sach WHERE sachID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, sachID);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateSoLuong(int sachID, int soLuong) throws SQLException {
        String query = "UPDATE Sach SET soLuong = ? WHERE sachID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, soLuong);
            stmt.setInt(2, sachID);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateGia(int sachID, double gia) throws SQLException {
        String query = "UPDATE Sach SET gia = ? WHERE sachID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, gia);
            stmt.setInt(2, sachID);
            return stmt.executeUpdate() > 0;
        }
    }

}

