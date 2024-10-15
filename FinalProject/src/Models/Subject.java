package Models;
import java.time.LocalDate;

public class Subject {
    private String monhocID;
    private String tenmonhoc;
    private int sotc;
    private LocalDate ngaybatdau;
    private LocalDate ngayketthuc;

    public Subject() {}

    public String getMonhocID() {
        return monhocID;
    }
    public void setMonhocID(String monhocID) {
        this.monhocID = monhocID;
    }

    public String getTenmonhoc() { 
        return tenmonhoc;
    }
    public void setTenmonhoc(String tenmonhoc) {
        this.tenmonhoc = tenmonhoc;
    }

    public int getSotc() {
        return sotc;
    }
    public void setSotc(int sotc) {
        if (sotc < 0) { 
            throw new IllegalArgumentException("So tin chi khong the nho hon 0.");
        }
        this.sotc = sotc;
    }

    public LocalDate getNgaybatdau() {
        return ngaybatdau;
    }
    public void setNgaybatdau(LocalDate ngaybatdau) {
        this.ngaybatdau = ngaybatdau;
    }

    public LocalDate getNgayketthuc() {
        return ngayketthuc;
    }
    public void setNgayketthuc(LocalDate ngayketthuc) {
        if (ngayketthuc.isBefore(ngaybatdau)) {
            throw new IllegalArgumentException("Ngay ket thuc khong the truoc ngay bat dau.");
        }
        this.ngayketthuc = ngayketthuc;
    }    

    public Subject(String monhocID, String tenmonhoc, int sotc, LocalDate ngaybatdau, LocalDate ngayketthuc) {
        this.monhocID = monhocID;
        this.tenmonhoc = tenmonhoc;
        setSotc(sotc);
        setNgaybatdau(ngaybatdau);
        setNgayketthuc(ngayketthuc);
    }

    @Override
    public String toString() {
        return "Subject [monhocID=" + monhocID + ", tenmonhoc=" + tenmonhoc + ", sotc=" + sotc + ", ngaybatdau=" + ngaybatdau + ", ngayketthuc=" + ngayketthuc + "]";
    }

}
