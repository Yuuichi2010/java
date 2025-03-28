package dao;

import dto.PhieuMuonDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhieuMuonDAO {
    private Connection conn;

    public PhieuMuonDAO(Connection conn) {
        this.conn = conn;
    }

    public List<PhieuMuonDTO> getAll() throws SQLException {
        List<PhieuMuonDTO> list = new ArrayList<>();
        String query = "SELECT * FROM PhieuMuon";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                PhieuMuonDTO pm = new PhieuMuonDTO(
                    rs.getInt("phieuMuonID"),
                    rs.getInt("docGiaID"),
                    rs.getString("ngayMuon"),
                    rs.getString("ngayTraDuKien")
                );
                list.add(pm);
            }
        }
        return list;
    }

    public PhieuMuonDTO getByID(int phieuMuonID) throws SQLException {
        String query = "SELECT * FROM PhieuMuon WHERE phieuMuonID = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, phieuMuonID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new PhieuMuonDTO(
                        rs.getInt("phieuMuonID"),
                        rs.getInt("docGiaID"),
                        rs.getString("ngayMuon"),
                        rs.getString("ngayTraDuKien")
                    );
                }
            }
        }
        return null;
    }

    public boolean add(PhieuMuonDTO pm) throws SQLException {
        String query = "INSERT INTO PhieuMuon (docGiaID, ngayMuon, ngayTraDuKien) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, pm.getDocGiaID());
            ps.setString(2, pm.getNgayMuon());
            ps.setString(3, pm.getNgayTraDuKien());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean update(PhieuMuonDTO pm) throws SQLException {
        String query = "UPDATE PhieuMuon SET docGiaID = ?, ngayMuon = ?, ngayTraDuKien = ? WHERE phieuMuonID = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, pm.getDocGiaID());
            ps.setString(2, pm.getNgayMuon());
            ps.setString(3, pm.getNgayTraDuKien());
            ps.setInt(4, pm.getPhieuMuonID());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int phieuMuonID) throws SQLException {
        String query = "DELETE FROM PhieuMuon WHERE phieuMuonID = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, phieuMuonID);
            return ps.executeUpdate() > 0;
        }
    }

    public List<PhieuMuonDTO> getByDocGiaID(int docGiaID) throws SQLException {
        List<PhieuMuonDTO> list = new ArrayList<>();
        String query = "SELECT * FROM PhieuMuon WHERE docGiaID = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, docGiaID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PhieuMuonDTO pm = new PhieuMuonDTO(
                        rs.getInt("phieuMuonID"),
                        rs.getInt("docGiaID"),
                        rs.getString("ngayMuon"),
                        rs.getString("ngayTraDuKien")
                    );
                    list.add(pm);
                }
            }
        }
        return list;
    }

    public List<PhieuMuonDTO> getByNgayMuon(String ngayMuon) throws SQLException {
        List<PhieuMuonDTO> list = new ArrayList<>();
        String query = "SELECT * FROM PhieuMuon WHERE ngayMuon = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, ngayMuon);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PhieuMuonDTO pm = new PhieuMuonDTO(
                        rs.getInt("phieuMuonID"),
                        rs.getInt("docGiaID"),
                        rs.getString("ngayMuon"),
                        rs.getString("ngayTraDuKien")
                    );
                    list.add(pm);
                }
            }
        }
        return list;
    }

    public List<PhieuMuonDTO> getByNgayTraDuKien(String ngayTraDuKien) throws SQLException {
        List<PhieuMuonDTO> list = new ArrayList<>();
        String query = "SELECT * FROM PhieuMuon WHERE ngayTraDuKien = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, ngayTraDuKien);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PhieuMuonDTO pm = new PhieuMuonDTO(
                        rs.getInt("phieuMuonID"),
                        rs.getInt("docGiaID"),
                        rs.getString("ngayMuon"),
                        rs.getString("ngayTraDuKien")
                    );
                    list.add(pm);
                }
            }
        }
        return list;
    }

    public List<PhieuMuonDTO> getByDocGiaIDAndNgayMuon(int docGiaID, String ngayMuon) throws SQLException {
        List<PhieuMuonDTO> list = new ArrayList<>();
        String query = "SELECT * FROM PhieuMuon WHERE docGiaID = ? AND ngayMuon = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, docGiaID);
            ps.setString(2, ngayMuon);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PhieuMuonDTO pm = new PhieuMuonDTO(
                        rs.getInt("phieuMuonID"),
                        rs.getInt("docGiaID"),
                        rs.getString("ngayMuon"),
                        rs.getString("ngayTraDuKien")
                    );
                    list.add(pm);
                }
            }
        }
        return list;
    }

    public List<PhieuMuonDTO> getByDocGiaIDAndNgayTraDuKien(int docGiaID, String ngayTraDuKien) throws SQLException {
        List<PhieuMuonDTO> list = new ArrayList<>();
        String query = "SELECT * FROM PhieuMuon WHERE docGiaID = ? AND ngayTraDuKien = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, docGiaID);
            ps.setString(2, ngayTraDuKien);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PhieuMuonDTO pm = new PhieuMuonDTO(
                        rs.getInt("phieuMuonID"),
                        rs.getInt("docGiaID"),
                        rs.getString("ngayMuon"),
                        rs.getString("ngayTraDuKien")
                    );
                    list.add(pm);
                }
            }
        }
        return list;
    }

    public List<PhieuMuonDTO> getByNgayMuonAndNgayTraDuKien(String ngayMuon, String ngayTraDuKien) throws SQLException {
        List<PhieuMuonDTO> list = new ArrayList<>();
        String query = "SELECT * FROM PhieuMuon WHERE ngayMuon = ? AND ngayTraDuKien = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, ngayMuon);
            ps.setString(2, ngayTraDuKien);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PhieuMuonDTO pm = new PhieuMuonDTO(
                        rs.getInt("phieuMuonID"),
                        rs.getInt("docGiaID"),
                        rs.getString("ngayMuon"),
                        rs.getString("ngayTraDuKien")
                    );
                    list.add(pm);
                }
            }
        }
        return list;
    }

    public List<PhieuMuonDTO> getByDocGiaIDAndNgayMuonAndNgayTraDuKien(int docGiaID, String ngayMuon, String ngayTraDuKien) throws SQLException {
        List<PhieuMuonDTO> list = new ArrayList<>();
        String query = "SELECT * FROM PhieuMuon WHERE docGiaID = ? AND ngayMuon = ? AND ngayTraDuKien = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, docGiaID);
            ps.setString(2, ngayMuon);
            ps.setString(3, ngayTraDuKien);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PhieuMuonDTO pm = new PhieuMuonDTO(
                        rs.getInt("phieuMuonID"),
                        rs.getInt("docGiaID"),
                        rs.getString("ngayMuon"),
                        rs.getString("ngayTraDuKien")
                    );
                    list.add(pm);
                }
            }
        }
        return list;
    }
}
