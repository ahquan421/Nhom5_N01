package Nhom5_N01.Midterm;

public class Main {
    public static void main(String[] args) {
        double[] x = {1, 2, 3, 4, 5};
        DiscreteSignal tinHieu = new DiscreteSignal(1.0, 1.0, 1.0, 1.0, x);
        Radar radar = new Radar(tinHieu);

        System.out.println("Gia tri X(4): " + radar.layGiaTriX(4));
    }
}
