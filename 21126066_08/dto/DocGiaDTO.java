package dto;

public class DocGiaDTO {
    private int docGiaID;
    private String hoTen;
    private String cmnd;
    private String ngaySinh;
    private String gioiTinh;
    private String email;
    private String diaChi;
    private String ngayLapThe;
    private String ngayHetHan;

    public DocGiaDTO() {}

    public DocGiaDTO(int docGiaID, String hoTen, String cmnd, String ngaySinh, String gioiTinh, String email, String diaChi, String ngayLapThe, String ngayHetHan) {
        this.docGiaID = docGiaID;
        this.hoTen = hoTen;
        this.cmnd = cmnd;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.email = email;
        this.diaChi = diaChi;
        this.ngayLapThe = ngayLapThe;
        this.ngayHetHan = ngayHetHan;
    }

    public int getDocGiaID() { return docGiaID; }
    public void setDocGiaID(int docGiaID) { this.docGiaID = docGiaID; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public String getCmnd() { return cmnd; }
    public void setCmnd(String cmnd) { this.cmnd = cmnd; }
    public String getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(String ngaySinh) { this.ngaySinh = ngaySinh; }
    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public String getNgayLapThe() { return ngayLapThe; }
    public void setNgayLapThe(String ngayLapThe) { this.ngayLapThe = ngayLapThe; }
    public String getNgayHetHan() { return ngayHetHan; }
    public void setNgayHetHan(String ngayHetHan) { this.ngayHetHan = ngayHetHan; }
}
