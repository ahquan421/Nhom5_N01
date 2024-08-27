package Nhom5_N01;
public class L2_P3 {
    static void f(Number m) {
        m.i = 15;
    }
    public static void main(String[] args) {
        Number n = new Number();
        n.i = 14;
        f(n);
        System.out.println("n.i = " + n.i);
    } 
}