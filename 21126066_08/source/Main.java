package source;

import bus.DocGiaBUS;
import bus.ThuThuBUS;
import bus.SachBUS;
import bus.PhieuMuonBUS;
import bus.PhieuTraBUS;
import dto.DocGiaDTO;
import dto.ThuThuDTO;
import dto.SachDTO;
import dto.PhieuMuonDTO;
import dto.PhieuTraDTO;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DocGiaBUS docGiaBUS = new DocGiaBUS();
        List<DocGiaDTO> listDocGia = docGiaBUS.getAll();
        System.out.println("Danh sách độc giả:");
        if(listDocGia != null) {
            for(DocGiaDTO d : listDocGia) {
                System.out.println(d.getDocGiaID() + " - " + d.getHoTen() + " - " + d.getEmail());
            }
        }
        
        String keyword = "Nguyen";
        List<DocGiaDTO> searchResult = docGiaBUS.getByKeyword(keyword);
        System.out.println("\nKết quả tìm kiếm độc giả với từ khóa '" + keyword + "':");
        if(searchResult != null) {
            for(DocGiaDTO d : searchResult) {
                System.out.println(d.getDocGiaID() + " - " + d.getHoTen());
            }
        }
        
        DocGiaDTO newDocGia = new DocGiaDTO(0, "Tran Van A", "123456789", "1990-01-01", "Nam", "a@gmail.com", "123 ABC Street", "2023-04-01", "2027-04-01");
        if(docGiaBUS.addDocGia(newDocGia)) {
            System.out.println("\nThêm độc giả thành công!");
        } else {
            System.out.println("\nThêm độc giả thất bại!");
        }
        
        
        ThuThuBUS thuThuBUS = new ThuThuBUS();
        List<ThuThuDTO> listThuThu = thuThuBUS.getAll();
        System.out.println("\nDanh sách thủ thư:");
        if(listThuThu != null) {
            for(ThuThuDTO t : listThuThu) {
                System.out.println(t.getThuThuID() + " - " + t.getHoTen());
            }
        }
        
        // Ví dụ: Sử dụng SachBUS
        SachBUS sachBUS = new SachBUS();
        List<SachDTO> listSach = sachBUS.getAll();
        System.out.println("\nDanh sách sách:");
        if(listSach != null) {
            for(SachDTO s : listSach) {
                System.out.println(s.getSachID() + " - " + s.getTenSach() + " - " + s.getTacGia());
            }
        }
        
    }
}
