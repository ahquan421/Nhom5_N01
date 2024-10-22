import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class MonHoc {
    private String maMonHoc;
    private String tenMonHoc;
    private int soTinChi;
    private String ngayBatDau;
    private String ngayKetThuc;

    public MonHoc(String maMonHoc, String tenMonHoc, int soTinChi, String ngayBatDau, String ngayKetThuc) {
        this.maMonHoc = maMonHoc;
        this.tenMonHoc = tenMonHoc;
        this.soTinChi = soTinChi;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
    }

    @Override
    public String toString() {
        return tenMonHoc + " (" + maMonHoc + ", " + soTinChi + " tín chỉ, " + ngayBatDau + " - " + ngayKetThuc + ")"; // Cập nhật toString để hiển thị mã môn học
    }
}

public class DangKyMonHoc extends JFrame {
    private ArrayList<JCheckBox> checkBoxList = new ArrayList<>();
    private String tenSinhVien;
    private String maSinhVien;
    private ArrayList<String> monHocDaDangKyList = new ArrayList<>();

    private JPanel panelMain;
    private JPanel panelButtons;

    public DangKyMonHoc(String tenSinhVien, String maSinhVien) {
        this.tenSinhVien = tenSinhVien;
        this.maSinhVien = maSinhVien;

        setTitle("Giao diện đăng ký môn học cho sinh viên!");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panelMain = new JPanel();
        panelMain.setLayout(new BorderLayout());

        JButton btnThongTinSinhVien = new JButton("Thông tin sinh viên");
        btnThongTinSinhVien.addActionListener(e -> hienThiThongTinSinhVien());

        JButton btnDangKyMonHoc = new JButton("Đăng ký môn học");
        
        btnDangKyMonHoc.addActionListener(e -> hienThiGiaoDienDangKyMonHoc());

        JButton btnLogout = new JButton("Đăng xuất");
        btnLogout.addActionListener(e -> quayTroVeDangNhap());

        panelButtons = new JPanel();
        panelButtons.setLayout(new FlowLayout());
        panelButtons.add(btnThongTinSinhVien);
        panelButtons.add(btnDangKyMonHoc);
        panelButtons.add(btnLogout); 

        panelMain.add(panelButtons, BorderLayout.NORTH);
        add(panelMain);
    }
    private void quayTroVeDangNhap() {
        dispose(); 
        new LoginSystem(); 
    }

    private void hienThiThongTinSinhVien() {
        panelMain.removeAll(); 
        JPanel panelThongTin = new JPanel();
        panelThongTin.setLayout(new BoxLayout(panelThongTin, BoxLayout.Y_AXIS));

        try {
            String[] thongTinSinhVien = layThongTinSinhVien(maSinhVien);
            if (thongTinSinhVien != null) {
                panelThongTin.add(new JLabel("Họ tên: " + thongTinSinhVien[1]));
                panelThongTin.add(new JLabel("Mã sinh viên: " + thongTinSinhVien[0]));
                panelThongTin.add(new JLabel("Giới tính: " + thongTinSinhVien[2]));
                panelThongTin.add(new JLabel("Năm sinh: " + thongTinSinhVien[3]));
                panelThongTin.add(new JLabel("Quê quán: " + thongTinSinhVien[4]));
                panelThongTin.add(new JLabel("Gmail: " + thongTinSinhVien[5]));
                panelThongTin.add(new JLabel("Các môn học đã đăng ký:")); 

                String fileName = "SV" + maSinhVien + "_Dangky.txt";
                StringBuilder monHocDaDangKy = new StringBuilder();

                try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        monHocDaDangKy.append(line).append("\n");
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Sinh viên chưa đăng ký môn học nào! ");
                }

                if (monHocDaDangKy.length() > 0) {
                    String[] monHocs = monHocDaDangKy.toString().split("\n"); 
                    for (String monHoc : monHocs) {
                        panelThongTin.add(new JLabel(monHoc));
                    }
                } else {
                    panelThongTin.add(new JLabel("Chưa đăng ký môn học nào."));
                }

            } else {
                panelThongTin.add(new JLabel("Không tìm thấy thông tin sinh viên."));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi đọc file: " + e.getMessage());
        }

        panelMain.add(panelThongTin, BorderLayout.CENTER);

        JButton btnBack = new JButton("Quay lại trang chủ");
        btnBack.addActionListener(e -> quayTroVeTrangChu());
        panelMain.add(btnBack, BorderLayout.SOUTH);

        panelMain.revalidate();
        panelMain.repaint();
    }

