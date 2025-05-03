package presentation.frames;

import business.LibrarianService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    private LibrarianService librarianService;

    public LoginFrame() {
        librarianService = new LibrarianService();

        initComponents();
        layoutComponents();
        registerListeners();

        setTitle("Library Management System - Login");
        setSize(450, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void initComponents() {
        usernameField = new RoundJTextField(20);
        passwordField = new RoundJPasswordField(20);
        loginButton = new RoundedButton("Login");
        registerButton = new RoundedButton("Register");
    }

    private void layoutComponents() {
        // Main panel with gradient background
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth(), h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(45, 85, 130), 0, h, new Color(34, 67, 101));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setBorder(new EmptyBorder(0, 0, 0, 0));

        // Create login form panel with white background
        JPanel loginFormPanel = new JPanel();
        loginFormPanel.setLayout(new BoxLayout(loginFormPanel, BoxLayout.Y_AXIS));
        loginFormPanel.setBackground(Color.WHITE);
        loginFormPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        // Logo panel
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoPanel.setBackground(Color.WHITE);

        // Logo image (book icon)
        ImageIcon logoIcon = createBookIcon(100, 100);
        JLabel logoLabel = new JLabel(logoIcon);
        logoPanel.add(logoLabel);

        // Title panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Library Management System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(45, 85, 130));
        titlePanel.add(titleLabel);

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Username field
        JPanel usernamePanel = new JPanel(new BorderLayout(10, 10));
        usernamePanel.setBackground(Color.WHITE);
        usernamePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        usernamePanel.add(usernameLabel, BorderLayout.NORTH);
        usernamePanel.add(usernameField, BorderLayout.CENTER);

        // Password field
        JPanel passwordPanel = new JPanel(new BorderLayout(10, 10));
        passwordPanel.setBackground(Color.WHITE);
        passwordPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        passwordPanel.add(passwordLabel, BorderLayout.NORTH);
        passwordPanel.add(passwordField, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // Style buttons
        loginButton.setBackground(new Color(45, 85, 130));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(200, 40));

        registerButton.setBackground(Color.WHITE);
        registerButton.setForeground(new Color(45, 85, 130));
        registerButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        registerButton.setFocusPainted(false);
        registerButton.setBorder(BorderFactory.createLineBorder(new Color(45, 85, 130), 1));
        registerButton.setPreferredSize(new Dimension(200, 40));

        // Center buttons
        JPanel loginButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginButtonPanel.setBackground(Color.WHITE);
        loginButtonPanel.add(loginButton);

        JPanel registerButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        registerButtonPanel.setBackground(Color.WHITE);
        registerButtonPanel.add(registerButton);

        buttonPanel.add(loginButtonPanel);
        buttonPanel.add(registerButtonPanel);

        // Add all components to form panel
        formPanel.add(usernamePanel);
        formPanel.add(passwordPanel);
        formPanel.add(buttonPanel);

        // Add all panels to login form
        loginFormPanel.add(logoPanel);
        loginFormPanel.add(titlePanel);
        loginFormPanel.add(formPanel);

        // Add login form to main panel with some margin
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new GridBagLayout());
        wrapperPanel.setOpaque(false);
        wrapperPanel.add(loginFormPanel);

        mainPanel.add(wrapperPanel, BorderLayout.CENTER);

        // Set content pane
        setContentPane(mainPanel);
    }


    private void registerListeners() {
        // Login button
        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                showErrorMessage("Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu.");
                return;
            }

            boolean authenticated = librarianService.authenticate(username, password);
            if (authenticated) {
                dispose();
                new MainFrame(username).setVisible(true);
            } else {
                showErrorMessage("Tên đăng nhập hoặc mật khẩu không đúng.");
            }
        });

        // Register button
        registerButton.addActionListener(e -> {
            dispose();
            new RegisterFrame().setVisible(true);
        });
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(
                LoginFrame.this,
                message,
                "Lỗi Đăng Nhập",
                JOptionPane.ERROR_MESSAGE
        );
    }

    // Create a custom book icon
    private ImageIcon createBookIcon(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // Enable anti-aliasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw three books
        g2d.setColor(new Color(30, 30, 30));

        // Book 1
        g2d.fillRect(width/5, height/4, width/5, height/2);

        // Book 2
        g2d.fillRect(2*width/5, height/5, width/5, 3*height/5);

        // Book 3 (slightly tilted)
        g2d.rotate(Math.toRadians(15), 3*width/5 + width/10, height/2);
        g2d.fillRect(3*width/5, height/4, width/5, height/2);

        g2d.dispose();
        return new ImageIcon(image);
    }

    // Custom rounded JTextField
    private class RoundJTextField extends JTextField {
        private Shape shape;

        public RoundJTextField(int size) {
            super(size);
            setOpaque(false);
            setFont(new Font("Arial", Font.PLAIN, 14));
            setPreferredSize(new Dimension(300, 35));
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
        }

        protected void paintComponent(Graphics g) {
            g.setColor(getBackground());
            g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
            super.paintComponent(g);
        }

        protected void paintBorder(Graphics g) {
            g.setColor(getForeground());
            g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
        }

        public boolean contains(int x, int y) {
            if (shape == null || !shape.getBounds().equals(getBounds())) {
                shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15);
            }
            return shape.contains(x, y);
        }
    }

    // Custom rounded JPasswordField
    private class RoundJPasswordField extends JPasswordField {
        private Shape shape;

        public RoundJPasswordField(int size) {
            super(size);
            setOpaque(false);
            setFont(new Font("Arial", Font.PLAIN, 14));
            setPreferredSize(new Dimension(300, 35));
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
        }

        protected void paintComponent(Graphics g) {
            g.setColor(getBackground());
            g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
            super.paintComponent(g);
        }

        protected void paintBorder(Graphics g) {
            g.setColor(getForeground());
            g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
        }

        public boolean contains(int x, int y) {
            if (shape == null || !shape.getBounds().equals(getBounds())) {
                shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15);
            }
            return shape.contains(x, y);
        }
    }

    // Custom rounded button
    private class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text);
            setOpaque(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (getModel().isPressed()) {
                g2.setColor(getBackground().darker());
            } else if (getModel().isRollover()) {
                g2.setColor(getBackground().brighter());
            } else {
                g2.setColor(getBackground());
            }

            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

            g2.setColor(getForeground());
            g2.setFont(getFont());
            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(getText())) / 2;
            int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
            g2.drawString(getText(), x, y);

            g2.dispose();
        }
    }

    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}