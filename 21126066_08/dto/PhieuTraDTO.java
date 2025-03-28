package dto;

public class PhieuTraDTO {
    private int phieuTraID;
    private int phieuMuonID;
    private String ngayTraThucTe;
    private double tienPhat;

    public PhieuTraDTO() {}

    public PhieuTraDTO(int phieuTraID, int phieuMuonID, String ngayTraThucTe, double tienPhat) {
        this.phieuTraID = phieuTraID;
        this.phieuMuonID = phieuMuonID;
        this.ngayTraThucTe = ngayTraThucTe;
        this.tienPhat = tienPhat;
    }

    public int getPhieuTraID() { return phieuTraID; }
    public void setPhieuTraID(int phieuTraID) { this.phieuTraID = phieuTraID; }
    public int getPhieuMuonID() { return phieuMuonID; }
    public void setPhieuMuonID(int phieuMuonID) { this.phieuMuonID = phieuMuonID; }
    public String getNgayTraThucTe() { return ngayTraThucTe; }
    public void setNgayTraThucTe(String ngayTraThucTe) { this.ngayTraThucTe = ngayTraThucTe; }
    public double getTienPhat() { return tienPhat; }
    public void setTienPhat(double tienPhat) { this.tienPhat = tienPhat; }
}
