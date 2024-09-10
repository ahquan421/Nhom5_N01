package Nhom5_N01.Midterm;

public class ContinuousSignal implements Signal {
    private double bienDo;
    private double tanSo;
    private double chuKy;
    private double buocSong;

    public ContinuousSignal(double bienDo, double tanSo, double chuKy, double buocSong) {
        this.bienDo = bienDo;
        this.tanSo = tanSo;
        this.chuKy = chuKy;
        this.buocSong = buocSong;
    }
    

    @Override
    public double getBienDo() {
        return bienDo;
    }

    @Override
    public double getTanSo() { return tanSo; }

    @Override
    public double getChuKy() {
        return chuKy;
    }

    @Override
    public double getBuocSong() {
        return buocSong;
    }
}
