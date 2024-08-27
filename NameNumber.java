package Nhom5_N01;

public class NameNumber {
    private String lastName;
    private String telNumber;
    
    NameNumber() {}

    public NameNumber(String name, String num) {
        this.lastName = name;
        this.telNumber = num;
    }
    public String getLastName() {
        return lastName;
    }

    public String getTelNumber() {
        return telNumber;
    }

}