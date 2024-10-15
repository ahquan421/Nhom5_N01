package Views;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeacherManagementSystem {
    private JFrame frame;

    public TeacherManagementSystem() {
        frame = new JFrame("He thong quan ly giao vien");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLayout(new GridLayout(4, 1));

        JLabel titleLabel = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18)); 
        frame.add(titleLabel); 

        JPanel studentPanel = new JPanel();
        JPanel subjectPanel = new JPanel();
        JPanel logoutPanel = new JPanel(); 

        JButton studentManagementButton = new JButton("Quan ly hoc sinh");
        JButton subjectManagementButton = new JButton("Quan ly mon hoc");
        JButton logoutButton = new JButton("Dang xuat");

        studentManagementButton.setPreferredSize(new Dimension(150, 30));
        subjectManagementButton.setPreferredSize(new Dimension(150, 30));
        logoutButton.setPreferredSize(new Dimension(150, 30));

        studentPanel.add(studentManagementButton);
        subjectPanel.add(subjectManagementButton);
        logoutPanel.add(logoutButton);

        studentPanel.setPreferredSize(new Dimension(200, 40)); 
        subjectPanel.setPreferredSize(new Dimension(200, 40));
        logoutPanel.setPreferredSize(new Dimension(200, 40));

        frame.add(studentPanel);
        frame.add(subjectPanel);
        frame.add(logoutPanel);

        studentManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StudentManagementSystem(); 
            }
        });

        subjectManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SubjectManagementSystem(); 
            }
        });

        logoutButton.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); 
                new LoginSystem(); 
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TeacherManagementSystem());
    }

    public void showTeacherManagement() {
        throw new UnsupportedOperationException("Unimplemented method 'showTeacherManagement'");
    }
}
