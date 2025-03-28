package bus;

import dao.ConnectionManager;
import dao.DocGiaDAO;
import dto.DocGiaDTO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DocGiaBUS {
    private DocGiaDAO dao;

    public DocGiaBUS() {
        try {
            Connection conn = ConnectionManager.getConnection();
            dao = new DocGiaDAO(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<DocGiaDTO> getAll() {
        try {
            return dao.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public DocGiaDTO getByID(int id) {
        try {
            return dao.getByID(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<DocGiaDTO> getByKeyword(String keyword) {
        try {
            return dao.getByKeyword(keyword);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addDocGia(DocGiaDTO docGia) {
        try {
            return dao.addDocGia(docGia);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateDocGia(DocGiaDTO docGia) {
        try {
            return dao.updateDocGia(docGia);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteDocGia(int id) {
        try {
            return dao.deleteDocGia(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