    private void hienThiGiaoDienDangKyMonHoc() {
        panelMain.removeAll();
        checkBoxList.clear();

        JPanel panelMonHoc = new JPanel();
        panelMonHoc.setLayout(new BoxLayout(panelMonHoc, BoxLayout.Y_AXIS));
        panelMonHoc.setBorder(BorderFactory.createTitledBorder("Chọn môn học"));

        try {
            ArrayList<MonHoc> danhSachMonHoc = docMonHocTuFile("DanhsachMH.txt");
            for (MonHoc monHoc : danhSachMonHoc) {
                JCheckBox checkBox = new JCheckBox(monHoc.toString());
                checkBoxList.add(checkBox);
                panelMonHoc.add(checkBox);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi đọc file: " + e.getMessage());
        }

        JButton btnXacNhan = new JButton("Xác nhận đăng ký");

        JButton btnQuayLai = new JButton("Quay lại trang chủ");
        btnQuayLai.addActionListener(e -> quayTroVeTrangChu());

        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelButtons.add(btnXacNhan);
        panelButtons.add(btnQuayLai);

        btnXacNhan.addActionListener(e -> {
            StringBuilder monHocDaDangKy = new StringBuilder();
            StringBuilder monHocTrung = new StringBuilder();
            boolean hasDuplicate = false; 

            for (JCheckBox checkBox : checkBoxList) {
                if (checkBox.isSelected()) {
                    String monHoc = checkBox.getText();
                    if (monHocDaDangKyList.contains(monHoc)) {
                        monHocTrung.append(monHoc).append("\n");
                        hasDuplicate = true;
                    } else {
                        monHocDaDangKyList.add(monHoc); 
                        monHocDaDangKy.append(monHoc).append("\n");
                    }
                }
            }

            if (monHocTrung.length() > 0) {
                JOptionPane.showMessageDialog(null, "Bạn đã đăng ký các môn học sau đây trước đó:\n" + monHocTrung.toString());
            }

            if (monHocDaDangKy.toString().isEmpty() && !hasDuplicate) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn ít nhất một môn học!");
            } else if (!monHocDaDangKy.toString().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Đăng ký thành công!\n" +
                        "Họ tên: " + tenSinhVien + "\n" +
                        "Mã sinh viên: " + maSinhVien + "\n" +
                        "Môn học đã đăng ký:\n" + monHocDaDangKy.toString());

                String fileName = "SV" + maSinhVien + "_Dangky.txt"; 
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                    writer.write(monHocDaDangKy.toString());
                    writer.write("\n"); 
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(null, "Lỗi khi ghi vào file: " + ioException.getMessage());
                }
            }

            panelMain.revalidate();
            panelMain.repaint();
        });

        panelMain.add(new JScrollPane(panelMonHoc), BorderLayout.CENTER);
        panelMain.add(panelButtons, BorderLayout.SOUTH); 

        panelMain.revalidate();
        panelMain.repaint();
    }

    private String[] layThongTinSinhVien(String maSinhVien) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("DanhsachSV.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts[0].equals(maSinhVien)) {
                    return parts;
                }
            }
        }
        return null;
    }

    private void quayTroVeTrangChu() {
        panelMain.removeAll();
        panelMain.add(panelButtons, BorderLayout.NORTH);
        panelMain.revalidate();
        panelMain.repaint();
    }

    private ArrayList<MonHoc> docMonHocTuFile(String filePath) throws IOException {
        ArrayList<MonHoc> danhSachMonHoc = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(", ");
                String maMonHoc = parts[0]; 
                String tenMonHoc = parts[1];
                int soTinChi = Integer.parseInt(parts[2]);
                String ngayBatDau = parts[3];
                String ngayKetThuc = parts[4];
                danhSachMonHoc.add(new MonHoc(maMonHoc, tenMonHoc, soTinChi, ngayBatDau, ngayKetThuc)); 
            }
        }
        return danhSachMonHoc;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DangKyMonHoc("Tên Sinh Viên", "Mã Sinh Viên").setVisible(true));
    }
}