package bus;

import dao.ConnectionManager;
import dao.PhieuTraDAO;
import dto.PhieuTraDTO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PhieuTraBUS {
    private PhieuTraDAO dao;

    public PhieuTraBUS() {
        try {
            Connection conn = ConnectionManager.getConnection();
            dao = new PhieuTraDAO(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PhieuTraDTO> getAll() {
        try {
            return dao.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public PhieuTraDTO getByID(int id) {
        try {
            return dao.getByID(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PhieuTraDTO> getByTienPhat(int tienPhat) {
        try {
            return dao.getByTienPhat(tienPhat);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PhieuTraDTO> getByPhieuMuonID(int phieuMuonID) {
        try {
            return dao.getByPhieuMuonID(phieuMuonID);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PhieuTraDTO> getByNgayTraThucTe(String ngayTraThucTe) {
        try {
            return dao.getByNgayTraThucTe(ngayTraThucTe);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PhieuTraDTO> getByTienPhat(double tienPhat) {
        try {
            return dao.getByTienPhat(tienPhat);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}