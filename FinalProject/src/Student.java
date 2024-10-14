import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public class Student {
    private int sinhvienID;
    private String hoten;
    private Gender gioitinh; 
    private LocalDate ngaysinh;
    private String quequan;
    private String email;

    public enum Gender {
        NAM("Nam"),
        NU("Nữ");
        private String value;
        Gender(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
        public static Gender fromString(String text) {
            for (Gender g : Gender.values()) {
                if (g.value.equalsIgnoreCase(text)) {
                    return g;
                }
            }
            throw new IllegalArgumentException("Giới tính chỉ có thể là 'Nam' hoặc 'Nữ'.");
        }
    }

    public Student() {}

    public int getSinhvienID() {
        return sinhvienID;
    }
    public void setSinhvienID(int sinhvienID) {
        if (sinhvienID <= 0) {
            throw new IllegalArgumentException("Ma sinh vien phai lon hơn 0.");
        }
        this.sinhvienID = sinhvienID;
    }

    public String getHoten() {
        return hoten;
    }
    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public Gender getGioitinh() {
        return gioitinh;
    }
    public void setGioitinh(String gioitinh) {
        this.gioitinh = Gender.fromString(gioitinh);
    }

    public LocalDate getNgaysinh() {
        return ngaysinh;
    }
    public void setNgaysinh(LocalDate ngaysinh) {
        if (!isValidNgaysinh(ngaysinh)) {
            throw new IllegalArgumentException("Ngày sinh không hợp lệ: " + ngaysinh);
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
            throw new IllegalArgumentException("Email không hợp lệ: " + email);
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
        return "Student [sinhvienID=" + sinhvienID + ", hoten=" + hoten + ", gioitinh=" + gioitinh.getValue() + 
                ", ngaysinh=" + ngaysinh + ", quequan=" + quequan + ", email=" + email + "]";
    }
}
