package bus;

import dao.ConnectionManager;
import dao.ThuThuDAO;
import dto.ThuThuDTO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ThuThuBUS {
    private ThuThuDAO dao;

    public ThuThuBUS() {
        try {
            Connection conn = ConnectionManager.getConnection();
            dao = new ThuThuDAO(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ThuThuDTO> getAll() {
        try {
            return dao.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public ThuThuDTO getByID(int id) {
        try {
            return dao.getByID(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<ThuThuDTO> getByKeyword(String keyword) {
        try {
            return dao.getByKeyword(keyword);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean addThuThu(ThuThuDTO thuThu) {
        try {
            return dao.addThuThu(thuThu);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateThuThu(ThuThuDTO thuThu) {
        try {
            return dao.updateThuThu(thuThu);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteThuThu(int id) {
        try {
            return dao.deleteThuThu(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        ThuThuBUS bus = new ThuThuBUS();
        List<ThuThuDTO> list = bus.getAll();
        for (ThuThuDTO t : list) {
            System.out.println(t);
        }
    }
}
