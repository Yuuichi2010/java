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
        readerIDField.setEditable(false); // Reader ID cannot be modified

        fullNameField = new JTextField();
        identityCardField = new JTextField();
        dateOfBirthField = new JTextField();

        String[] genders = {"Male", "Female", "Other"};
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

        // Title panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(51, 102, 153));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleHeaderLabel = new JLabel("Edit Reader");
        titleHeaderLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleHeaderLabel.setForeground(Color.WHITE);
        titlePanel.add(titleHeaderLabel);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Style the form fields
        readerIDField.setPreferredSize(new Dimension(250, 30));
        fullNameField.setPreferredSize(new Dimension(250, 30));
        identityCardField.setPreferredSize(new Dimension(250, 30));
        dateOfBirthField.setPreferredSize(new Dimension(250, 30));
        emailField.setPreferredSize(new Dimension(250, 30));
        registrationDateField.setPreferredSize(new Dimension(250, 30));

        // Style the labels
        Font labelFont = new Font("Arial", Font.BOLD, 14);

        // Reader ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel readerIDLabel = new JLabel("Reader ID:");
        readerIDLabel.setFont(labelFont);
        formPanel.add(readerIDLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(readerIDField, gbc);

        // Full Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        JLabel fullNameLabel = new JLabel("Full Name:");
        fullNameLabel.setFont(labelFont);
        formPanel.add(fullNameLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(fullNameField, gbc);

        // Identity Card
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        JLabel identityCardLabel = new JLabel("Identity Card:");
        identityCardLabel.setFont(labelFont);
        formPanel.add(identityCardLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(identityCardField, gbc);

        // Date of Birth
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        JLabel dateOfBirthLabel = new JLabel("Date of Birth:");
        dateOfBirthLabel.setFont(labelFont);
        formPanel.add(dateOfBirthLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(dateOfBirthField, gbc);

        // Gender
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(labelFont);
        formPanel.add(genderLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;

        // Style the combo box
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

        // Address
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0.0;
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setFont(labelFont);
        formPanel.add(addressLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;

        // Address area with scroll pane
        JScrollPane addressScrollPane = new JScrollPane(addressArea);
        addressScrollPane.setPreferredSize(new Dimension(250, 80));
        addressScrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        formPanel.add(addressScrollPane, gbc);

        // Registration Date
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.weightx = 0.0;
        JLabel registrationDateLabel = new JLabel("Registration Date:");
        registrationDateLabel.setFont(labelFont);
        formPanel.add(registrationDateLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(registrationDateField, gbc);

        // Expiration Date
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.weightx = 0.0;
        JLabel expDateTitleLabel = new JLabel("Expiration Date:");
        expDateTitleLabel.setFont(labelFont);
        formPanel.add(expDateTitleLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        expirationDateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(expirationDateLabel, gbc);

        // Add date information
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 8, 8, 8);

        JPanel dateHelpPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dateHelpPanel.add(new JLabel("Note: Date format is DD/MM/YYYY"));
        formPanel.add(dateHelpPanel, gbc);

        // Add panels to main panel
        add(titlePanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);

        // Update expiration date when registration date changes
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

            // Set gender selection
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

            // Validate input
            if (readerID.isEmpty() || fullName.isEmpty() || identityCard.isEmpty() ||
                    dateOfBirthStr.isEmpty() || gender == null || registrationDateStr.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Required fields cannot be empty.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return null;
            }

            // Parse dates
            LocalDate dateOfBirth = DateUtils.parseDisplayDate(dateOfBirthStr);
            LocalDate registrationDate = DateUtils.parseDisplayDate(registrationDateStr);

            if (dateOfBirth == null || registrationDate == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Invalid date format. Please use DD/MM/YYYY format.",
                        "Date Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return null;
            }

            // Validate date of birth (should be in the past)
            if (dateOfBirth.isAfter(LocalDate.now())) {
                JOptionPane.showMessageDialog(
                        this,
                        "Date of birth must be in the past.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return null;
            }

            // Calculate expiration date (48 months from registration date)
            LocalDate expirationDate = registrationDate.plusMonths(48);

            // Create reader object
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
                    "Invalid input. Please check your entries.\nError: " + e.getMessage(),
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
            return null;
        }
    }
}