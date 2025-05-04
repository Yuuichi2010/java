package presentation.frames;

import business.LibrarianService;
import entities.Librarian;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField fullNameField;
    private JTextField emailField;
    private JButton registerButton;
    private JButton cancelButton;

    private LibrarianService librarianService;

    public RegisterFrame() {
        librarianService = new LibrarianService();

        initComponents();
        layoutComponents();
        registerListeners();

        setTitle("Hệ Thống Quản Lý Thư Viện - Đăng Ký Thủ Thư");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initComponents() {
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);
        fullNameField = new JTextField(20);
        emailField = new JTextField(20);
        registerButton = new JButton("Đăng Ký");
        cancelButton = new JButton("Hủy");
    }

    private void layoutComponents() {
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Đăng Ký Thủ Thư Mới");
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        headerPanel.add(titleLabel);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Tên đăng nhập:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(usernameField, gbc);

        // Full Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Họ và tên:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(fullNameField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(emailField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Mật khẩu:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(passwordField, gbc);

        // Confirm Password
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Xác nhận mật khẩu:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(confirmPasswordField, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);

        // Add panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Set content pane
        setContentPane(mainPanel);
    }

    private void registerListeners() {
        // Register button
        registerButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String fullName = fullNameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            // Validate input
            if (username.isEmpty() || fullName.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(
                        RegisterFrame.this,
                        "Tất cả các trường đều là bắt buộc!",
                        "Lỗi đăng ký",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(
                        RegisterFrame.this,
                        "Mật khẩu không khớp!",
                        "Lỗi đăng ký",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            if (password.length() < 6) {
                JOptionPane.showMessageDialog(
                        RegisterFrame.this,
                        "Mật khẩu phải có ít nhất 6 ký tự!",
                        "Lỗi đăng ký",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Create librarian object
            Librarian librarian = new Librarian();
            librarian.setUsername(username);
            librarian.setFullName(fullName);
            librarian.setEmail(email);
            librarian.setPassword(password);

            // Register librarian
            boolean success = librarianService.registerLibrarian(librarian);
            if (success) {
                JOptionPane.showMessageDialog(
                        RegisterFrame.this,
                        "Đăng ký thành công! Bạn có thể đăng nhập bằng thông tin đã đăng ký.",
                        "Thành công đăng ký",
                        JOptionPane.INFORMATION_MESSAGE
                );
                dispose();
                new LoginFrame().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(
                        RegisterFrame.this,
                        "Tên đăng nhập đã tồn tại hoặc xảy ra lỗi. Vui lòng thử lại.",
                        "Lỗi đăng ký",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // Cancel button
        cancelButton.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
    }
}