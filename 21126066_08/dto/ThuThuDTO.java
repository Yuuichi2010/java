package dto;

public class ThuThuDTO {
    private int thuThuID;
    private String hoTen;
    private String username;
    private String password;
    private String email;

    public ThuThuDTO() {}

    public ThuThuDTO(int thuThuID, String hoTen, String username, String password, String email) {
        this.thuThuID = thuThuID;
        this.hoTen = hoTen;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public int getThuThuID() { return thuThuID; }
    public void setThuThuID(int thuThuID) { this.thuThuID = thuThuID; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
