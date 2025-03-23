import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LoginScreen extends JFrame {
    public LoginScreen() {
        setTitle("Login");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setLayout(new BorderLayout());

        JPanel bgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(30, 87, 153), getWidth(), getHeight(), new Color(125, 185, 232));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        bgPanel.setLayout(null);
        
        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBounds(50, 100, 300, 350);
        loginPanel.setLayout(null);
        loginPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

        JLabel titleLabel = new JLabel("Library Login", JLabel.CENTER);
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 20));
        titleLabel.setForeground(new Color(50, 50, 50));
        titleLabel.setBounds(0, 20, 300, 30);
        loginPanel.add(titleLabel);

        // Username
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(20, 70, 100, 20);
        JTextField userField = new JTextField();
        userField.setBounds(20, 95, 260, 30);
        userField.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1));

        // Password
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(20, 140, 100, 20);
        JPasswordField passField = new JPasswordField();
        passField.setBounds(20, 165, 260, 30);
        passField.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1));

        // Nút login
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(20, 220, 260, 40);
        loginButton.setFont(new Font("Poppins", Font.BOLD, 14));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(30, 144, 255));
        loginButton.setBorder(null);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hiệu ứng hover
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(new Color(0, 102, 204));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(new Color(30, 144, 255));
            }
        });

        // Nút thoát
        JButton exitButton = new JButton("X");
        exitButton.setBounds(360, 10, 30, 30);
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));
        exitButton.setForeground(Color.WHITE);
        exitButton.setBackground(Color.RED);
        exitButton.setBorder(null);
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        exitButton.addActionListener(e -> System.exit(0));

        // Thêm các thành phần vào panel login
        loginPanel.add(userLabel);
        loginPanel.add(userField);
        loginPanel.add(passLabel);
        loginPanel.add(passField);
        loginPanel.add(loginButton);
        
        bgPanel.add(exitButton);
        bgPanel.add(loginPanel);
        add(bgPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginScreen::new);
    }
}
