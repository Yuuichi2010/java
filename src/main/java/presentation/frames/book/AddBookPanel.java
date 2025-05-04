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

        SpinnerNumberModel yearModel = new SpinnerNumberModel(2023, 1800, 2100, 1);
        yearSpinner = new JSpinner(yearModel);

        String[] genres = {"Tiểu thuyết", "Phi hư cấu", "Khoa học", "Công nghệ", "Lịch sử",
                "Tiểu sử", "Phát triển bản thân", "Thiếu nhi", "Giáo dục", "Khác"};
        genreComboBox = new JComboBox<>(genres);

        NumberFormat priceFormat = NumberFormat.getNumberInstance();
        priceFormat.setMinimumFractionDigits(2);
        priceFormat.setMaximumFractionDigits(2);
        priceField = new JFormattedTextField(priceFormat);
        priceField.setValue(0.0);
        priceField.setColumns(10);

        SpinnerNumberModel quantityModel = new SpinnerNumberModel(1, 1, 1000, 1);
        quantitySpinner = new JSpinner(quantityModel);
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(51, 102, 153));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleHeaderLabel = new JLabel("Thêm sách mới");
        titleHeaderLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleHeaderLabel.setForeground(Color.WHITE);
        titlePanel.add(titleHeaderLabel);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        isbnField.setPreferredSize(new Dimension(250, 30));
        titleField.setPreferredSize(new Dimension(250, 30));
        authorField.setPreferredSize(new Dimension(250, 30));
        publisherField.setPreferredSize(new Dimension(250, 30));
        yearSpinner.setPreferredSize(new Dimension(100, 30));
        quantitySpinner.setPreferredSize(new Dimension(100, 30));
        genreComboBox.setPreferredSize(new Dimension(250, 30));
        priceField.setPreferredSize(new Dimension(150, 30));

        Font labelFont = new Font("Arial", Font.BOLD, 14);

        // ISBN
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel isbnLabel = new JLabel("Mã ISBN:");
        isbnLabel.setFont(labelFont);
        formPanel.add(isbnLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JPanel isbnPanel = createFormFieldPanel(isbnField, "Nhập mã ISBN");
        formPanel.add(isbnPanel, gbc);

        // Title
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        JLabel titleLabel = new JLabel("Tiêu đề:");
        titleLabel.setFont(labelFont);
        formPanel.add(titleLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JPanel titlePanel2 = createFormFieldPanel(titleField, "Nhập tiêu đề sách");
        formPanel.add(titlePanel2, gbc);

        // Author
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel authorLabel = new JLabel("Tác giả:");
        authorLabel.setFont(labelFont);
        formPanel.add(authorLabel, gbc);

        gbc.gridx = 1;
        JPanel authorPanel = createFormFieldPanel(authorField, "Nhập tên tác giả");
        formPanel.add(authorPanel, gbc);

        // Publisher
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel publisherLabel = new JLabel("Nhà xuất bản:");
        publisherLabel.setFont(labelFont);
        formPanel.add(publisherLabel, gbc);

        gbc.gridx = 1;
        JPanel publisherPanel = createFormFieldPanel(publisherField, "Nhập tên nhà xuất bản");
        formPanel.add(publisherPanel, gbc);

        // Year
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel yearLabel = new JLabel("Năm xuất bản:");
        yearLabel.setFont(labelFont);
        formPanel.add(yearLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(yearSpinner, gbc);

        // Genre
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel genreLabel = new JLabel("Thể loại:");
        genreLabel.setFont(labelFont);
        formPanel.add(genreLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(genreComboBox, gbc);

        // Price
        gbc.gridx = 0;
        gbc.gridy = 6;
        JLabel priceLabel = new JLabel("Giá (VND):");
        priceLabel.setFont(labelFont);
        formPanel.add(priceLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(priceField, gbc);

        // Quantity
        gbc.gridx = 0;
        gbc.gridy = 7;
        JLabel quantityLabel = new JLabel("Số lượng:");
        quantityLabel.setFont(labelFont);
        formPanel.add(quantityLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(quantitySpinner, gbc);

        add(titlePanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
    }

    private JPanel createFormFieldPanel(JTextField textField, String placeholder) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

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

            if (isbn.isEmpty() || title.isEmpty() || author.isEmpty() || publisher.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Vui lòng điền đầy đủ các trường bắt buộc!",
                        "Lỗi xác thực",
                        JOptionPane.ERROR_MESSAGE
                );
                return null;
            }

            return new Book(isbn, title, author, publisher, year, genre, price, quantity);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Dữ liệu không hợp lệ. Vui lòng kiểm tra lại.",
                    "Lỗi nhập liệu",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
            return null;
        }
    }
}
