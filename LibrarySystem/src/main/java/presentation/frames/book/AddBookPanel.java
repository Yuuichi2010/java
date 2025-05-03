package presentation.frames.book;

import entities.Book;
import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;

public class AddBookPanel extends JPanel {
    private JTextField isbnField;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField publisherField;
    private JSpinner yearSpinner;
    private JComboBox<String> genreComboBox;
    private JFormattedTextField priceField;
    private JSpinner quantitySpinner;

    public AddBookPanel() {
        initComponents();
        layoutComponents();
    }

    private void initComponents() {
        isbnField = new JTextField(20);
        titleField = new JTextField(20);
        authorField = new JTextField(20);
        publisherField = new JTextField(20);

        // Year spinner
        SpinnerNumberModel yearModel = new SpinnerNumberModel(2023, 1800, 2100, 1);
        yearSpinner = new JSpinner(yearModel);

        // Genre combo box
        String[] genres = {"Fiction", "Non-Fiction", "Science", "Technology", "History",
                "Biography", "Self-Help", "Children", "Education", "Other"};
        genreComboBox = new JComboBox<>(genres);

        // Price field with number format
        NumberFormat priceFormat = NumberFormat.getNumberInstance();
        priceFormat.setMinimumFractionDigits(2);
        priceFormat.setMaximumFractionDigits(2);
        priceField = new JFormattedTextField(priceFormat);
        priceField.setValue(0.0);
        priceField.setColumns(10);

        // Quantity spinner
        SpinnerNumberModel quantityModel = new SpinnerNumberModel(1, 1, 1000, 1);
        quantitySpinner = new JSpinner(quantityModel);
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());

        // Title panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(51, 102, 153));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleHeaderLabel = new JLabel("Add New Book");
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
        isbnField.setPreferredSize(new Dimension(250, 30));
        titleField.setPreferredSize(new Dimension(250, 30));
        authorField.setPreferredSize(new Dimension(250, 30));
        publisherField.setPreferredSize(new Dimension(250, 30));

        // Style the spinners
        yearSpinner.setPreferredSize(new Dimension(100, 30));
        quantitySpinner.setPreferredSize(new Dimension(100, 30));

        // Style the labels
        Font labelFont = new Font("Arial", Font.BOLD, 14);

        // ISBN
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel isbnLabel = new JLabel("ISBN:");
        isbnLabel.setFont(labelFont);
        formPanel.add(isbnLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JPanel isbnPanel = createFormFieldPanel(isbnField, "Enter the ISBN number");
        formPanel.add(isbnPanel, gbc);

        // Title
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setFont(labelFont);
        formPanel.add(titleLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JPanel titlePanel2 = createFormFieldPanel(titleField, "Enter the book title");
        formPanel.add(titlePanel2, gbc);

        // Author
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setFont(labelFont);
        formPanel.add(authorLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JPanel authorPanel = createFormFieldPanel(authorField, "Enter the author name");
        formPanel.add(authorPanel, gbc);

        // Publisher
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        JLabel publisherLabel = new JLabel("Publisher:");
        publisherLabel.setFont(labelFont);
        formPanel.add(publisherLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JPanel publisherPanel = createFormFieldPanel(publisherField, "Enter the publisher name");
        formPanel.add(publisherPanel, gbc);

        // Year
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        JLabel yearLabel = new JLabel("Publish Year:");
        yearLabel.setFont(labelFont);
        formPanel.add(yearLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(yearSpinner, gbc);

        // Genre
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.0;
        JLabel genreLabel = new JLabel("Genre:");
        genreLabel.setFont(labelFont);
        formPanel.add(genreLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;

        // Style the combo box
        genreComboBox.setPreferredSize(new Dimension(250, 30));
        formPanel.add(genreComboBox, gbc);

        // Price
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0.0;
        JLabel priceLabel = new JLabel("Price (VND):");
        priceLabel.setFont(labelFont);
        formPanel.add(priceLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        priceField.setPreferredSize(new Dimension(150, 30));
        formPanel.add(priceField, gbc);

        // Quantity
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.weightx = 0.0;
        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setFont(labelFont);
        formPanel.add(quantityLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(quantitySpinner, gbc);

        // Add panels to main panel
        add(titlePanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
    }

    // Helper method to create form field panel with placeholder text
    private JPanel createFormFieldPanel(JTextField textField, String placeholder) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Add placeholder text
        textField.setForeground(Color.GRAY);
        textField.setText(placeholder);

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

    public Book getBook() {
        try {
            String isbn = isbnField.getText().trim();
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            String publisher = publisherField.getText().trim();
            int year = (Integer) yearSpinner.getValue();
            String genre = (String) genreComboBox.getSelectedItem();
            double price = ((Number) priceField.getValue()).doubleValue();
            int quantity = (Integer) quantitySpinner.getValue();

            // Validate input
            if (isbn.isEmpty() || title.isEmpty() || author.isEmpty() || publisher.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "All fields are required!",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return null;
            }

            return new Book(isbn, title, author, publisher, year, genre, price, quantity);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid input. Please check your entries.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
            return null;
        }
    }
}