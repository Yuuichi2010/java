package dto;

public class SachDTO {
    private int sachID;
    private String ISBN;
    private String tenSach;
    private String tacGia;
    private String nhaXuatBan;
    private int namXuatBan;
    private String theLoai;
    private double gia;
    private int soLuong;

    public SachDTO() {}

    public SachDTO(int sachID, String ISBN, String tenSach, String tacGia, String nhaXuatBan, int namXuatBan, String theLoai, double gia, int soLuong) {
        this.sachID = sachID;
        this.ISBN = ISBN;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.nhaXuatBan = nhaXuatBan;
        this.namXuatBan = namXuatBan;
        this.theLoai = theLoai;
        this.gia = gia;
        this.soLuong = soLuong;
    }

    public int getSachID() { return sachID; }
    public void setSachID(int sachID) { this.sachID = sachID; }
    public String getISBN() { return ISBN; }
    public void setISBN(String ISBN) { this.ISBN = ISBN; }
    public String getTenSach() { return tenSach; }
    public void setTenSach(String tenSach) { this.tenSach = tenSach; }
    public String getTacGia() { return tacGia; }
    public void setTacGia(String tacGia) { this.tacGia = tacGia; }
    public String getNhaXuatBan() { return nhaXuatBan; }
    public void setNhaXuatBan(String nhaXuatBan) { this.nhaXuatBan = nhaXuatBan; }
    public int getNamXuatBan() { return namXuatBan; }
    public void setNamXuatBan(int namXuatBan) { this.namXuatBan = namXuatBan; }
    public String getTheLoai() { return theLoai; }
    public void setTheLoai(String theLoai) { this.theLoai = theLoai; }
    public double getGia() { return gia; }
    public void setGia(double gia) { this.gia = gia; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
}
