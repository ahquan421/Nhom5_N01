import java.time.LocalDate;

public class Register {
    private String madangki;
    private LocalDate ngaydangki;
    private int sinhvienID;
    private String monhocID;

    public Register() {}

    public String getMadangki() {
        return madangki;
    }

    public void setMadangki(String madangki) {
        this.madangki = madangki;
    }

    public LocalDate getNgaydangki() {
        return ngaydangki;
    }

    public void setNgaydangki(LocalDate ngaydangki) {
        if (!isValidRegistrationDate(ngaydangki)) {
            throw new IllegalArgumentException("Ngay dang ki khong hop le.");
        }
        this.ngaydangki = ngaydangki;
    }

    public int getSinhvienID() {
        return sinhvienID;
    }
    public void setSinhvienID(int sinhvienID) {
        if (sinhvienID <= 0) {
            throw new IllegalArgumentException("ID khong hop le.");
        }
        this.sinhvienID = sinhvienID;
    }

    public String getMonhocID() {
        return monhocID;
    }
    public void setMonhocID(String monhocID) {
        this.monhocID = monhocID;
    }

    public Register(String madangki, LocalDate ngaydangki, int sinhvienID, String monhocID) {
        this.madangki = madangki;
        setNgaydangki(ngaydangki);
        setSinhvienID(sinhvienID); 
        this.monhocID = monhocID;
    }

    private boolean isValidRegistrationDate(LocalDate ngaydangki) {
        // Ngày đăng ký không được là trong tương lai
        return !ngaydangki.isAfter(LocalDate.now());
    }
    @Override
    public String toString() {
        return "Register [madangki=" + madangki + ", ngaydangki=" + ngaydangki + ", sinhvienID=" + sinhvienID + ", monhocID=" + monhocID + "]";
    }
}
