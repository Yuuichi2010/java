package dao;

import dto.DocGiaDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocGiaDAO {
    private Connection conn;

    public DocGiaDAO(Connection conn) {
        this.conn = conn;
    }

    public List<DocGiaDTO> getAll() throws SQLException {
        List<DocGiaDTO> list = new ArrayList<>();
        String query = "SELECT * FROM DocGia";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                DocGiaDTO d = new DocGiaDTO(
                    rs.getInt("docGiaID"),
                    rs.getString("hoTen"),
                    rs.getString("cmnd"),
                    rs.getString("ngaySinh"),
                    rs.getString("gioiTinh"),
                    rs.getString("email"),
                    rs.getString("diaChi"),
                    rs.getString("ngayLapThe"),
                    rs.getString("ngayHetHan")
                );
                list.add(d);
            }
        }
        return list;
    }

    public DocGiaDTO getByID(int docGiaID) throws SQLException {
        String query = "SELECT * FROM DocGia WHERE docGiaID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, docGiaID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new DocGiaDTO(
                        rs.getInt("docGiaID"),
                        rs.getString("hoTen"),
                        rs.getString("cmnd"),
                        rs.getString("ngaySinh"),
                        rs.getString("gioiTinh"),
                        rs.getString("email"),
                        rs.getString("diaChi"),
                        rs.getString("ngayLapThe"),
                        rs.getString("ngayHetHan")
                    );
                }
            }
        }
        return null;
    }

    public List<DocGiaDTO> getByKeyword(String keyword) throws SQLException {
        List<DocGiaDTO> list = new ArrayList<>();
        String query = "SELECT * FROM DocGia WHERE hoTen LIKE ? OR cmnd LIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            String likeKeyword = "%" + keyword + "%";
            stmt.setString(1, likeKeyword);
            stmt.setString(2, likeKeyword);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DocGiaDTO d = new DocGiaDTO(
                        rs.getInt("docGiaID"),
                        rs.getString("hoTen"),
                        rs.getString("cmnd"),
                        rs.getString("ngaySinh"),
                        rs.getString("gioiTinh"),
                        rs.getString("email"),
                        rs.getString("diaChi"),
                        rs.getString("ngayLapThe"),
                        rs.getString("ngayHetHan")
                    );
                    list.add(d);
                }
            }
        }
        return list;
    }

    public boolean addDocGia(DocGiaDTO docGia) throws SQLException {
        String query = "INSERT INTO DocGia (hoTen, cmnd, ngaySinh, gioiTinh, email, diaChi, ngayLapThe, ngayHetHan) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, docGia.getHoTen());
            stmt.setString(2, docGia.getCmnd());
            stmt.setString(3, docGia.getNgaySinh());
            stmt.setString(4, docGia.getGioiTinh());
            stmt.setString(5, docGia.getEmail());
            stmt.setString(6, docGia.getDiaChi());
            stmt.setString(7, docGia.getNgayLapThe());
            stmt.setString(8, docGia.getNgayHetHan());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateDocGia(DocGiaDTO docGia) throws SQLException {
        String query = "UPDATE DocGia SET hoTen = ?, cmnd = ?, ngaySinh = ?, gioiTinh = ?, email = ?, diaChi = ?, ngayLapThe = ?, ngayHetHan = ? WHERE docGiaID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, docGia.getHoTen());
            stmt.setString(2, docGia.getCmnd());
            stmt.setString(3, docGia.getNgaySinh());
            stmt.setString(4, docGia.getGioiTinh());
            stmt.setString(5, docGia.getEmail());
            stmt.setString(6, docGia.getDiaChi());
            stmt.setString(7, docGia.getNgayLapThe());
            stmt.setString(8, docGia.getNgayHetHan());
            stmt.setInt(9, docGia.getDocGiaID());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteDocGia(int docGiaID) throws SQLException {
        String query = "DELETE FROM DocGia WHERE docGiaID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, docGiaID);
            return stmt.executeUpdate() > 0;
        }
    }
}
