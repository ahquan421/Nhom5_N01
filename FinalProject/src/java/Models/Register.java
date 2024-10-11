package java.Models;
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
        this.ngaydangki = ngaydangki;
    }
    public int getSinhvienID() {
        return sinhvienID;
    }
    public void setSinhvienID(int sinhvienID) {
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
        this.ngaydangki = ngaydangki;
        this.sinhvienID = sinhvienID;
        this.monhocID = monhocID;
    }

    @Override
    public String toString() {
        return "Register [madangki=" + madangki + ", ngaydangki=" + ngaydangki + ", sinhvienID=" + sinhvienID + ", monhocID=" + monhocID + "]";
    }
}
