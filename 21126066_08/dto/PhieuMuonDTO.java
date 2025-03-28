package dto;

public class PhieuMuonDTO {
    private int phieuMuonID;
    private int docGiaID;
    private String ngayMuon;
    private String ngayTraDuKien;

    public PhieuMuonDTO() {}

    public PhieuMuonDTO(int phieuMuonID, int docGiaID, String ngayMuon, String ngayTraDuKien) {
        this.phieuMuonID = phieuMuonID;
        this.docGiaID = docGiaID;
        this.ngayMuon = ngayMuon;
        this.ngayTraDuKien = ngayTraDuKien;
    }

    public int getPhieuMuonID() { return phieuMuonID; }
    public void setPhieuMuonID(int phieuMuonID) { this.phieuMuonID = phieuMuonID; }
    public int getDocGiaID() { return docGiaID; }
    public void setDocGiaID(int docGiaID) { this.docGiaID = docGiaID; }
    public String getNgayMuon() { return ngayMuon; }
    public void setNgayMuon(String ngayMuon) { this.ngayMuon = ngayMuon; }
    public String getNgayTraDuKien() { return ngayTraDuKien; }
    public void setNgayTraDuKien(String ngayTraDuKien) { this.ngayTraDuKien = ngayTraDuKien; }
}
