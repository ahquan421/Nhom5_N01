package Models;
import java.time.LocalDate;

public class Student {
    private int sinhvienID;
    private String hoten;
    private String gioitinh;
    private LocalDate ngaysinh;
    private String quequan;
    private String email;

    public Student() {}

    public int getSinhvienID() {
        return sinhvienID;
    }
    public void setSinhvienID(int sinhvienID) {
        this.sinhvienID = sinhvienID;
    }

    public String getHoten() {
        return hoten;
    }
    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public LocalDate getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(LocalDate ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getQuequan() {
        return quequan;
    }

    public void setQuequan(String quequan) {
        this.quequan = quequan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Student(int sinhvienID, String hoten, String gioitinh, LocalDate ngaysinh, String quequan, String email) {
        this.sinhvienID = sinhvienID;
        this.hoten = hoten;
        this.gioitinh = gioitinh;
        this.ngaysinh = ngaysinh;
        this.quequan = quequan;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Student [sinhvienID=" + sinhvienID + ", hoten=" + hoten + ", gioitinh=" + gioitinh + 
                ", ngaysinh=" + ngaysinh + ", quequan=" + quequan + ", email=" + email + "]";
    }
}
