import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

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
        if (sinhvienID <= 0) {
            throw new IllegalArgumentException("Ma sinh vien phai lon hon 0.");
        }
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
        if (!gioitinh.equalsIgnoreCase("Nam") && !gioitinh.equalsIgnoreCase("Nu")) {
            throw new IllegalArgumentException("Gioi tinh chi co the la 'Nam' hoac 'Nu'.");
        }
        this.gioitinh = gioitinh;
    }

    public LocalDate getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(LocalDate ngaysinh) {
        if (!isValidNgaysinh(ngaysinh)) {
            throw new IllegalArgumentException("Ngay sinh khong hop le: " + ngaysinh);
        }
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
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Email khong hop le: " + email);
        }
        this.email = email;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }

    private boolean isValidNgaysinh(LocalDate ngaysinh) {
        LocalDate today = LocalDate.now();
        Period period = Period.between(ngaysinh, today);
        return !ngaysinh.isAfter(today) && period.getYears() <= 100;
    }

    public Student(int sinhvienID, String hoten, String gioitinh, LocalDate ngaysinh, String quequan, String email) {
        setSinhvienID(sinhvienID);
        this.hoten = hoten;
        setGioitinh(gioitinh);
        setNgaysinh(ngaysinh);
        this.quequan = quequan;
        setEmail(email);
    }

    @Override
    public String toString() {
        return "Student [sinhvienID=" + sinhvienID + ", hoten=" + hoten + ", gioitinh=" + gioitinh + 
                ", ngaysinh=" + ngaysinh + ", quequan=" + quequan + ", email=" + email + "]";
    }
}
