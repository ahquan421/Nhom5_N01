import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeacherManagementSystem {
    private JFrame frame;

    public TeacherManagementSystem() {
        frame = new JFrame("Admin Dasboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton studentManagementButton = new JButton("Student Management");
        studentManagementButton.setPreferredSize(new Dimension(150, 30));

        JButton subjectManagementButton = new JButton("Subject Management");
        subjectManagementButton.setPreferredSize(new Dimension(150, 30));

        JButton logoutButton = new JButton("Log out");
        logoutButton.setPreferredSize(new Dimension(150, 30));

        buttonPanel.add(studentManagementButton);
        buttonPanel.add(subjectManagementButton);
        buttonPanel.add(logoutButton);

        frame.add(buttonPanel, BorderLayout.CENTER);

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

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TeacherManagementSystem());
    }
}
