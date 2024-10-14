package Views;

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
        frame = new JFrame("He thong dang nhap");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        JLabel titleLabel = new JLabel("Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0; 
        gbc.insets = new Insets(10, 0, 10, 0); 
        gbc.gridwidth = 2;
        frame.add(titleLabel, gbc);

        JLabel usernameLabel = new JLabel("Ten dang nhap:  ");
        gbc.gridwidth = 1; 
        gbc.gridx = 0; 
        gbc.gridy = 1; 
        frame.add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1; 
        frame.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Mat khau:");
        gbc.gridx = 0; 
        gbc.gridy = 2; 
        frame.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1; 
        frame.add(passwordField, gbc);

        JLabel roleLabel = new JLabel("Vai tro:");
        gbc.gridx = 0; 
        gbc.gridy = 3; 
        frame.add(roleLabel, gbc);

        String[] roles = {"Student", "Admin"};
        JComboBox<String> roleComboBox = new JComboBox<>(roles);
        gbc.gridx = 1; 
        frame.add(roleComboBox, gbc);

        JButton loginButton = new JButton("Dang nhap");
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
                    JOptionPane.showMessageDialog(frame, "Dang nhap thanh cong!", "Thong bao", JOptionPane.INFORMATION_MESSAGE);
                    if (role.equals("Student")) {
                        //Them dasboard dang ki sau
                    } else if (role.equals("Admin")) {
                        new TeacherManagementSystem();
                    }
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Ten dang nhap hoac mat khau sai!", "Loi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.setVisible(true);
    }

    private boolean authenticate(String username, String password, String role) {
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
            JOptionPane.showMessageDialog(frame, "Loi khi doc file: " + e.getMessage(), "Loi", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginSystem::new);
    }
}
