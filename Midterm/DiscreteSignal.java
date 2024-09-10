package Nhom5_N01.Midterm;
public class DiscreteSignal implements Signal {
    private double bienDo;
    private double tanSo;
    private double chuKy;
    private double buocSong;
    private double[] x;

    public DiscreteSignal(double bienDo, double tanSo, double chuKy, double buocSong, double[] x) {
        this.bienDo = bienDo;
        this.tanSo = tanSo;
        this.chuKy = chuKy;
        this.buocSong = buocSong;
        this.x = x;
    }

    @Override
    public double getBienDo() {
        return bienDo;
    }

    @Override
    public double getTanSo() {
        return tanSo;
    }

    @Override
    public double getChuKy() {
        return chuKy;
    }

    @Override
    public double getBuocSong() {
        return buocSong;
    }
    
    public double x(int n) {
        double result = 0;
        for (int k = 0; k < x.length; k++) {
            result += x[k] * delta(n - k);
        }
        return result;
    }
    private int delta(int n) {
        return n == 0 ? 1 : 0;
    }
}

