import java.util.regex.Pattern;

public class Student {
    private int sinhvienID;
    private String hoten;
    private Gender gioitinh; 
    private int namsinh;
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

    public int getNamsinh() {
        return namsinh;
    }
    public void setNamsinh(int namsinh) {
        this.namsinh = namsinh;
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

    public Student(int sinhvienID, String hoten, String gioitinh, int namsinh, String quequan, String email) {
        setSinhvienID(sinhvienID);
        this.hoten = hoten;
        setGioitinh(gioitinh);
        this.namsinh = namsinh;
        this.quequan = quequan;
        setEmail(email);
    }

    @Override
    public String toString() {
        return "Student [sinhvienID=" + sinhvienID + ", hoten=" + hoten + ", gioitinh=" + gioitinh.getValue() + ", namsinh=" + namsinh + ", quequan=" + quequan + ", email=" + email + "]";
    }
}
