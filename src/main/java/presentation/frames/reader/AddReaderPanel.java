package presentation.frames.reader;

import entities.Reader;
import utils.DateUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.UUID;

public class AddReaderPanel extends JPanel {
    private JTextField readerIDField;
    private JTextField fullNameField;
    private JTextField identityCardField;
    private JTextField dateOfBirthField;
    private JComboBox<String> genderComboBox;
    private JTextField emailField;
    private JTextArea addressArea;
    private JTextField registrationDateField;
    private JLabel expirationDateLabel;

    public AddReaderPanel() {
        initComponents();
        layoutComponents();
    }

    private void initComponents() {
        // Tạo ID bạn đọc tự động
        String randomID = "R" + String.format("%03d", (int) (Math.random() * 900 + 100));
        readerIDField = new JTextField(randomID);

        fullNameField = new JTextField();
        identityCardField = new JTextField();
        dateOfBirthField = new JTextField();

        String[] genders = {"Nam", "Nữ", "Khác"};
        genderComboBox = new JComboBox<>(genders);

        emailField = new JTextField();
        addressArea = new JTextArea(3, 20);
        addressArea.setLineWrap(true);
        addressArea.setWrapStyleWord(true);

        // Đặt ngày hiện tại làm ngày đăng ký
        LocalDate today = LocalDate.now();
        registrationDateField = new JTextField(DateUtils.formatDisplayDate(today));

        // Tính ngày hết hạn (48 tháng kể từ ngày đăng ký)
        LocalDate expirationDate = today.plusMonths(48);
        expirationDateLabel = new JLabel(DateUtils.formatDisplayDate(expirationDate));
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());

