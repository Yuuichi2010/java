package bus;

import dao.ConnectionManager;
import dao.SachDAO;
import dto.SachDTO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SachBUS {
    private SachDAO dao;

    public SachBUS() {
        try {
            Connection conn = ConnectionManager.getConnection();
            dao = new SachDAO(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<SachDTO> getAll() {
        try {
            return dao.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public SachDTO getByID(int id) {
        try {
            return dao.getByID(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<SachDTO> getByKeyword(String keyword) {
        try {
            return dao.getByKeyword(keyword);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addSach(SachDTO sach) {
        try {
            return dao.addSach(sach);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateSach(SachDTO sach) {
        try {
            return dao.updateSach(sach);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteSach(int id) {
        try {
            return dao.deleteSach(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateSoLuong(int id, int soLuong) {
        try {
            return dao.updateSoLuong(id, soLuong);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateGia(int id, double gia) {
        try {
            return dao.updateGia(id, gia);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
