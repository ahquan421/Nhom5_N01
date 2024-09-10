package Nhom5_N01.Midterm;

public class Radar {
    private DiscreteSignal signal;
	
	public Radar(DiscreteSignal signal) {
		this.signal = signal;
	}
	
    public double[] phanTichTinHieu() {
        double[] X = new double[16];
        for (int n = 0; n <= 15; n++) {
            X[n] = n >= 0 && n <= 15 ? 1 - (double)n / 15 : 0;
        }
        return X;
    }
    
    public double layGiaTriX(int n) {
        double[] X = phanTichTinHieu();
        return X[n];
    }

    public static void main(String[] args) {
        double[] x = {1, 2, 3, 4, 5}; 
        DiscreteSignal signal = new DiscreteSignal(1.0, 1.0, 1.0, 1.0, x);
        Radar radar = new Radar(signal);

        System.out.println("Gia tri X(4): " + radar.layGiaTriX(4));
    }
}
