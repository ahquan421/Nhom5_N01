package Nhom5_N01;

public class Time {
    int hour;
    Time() { setTime(0);}
    Time(int h) { setTime(h);}
    Time setTime(int h) {
        setHour(h);  
        return this; 
    }
    Time setHour(int h) {
        hour = ((h >= 0 && h < 24) ? h : 0);
        return this;
    }
    int getHour() { return hour;}
    public int stringToScreen() {
        return hour;
    }
    public static void main(String[] args) {
        Time time = new Time(10);
        System.out.println("Current hour: " + time.stringToScreen());
    }
}
