import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginSystem {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginSystem() {
        frame = new JFrame("Hệ thống đăng nhập");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);  
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
       
        JLabel titleLabel = new JLabel("Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridwidth = 2;
        frame.add(titleLabel, gbc);

        JLabel usernameLabel = new JLabel("Username:  ");
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        frame.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        frame.add(passwordField, gbc);

        JLabel roleLabel = new JLabel("Role:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(roleLabel, gbc);

        String[] roles = {"Student", "Admin"};
        JComboBox<String> roleComboBox = new JComboBox<>(roles);
        gbc.gridx = 1;
        frame.add(roleComboBox, gbc);

        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        frame.add(loginButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String role = (String) roleComboBox.getSelectedItem();

                if (authenticate(username, password, role)) {
                    if (role.equals("Student")) {
                        frame.dispose();
                        new DangKyMonHoc(username, username).setVisible(true); 
                    } else if (role.equals("Admin")) {
                        frame.dispose();
                        new TeacherManagementSystem();
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Tên đăng nhập hoặc mật khẩu sai!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private boolean authenticate(String username, String password, String role) {
        if (role.equalsIgnoreCase("admin")) {
            try (BufferedReader reader = new BufferedReader(new FileReader("Login.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",\\s*");
                    if (data.length == 3) {
                        String fileUsername = data[0];
                        String filePassword = data[1];
                        String fileRole = data[2];
    
                        if (fileUsername.equals(username) && filePassword.equals(password) && fileRole.equals(role)) {
                            return true;
                        }
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Lỗi khi đọc file: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else if (role.equalsIgnoreCase("student")) {
            try (BufferedReader reader = new BufferedReader(new FileReader("DanhsachSV.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",\\s*");
                    if (data.length >= 3) {
                        String studentId = data[0];  
                        String birthYear = data[3];  
                        
                        if (studentId.equals(username) && birthYear.equals(password)) {
                            return true;
                        }
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Lỗi khi đọc file: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    
        return false;
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginSystem::new); 
    }
}
