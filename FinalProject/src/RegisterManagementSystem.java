import javax.swing.*;
import java.awt.*;

public class RegisterManagementSystem {
    private JFrame frame;

    public RegisterManagementSystem() {
        frame = new JFrame("Dang ki mon hoc");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);

        JLabel messageLabel = new JLabel("Chuc nang nay dang duoc phat trien", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));

        frame.add(messageLabel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegisterManagementSystem::new);
    }
}


