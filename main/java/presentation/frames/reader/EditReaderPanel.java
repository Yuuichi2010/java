package presentation.frames.reader;

import entities.Reader;
import utils.DateUtils;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class EditReaderPanel extends JPanel {
    private JTextField readerIDField;
    private JTextField fullNameField;
    private JTextField identityCardField;
    private JTextField dateOfBirthField;
    private JComboBox<String> genderComboBox;
    private JTextField emailField;
    private JTextArea addressArea;
    private JTextField registrationDateField;
    private JLabel expirationDateLabel;
    private Reader originalReader;

    public EditReaderPanel(Reader reader) {
        this.originalReader = reader;
        initComponents();
        layoutComponents();
        populateFields();
    }

    private void initComponents() {
        readerIDField = new JTextField();
        readerIDField.setEditable(false); // Mã bạn đọc không thể thay đổi

        fullNameField = new JTextField();
        identityCardField = new JTextField();
        dateOfBirthField = new JTextField();

        String[] genders = {"Nam", "Nữ", "Khác"};
        genderComboBox = new JComboBox<>(genders);

        emailField = new JTextField();
        addressArea = new JTextArea(3, 20);
        addressArea.setLineWrap(true);
        addressArea.setWrapStyleWord(true);

        registrationDateField = new JTextField();
        expirationDateLabel = new JLabel();
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());

        // Panel tiêu đề
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(51, 102, 153));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleHeaderLabel = new JLabel("Chỉnh sửa thông tin bạn đọc");
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
        JLabel readerIDLabel = new JLabel("Mã bạn đọc:");
        readerIDLabel.setFont(labelFont);
        formPanel.add(readerIDLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(readerIDField, gbc);

        // Họ tên
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        JLabel fullNameLabel = new JLabel("Họ tên:");
        fullNameLabel.setFont(labelFont);
        formPanel.add(fullNameLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(fullNameField, gbc);

        // Số CMND/CCCD
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        JLabel identityCardLabel = new JLabel("Số CMND/CCCD:");
        identityCardLabel.setFont(labelFont);
        formPanel.add(identityCardLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(identityCardField, gbc);

        // Ngày sinh
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        JLabel dateOfBirthLabel = new JLabel("Ngày sinh:");
        dateOfBirthLabel.setFont(labelFont);
        formPanel.add(dateOfBirthLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(dateOfBirthField, gbc);

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
        formPanel.add(emailField, gbc);

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
        formPanel.add(registrationDateField, gbc);

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

        // Thêm thông tin về ngày
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

    private void populateFields() {
        if (originalReader != null) {
            readerIDField.setText(originalReader.getReaderID());
            fullNameField.setText(originalReader.getFullName());
            identityCardField.setText(originalReader.getIdentityCard());
            dateOfBirthField.setText(DateUtils.formatDisplayDate(originalReader.getDateOfBirth()));

            // Set giới tính đã chọn
            for (int i = 0; i < genderComboBox.getItemCount(); i++) {
                if (genderComboBox.getItemAt(i).equals(originalReader.getGender())) {
                    genderComboBox.setSelectedIndex(i);
                    break;
                }
            }

            emailField.setText(originalReader.getEmail());
            addressArea.setText(originalReader.getAddress());
            registrationDateField.setText(DateUtils.formatDisplayDate(originalReader.getRegistrationDate()));
            expirationDateLabel.setText(DateUtils.formatDisplayDate(originalReader.getExpirationDate()));
        }
    }

    private void updateExpirationDate() {
        String registrationDateStr = registrationDateField.getText();
        if (!registrationDateStr.isEmpty()) {
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

            // Phân tích ngày
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
