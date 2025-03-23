import java.awt.*;
import javax.swing.*;

public class ProfileScreen extends JFrame {

    public ProfileScreen() {
        super("Profile");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Áp dụng Nimbus LookAndFeel nếu có thể
        try { 
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); 
        } catch (Exception e) { 
            e.printStackTrace();
        }
        
        // Panel chính với màu nền nhẹ và border
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Tiêu đề với font chữ hiện đại và màu sắc tinh tế
        JLabel titleLabel = new JLabel("User Profile", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(new Color(45, 45, 45));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Panel trung tâm chứa thông tin người dùng
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setOpaque(false);
        
        // Ảnh đại diện (Hãy thay đổi đường dẫn đến ảnh của bạn)
        JLabel picLabel = new JLabel();
        picLabel.setIcon(new ImageIcon("path/to/your/profile_picture.png"));
        picLabel.setPreferredSize(new Dimension(150, 150));
        picLabel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        centerPanel.add(picLabel, BorderLayout.WEST);
        
        // Panel chứa form thông tin với GridBagLayout cho sự cân đối
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        appendFormComponent(formPanel, new JLabel("Username:"), 
                            new JTextField("JohnDoe"), gbc, 0, false);
        appendFormComponent(formPanel, new JLabel("Full Name:"), 
                            new JTextField("John Doe"), gbc, 1, true);
        appendFormComponent(formPanel, new JLabel("Email:"), 
                            new JTextField("john.doe@example.com"), gbc, 2, true);
        appendFormComponent(formPanel, new JLabel("Phone:"), 
                            new JTextField("0123456789"), gbc, 3, true);
        appendFormComponent(formPanel, new JLabel("Password:"), 
                            new JPasswordField("password"), gbc, 4, true);
        
        centerPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Panel chứa nút Save và Cancel với thiết kế đẹp mắt
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setOpaque(false);
        JButton btnSave = new JButton("Save");
        JButton btnCancel = new JButton("Cancel");
        styleButton(btnSave);
        styleButton(btnCancel);
        
        btnSave.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Profile saved successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
        });
        btnCancel.addActionListener(e -> dispose());
        
        bottomPanel.add(btnSave);
        bottomPanel.add(btnCancel);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    // Hàm tiện ích thêm components vào form
    private void appendFormComponent(JPanel panel, JLabel label, JComponent field, 
                                     GridBagConstraints gbc, int row, boolean editable) {
        if (field instanceof JTextField) {
            ((JTextField) field).setEditable(editable);
            ((JTextField) field).setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }
        if (field instanceof JPasswordField) {
            ((JPasswordField) field).setEditable(editable);
            ((JPasswordField) field).setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(label, gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }
    
    // Hàm để tạo kiểu cho các nút
    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(new Color(60, 149, 246));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProfileScreen().setVisible(true));
    }
}
