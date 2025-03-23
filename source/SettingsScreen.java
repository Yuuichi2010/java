import java.awt.*;
import javax.swing.*;

public class SettingsScreen extends JFrame {
    
    public SettingsScreen() {
        super("Advanced Settings");
        // Áp dụng Nimbus LookAndFeel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Panel chính với màu nền nhẹ
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Tiêu đề của trang
        JLabel titleLabel = new JLabel("Application Settings", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(new Color(45, 45, 45));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Tạo JTabbedPane cho các tab
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("General", createGeneralPanel());
        tabbedPane.addTab("Notifications", createNotificationPanel());
        tabbedPane.addTab("Privacy", createPrivacyPanel());
        tabbedPane.addTab("Advanced", createAdvancedPanel());
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Panel chứa nút Save và Cancel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);
        JButton btnSave = new JButton("Save Settings");
        JButton btnCancel = new JButton("Cancel");
        styleButton(btnSave);
        styleButton(btnCancel);
        btnSave.addActionListener(e -> JOptionPane.showMessageDialog(this, "Settings saved!", "Info", JOptionPane.INFORMATION_MESSAGE));
        btnCancel.addActionListener(e -> dispose());
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createGeneralPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        // Cài đặt Theme
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Theme:"), gbc);
        JPanel themePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        themePanel.setOpaque(false);
        JRadioButton rbLight = new JRadioButton("Light", true);
        JRadioButton rbDark = new JRadioButton("Dark");
        JRadioButton rbSystem = new JRadioButton("System Default");
        ButtonGroup themeGroup = new ButtonGroup();
        themeGroup.add(rbLight);
        themeGroup.add(rbDark);
        themeGroup.add(rbSystem);
        themePanel.add(rbLight);
        themePanel.add(rbDark);
        themePanel.add(rbSystem);
        gbc.gridx = 1;
        panel.add(themePanel, gbc);
        
        // Cài đặt ngôn ngữ
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Language:"), gbc);
        JComboBox<String> languageCombo = new JComboBox<>(new String[]{"English", "Vietnamese", "French", "Spanish"});
        gbc.gridx = 1;
        panel.add(languageCombo, gbc);
        
        // Cài đặt Auto-update
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Auto-Update:"), gbc);
        JCheckBox chkAutoUpdate = new JCheckBox();
        chkAutoUpdate.setSelected(true);
        gbc.gridx = 1;
        panel.add(chkAutoUpdate, gbc);
        
        return panel;
    }
    
    private JPanel createNotificationPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setOpaque(false);
        
        panel.add(new JLabel("Email Notifications:"));
        JCheckBox chkEmail = new JCheckBox("Enable", true);
        panel.add(chkEmail);
        
        panel.add(new JLabel("SMS Notifications:"));
        JCheckBox chkSMS = new JCheckBox("Enable");
        panel.add(chkSMS);
        
        panel.add(new JLabel("Push Notifications:"));
        JCheckBox chkPush = new JCheckBox("Enable");
        panel.add(chkPush);
        
        panel.add(new JLabel("Sound Notifications:"));
        JCheckBox chkSound = new JCheckBox("Enable");
        panel.add(chkSound);
        
        return panel;
    }
    
    private JPanel createPrivacyPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setOpaque(false);
        
        panel.add(new JLabel("Share Usage Data:"));
        JCheckBox chkUsage = new JCheckBox("Enable");
        panel.add(chkUsage);
        
        panel.add(new JLabel("Ad Personalization:"));
        JCheckBox chkAds = new JCheckBox("Enable");
        panel.add(chkAds);
        
        panel.add(new JLabel("Location Sharing:"));
        JCheckBox chkLocation = new JCheckBox("Enable");
        panel.add(chkLocation);
        
        panel.add(new JLabel("Clear Search History:"));
        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(e ->
                JOptionPane.showMessageDialog(panel, "Search history cleared."));
        panel.add(btnClear);
        
        return panel;
    }
    
    private JPanel createAdvancedPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Developer Mode:"), gbc);
        JCheckBox chkDev = new JCheckBox("Enable");
        gbc.gridx = 1;
        panel.add(chkDev, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Enable Logging:"), gbc);
        JCheckBox chkLog = new JCheckBox("Enable", true);
        gbc.gridx = 1;
        panel.add(chkLog, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Max Cache Size (MB):"), gbc);
        JSlider sliderCache = new JSlider(50, 500, 100);
        sliderCache.setMajorTickSpacing(50);
        sliderCache.setMinorTickSpacing(10);
        sliderCache.setPaintTicks(true);
        sliderCache.setPaintLabels(true);
        gbc.gridx = 1;
        panel.add(sliderCache, gbc);
        
        return panel;
    }
    
    // Hàm tạo kiểu cho các nút
    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(new Color(60, 149, 246));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SettingsScreen().setVisible(true));
    }
}
