package bus;

import dao.ConnectionManager;
import dao.PhieuMuonDAO;
import dto.PhieuMuonDTO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PhieuMuonBUS {
    private PhieuMuonDAO dao;

    public PhieuMuonBUS() {
        try {
            Connection conn = ConnectionManager.getConnection();
            dao = new PhieuMuonDAO(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PhieuMuonDTO> getAll() {
        try {
            return dao.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
   
    public PhieuMuonDTO getByID(int id) {
        try {
            return dao.getByID(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