        // Panel tiêu đề
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(51, 102, 153));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleHeaderLabel = new JLabel("Thêm người đọc mới");
        titleHeaderLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleHeaderLabel.setForeground(Color.WHITE);
        titlePanel.add(titleHeaderLabel);

        // Panel form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Style các trường dữ liệu
        readerIDField.setPreferredSize(new Dimension(250, 30));
        fullNameField.setPreferredSize(new Dimension(250, 30));
        identityCardField.setPreferredSize(new Dimension(250, 30));
        dateOfBirthField.setPreferredSize(new Dimension(250, 30));
        emailField.setPreferredSize(new Dimension(250, 30));
        registrationDateField.setPreferredSize(new Dimension(250, 30));

        // Style các nhãn
        Font labelFont = new Font("Arial", Font.BOLD, 14);

        // Mã bạn đọc
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel readerIDLabel = new JLabel("Mã người đọc:");
        readerIDLabel.setFont(labelFont);
        formPanel.add(readerIDLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JPanel readerIDPanel = createFormFieldPanel(readerIDField, "Tự động tạo");
        formPanel.add(readerIDPanel, gbc);

        // Họ tên
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        JLabel fullNameLabel = new JLabel("Họ tên:");
        fullNameLabel.setFont(labelFont);
        formPanel.add(fullNameLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JPanel fullNamePanel = createFormFieldPanel(fullNameField, "Nhập họ tên người đọc");
        formPanel.add(fullNamePanel, gbc);

        // Số CMND/CCCD
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        JLabel identityCardLabel = new JLabel("Số CMND/CCCD:");
        identityCardLabel.setFont(labelFont);
        formPanel.add(identityCardLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JPanel identityCardPanel = createFormFieldPanel(identityCardField, "Nhập số CMND/CCCD");
        formPanel.add(identityCardPanel, gbc);

        // Ngày sinh
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        JLabel dateOfBirthLabel = new JLabel("Ngày sinh:");
        dateOfBirthLabel.setFont(labelFont);
        formPanel.add(dateOfBirthLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JPanel dateOfBirthPanel = createFormFieldPanel(dateOfBirthField, "DD/MM/YYYY");
        formPanel.add(dateOfBirthPanel, gbc);

        // Giới tính
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        JLabel genderLabel = new JLabel("Giới tính:");
        genderLabel.setFont(labelFont);
        formPanel.add(genderLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        genderComboBox.setPreferredSize(new Dimension(250, 30));
        formPanel.add(genderComboBox, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.0;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(labelFont);
        formPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JPanel emailPanel = createFormFieldPanel(emailField, "Nhập địa chỉ email");
        formPanel.add(emailPanel, gbc);

        // Địa chỉ
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0.0;
        JLabel addressLabel = new JLabel("Địa chỉ:");
        addressLabel.setFont(labelFont);
        formPanel.add(addressLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JScrollPane addressScrollPane = new JScrollPane(addressArea);
        addressScrollPane.setPreferredSize(new Dimension(250, 80));
        addressScrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        formPanel.add(addressScrollPane, gbc);

        // Ngày đăng ký
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.weightx = 0.0;
        JLabel registrationDateLabel = new JLabel("Ngày đăng ký:");
        registrationDateLabel.setFont(labelFont);
        formPanel.add(registrationDateLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JPanel registrationDatePanel = createFormFieldPanel(registrationDateField, "DD/MM/YYYY");
        formPanel.add(registrationDatePanel, gbc);

        // Ngày hết hạn
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.weightx = 0.0;
        JLabel expDateTitleLabel = new JLabel("Ngày hết hạn:");
        expDateTitleLabel.setFont(labelFont);
        formPanel.add(expDateTitleLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        expirationDateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(expirationDateLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 8, 8, 8);

        JPanel dateHelpPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dateHelpPanel.add(new JLabel("Lưu ý: Định dạng ngày là DD/MM/YYYY"));
        formPanel.add(dateHelpPanel, gbc);

        add(titlePanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);

        registrationDateField.addActionListener(e -> updateExpirationDate());
        registrationDateField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                updateExpirationDate();
            }
        });
    }

    private JPanel createFormFieldPanel(JTextField textField, String placeholder) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        if (textField.getText().isEmpty()) {
            textField.setForeground(Color.GRAY);
            textField.setText(placeholder);
        }

        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });

        panel.add(textField, BorderLayout.CENTER);
        return panel;
    }

    private void updateExpirationDate() {
        String registrationDateStr = registrationDateField.getText();
        if (!registrationDateStr.equals("DD/MM/YYYY") && !registrationDateStr.isEmpty()) {
            LocalDate registrationDate = DateUtils.parseDisplayDate(registrationDateStr);
            if (registrationDate != null) {
                LocalDate expirationDate = registrationDate.plusMonths(48);
                expirationDateLabel.setText(DateUtils.formatDisplayDate(expirationDate));
            }
        }
    }

    public Reader getReader() {
        try {
            String readerID = readerIDField.getText().trim();
            String fullName = fullNameField.getText().trim();
            String identityCard = identityCardField.getText().trim();
            String dateOfBirthStr = dateOfBirthField.getText().trim();
            String gender = (String) genderComboBox.getSelectedItem();
            String email = emailField.getText().trim();
            String address = addressArea.getText().trim();
            String registrationDateStr = registrationDateField.getText().trim();

            if (readerID.isEmpty() || fullName.isEmpty() || identityCard.isEmpty() ||
                    dateOfBirthStr.isEmpty() || gender == null || registrationDateStr.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Các trường bắt buộc không được để trống.",
                        "Lỗi xác thực",
                        JOptionPane.ERROR_MESSAGE
                );
                return null;
            }

            if (fullNameField.getForeground() == Color.GRAY ||
                    identityCardField.getForeground() == Color.GRAY ||
                    dateOfBirthField.getForeground() == Color.GRAY ||
                    emailField.getForeground() == Color.GRAY ||
                    registrationDateField.getForeground() == Color.GRAY) {
                JOptionPane.showMessageDialog(
                        this,
                        "Vui lòng điền đầy đủ các trường bắt buộc với thông tin hợp lệ.",
                        "Lỗi nhập liệu",
                        JOptionPane.ERROR_MESSAGE
                );
                return null;
            }

            LocalDate dateOfBirth = DateUtils.parseDisplayDate(dateOfBirthStr);
            LocalDate registrationDate = DateUtils.parseDisplayDate(registrationDateStr);

            if (dateOfBirth == null || registrationDate == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Định dạng ngày không hợp lệ. Vui lòng sử dụng định dạng DD/MM/YYYY.",
                        "Lỗi ngày tháng",
                        JOptionPane.ERROR_MESSAGE
                );
                return null;
            }

            if (dateOfBirth.isAfter(LocalDate.now())) {
                JOptionPane.showMessageDialog(
                        this,
                        "Ngày sinh phải là ngày trong quá khứ.",
                        "Lỗi xác thực",
                        JOptionPane.ERROR_MESSAGE
                );
                return null;
            }

            LocalDate expirationDate = registrationDate.plusMonths(48);

            Reader reader = new Reader();
            reader.setReaderID(readerID);
            reader.setFullName(fullName);
            reader.setIdentityCard(identityCard);
            reader.setDateOfBirth(dateOfBirth);
            reader.setGender(gender);
            reader.setEmail(email);
            reader.setAddress(address);
            reader.setRegistrationDate(registrationDate);
            reader.setExpirationDate(expirationDate);

            return reader;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi nhập liệu. Vui lòng kiểm tra lại thông tin.\nLỗi: " + e.getMessage(),
                    "Lỗi nhập liệu",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
            return null;
        }
    }
}
