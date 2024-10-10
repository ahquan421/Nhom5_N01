public class Student {
    private int sinhvienID;
    private String hoten;
    private String gioitinh;
    private int namsinh;
    private String quequan;
    private String email;

    public int getSinhvienID(){
        return sinhvienID;
    }
    public void setSinhvienID(int sinhvienID){
        this.sinhvienID = sinhvienID;
    }
    public String getHoten(){
        return hoten;
    }
    public void setHoten(String hoten){
        this.hoten = hoten;
    }
    public String getGioitinh(){
        return gioitinh;
    }
    public void setGioitinh(String gioitinh){
        this.gioitinh = gioitinh;
    }
    public int getNamsinh(){
        return namsinh;
    }
    public void setNamsinh(int namsinh){
        this.namsinh = namsinh;
    }
    public String getQuequan(){
        return quequan;
    }
    public void setQuequan(String quequan){
        this.quequan = quequan;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public Student(int sinhvienID, String hoten, String gioitinh, int namsinh, String quequan, String email ){
        super();
        this.sinhvienID = sinhvienID;
        this.hoten = hoten;
        this.gioitinh = gioitinh;
        this.namsinh = namsinh;
        this.quequan = quequan;
        this.email = email;
    }

    @Override
    public String toString(){
        return "Student [sinhvienID =" + sinhvienID +", hoten=" + hoten +", gioitinh=" + gioitinh +", namsinh="+ namsinh+", quequan =" + quequan +", email ="+ email +"]";
    }

}