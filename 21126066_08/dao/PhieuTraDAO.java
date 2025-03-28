package dao;

import dto.PhieuTraDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhieuTraDAO {
    private Connection conn;

    public PhieuTraDAO(Connection conn) {
        this.conn = conn;
    }

    public List<PhieuTraDTO> getAll() throws SQLException {
        List<PhieuTraDTO> list = new ArrayList<>();
        String query = "SELECT * FROM PhieuTra";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                PhieuTraDTO pt = new PhieuTraDTO(
                    rs.getInt("phieuTraID"),
                    rs.getInt("phieuMuonID"),
                    rs.getString("ngayTraThucTe"),
                    rs.getDouble("tienPhat")
                );
                list.add(pt);
            }
        }
        return list;
    }

    public PhieuTraDTO getByID(int phieuTraID) throws SQLException {
        String query = "SELECT * FROM PhieuTra WHERE phieuTraID = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, phieuTraID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new PhieuTraDTO(
                        rs.getInt("phieuTraID"),
                        rs.getInt("phieuMuonID"),
                        rs.getString("ngayTraThucTe"),
                        rs.getDouble("tienPhat")
                    );
                }
            }
        }
        return null;
    }

    public boolean add(PhieuTraDTO pt) throws SQLException {
        String query = "INSERT INTO PhieuTra (phieuMuonID, ngayTraThucTe, tienPhat) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, pt.getPhieuMuonID());
            ps.setString(2, pt.getNgayTraThucTe());
            ps.setDouble(3, pt.getTienPhat());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean update(PhieuTraDTO pt) throws SQLException {
        String query = "UPDATE PhieuTra SET phieuMuonID = ?, ngayTraThucTe = ?, tienPhat = ? WHERE phieuTraID = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, pt.getPhieuMuonID());
            ps.setString(2, pt.getNgayTraThucTe());
            ps.setDouble(3, pt.getTienPhat());
            ps.setInt(4, pt.getPhieuTraID());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int phieuTraID) throws SQLException {
        String query = "DELETE FROM PhieuTra WHERE phieuTraID = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, phieuTraID);
            return ps.executeUpdate() > 0;
        }
    }

    public List<PhieuTraDTO> getByPhieuMuonID(int phieuMuonID) throws SQLException {
        List<PhieuTraDTO> list = new ArrayList<>();
        String query = "SELECT * FROM PhieuTra WHERE phieuMuonID = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, phieuMuonID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PhieuTraDTO pt = new PhieuTraDTO(
                        rs.getInt("phieuTraID"),
                        rs.getInt("phieuMuonID"),
                        rs.getString("ngayTraThucTe"),
                        rs.getDouble("tienPhat")
                    );
                    list.add(pt);
                }
            }
        }
        return list;
    }

    public List<PhieuTraDTO> getByNgayTraThucTe(String ngayTraThucTe) throws SQLException {
        List<PhieuTraDTO> list = new ArrayList<>();
        String query = "SELECT * FROM PhieuTra WHERE ngayTraThucTe = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, ngayTraThucTe);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PhieuTraDTO pt = new PhieuTraDTO(
                        rs.getInt("phieuTraID"),
                        rs.getInt("phieuMuonID"),
                        rs.getString("ngayTraThucTe"),
                        rs.getDouble("tienPhat")
                    );
                    list.add(pt);
                }
            }
        }
        return list;
    }

    public List<PhieuTraDTO> getByTienPhat(double tienPhat) throws SQLException {
        List<PhieuTraDTO> list = new ArrayList<>();
        String query = "SELECT * FROM PhieuTra WHERE tienPhat = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setDouble(1, tienPhat);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PhieuTraDTO pt = new PhieuTraDTO(
                        rs.getInt("phieuTraID"),
                        rs.getInt("phieuMuonID"),
                        rs.getString("ngayTraThucTe"),
                        rs.getDouble("tienPhat")
                    );
                    list.add(pt);
                }
            }
        }
        return list;
    }

    public List<PhieuTraDTO> getByPhieuMuonIDAndNgayTraThucTe(int phieuMuonID, String ngayTraThucTe) throws SQLException {
        List<PhieuTraDTO> list = new ArrayList<>();
        String query = "SELECT * FROM PhieuTra WHERE phieuMuonID = ? AND ngayTraThucTe = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, phieuMuonID);
            ps.setString(2, ngayTraThucTe);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PhieuTraDTO pt = new PhieuTraDTO(
                        rs.getInt("phieuTraID"),
                        rs.getInt("phieuMuonID"),
                        rs.getString("ngayTraThucTe"),
                        rs.getDouble("tienPhat")
                    );
                    list.add(pt);
                }
            }
        }
        return list;
    }

    public List<PhieuTraDTO> getByPhieuMuonIDAndTienPhat(int phieuMuonID, double tienPhat) throws SQLException {
        List<PhieuTraDTO> list = new ArrayList<>();
        String query = "SELECT * FROM PhieuTra WHERE phieuMuonID = ? AND tienPhat = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, phieuMuonID);
            ps.setDouble(2, tienPhat);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PhieuTraDTO pt = new PhieuTraDTO(
                        rs.getInt("phieuTraID"),
                        rs.getInt("phieuMuonID"),
                        rs.getString("ngayTraThucTe"),
                        rs.getDouble("tienPhat")
                    );
                    list.add(pt);
                }
            }
        }
        return list;
    }

    public List<PhieuTraDTO> getByNgayTraThucTeAndTienPhat(String ngayTraThucTe, double tienPhat) throws SQLException {
        List<PhieuTraDTO> list = new ArrayList<>();
        String query = "SELECT * FROM PhieuTra WHERE ngayTraThucTe = ? AND tienPhat = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, ngayTraThucTe);
            ps.setDouble(2, tienPhat);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PhieuTraDTO pt = new PhieuTraDTO(
                        rs.getInt("phieuTraID"),
                        rs.getInt("phieuMuonID"),
                        rs.getString("ngayTraThucTe"),
                        rs.getDouble("tienPhat")
                    );
                    list.add(pt);
                }
            }
        }
        return list;
    }

    public List<PhieuTraDTO> getByPhieuMuonIDAndNgayTraThucTeAndTienPhat(int phieuMuonID, String ngayTraThucTe, double tienPhat) throws SQLException {
        List<PhieuTraDTO> list = new ArrayList<>();
        String query = "SELECT * FROM PhieuTra WHERE phieuMuonID = ? AND ngayTraThucTe = ? AND tienPhat = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, phieuMuonID);
            ps.setString(2, ngayTraThucTe);
            ps.setDouble(3, tienPhat);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PhieuTraDTO pt = new PhieuTraDTO(
                        rs.getInt("phieuTraID"),
                        rs.getInt("phieuMuonID"),
                        rs.getString("ngayTraThucTe"),
                        rs.getDouble("tienPhat")
                    );
                    list.add(pt);
                }
            }
        }
        return list;
    }
}