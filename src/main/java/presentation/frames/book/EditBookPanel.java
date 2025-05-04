package presentation.frames.book;

import entities.Book;
import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;

public class EditBookPanel extends JPanel {
    private JTextField isbnField;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField publisherField;
    private JSpinner yearSpinner;
    private JComboBox<String> genreComboBox;
    private JFormattedTextField priceField;
    private JSpinner quantitySpinner;
    private Book originalBook;

    public EditBookPanel(Book book) {
        this.originalBook = book;
        initComponents();
        layoutComponents();
        populateFields();
    }

    private void initComponents() {
        isbnField = new JTextField(20);
        isbnField.setEditable(false); // ISBN không thể thay đổi
        titleField = new JTextField(20);
        authorField = new JTextField(20);
        publisherField = new JTextField(20);

        // Spinner cho năm
        SpinnerNumberModel yearModel = new SpinnerNumberModel(2023, 1800, 2100, 1);
        yearSpinner = new JSpinner(yearModel);

        // ComboBox cho thể loại
        String[] genres = {"Tiểu thuyết", "Phi tiểu thuyết", "Khoa học", "Công nghệ", "Lịch sử",
                "Tiểu sử", "Sách tự lực", "Trẻ em", "Giáo dục", "Khác"};
        genreComboBox = new JComboBox<>(genres);

        // Trường giá với định dạng số
        NumberFormat priceFormat = NumberFormat.getNumberInstance();
        priceFormat.setMinimumFractionDigits(2);
        priceFormat.setMaximumFractionDigits(2);
        priceField = new JFormattedTextField(priceFormat);
        priceField.setValue(0.0);
        priceField.setColumns(10);

        // Spinner cho số lượng
        SpinnerNumberModel quantityModel = new SpinnerNumberModel(0, 0, 1000, 1);
        quantitySpinner = new JSpinner(quantityModel);
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245)); // Màu nền nhẹ nhàng

        // Tiêu đề
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(51, 102, 153));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleHeaderLabel = new JLabel("Chỉnh sửa thông tin sách");
        titleHeaderLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleHeaderLabel.setForeground(Color.WHITE);
        titlePanel.add(titleHeaderLabel);

        // Bảng nhập liệu
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Cấu hình các trường nhập liệu
        setFieldDimensions(isbnField, titleField, authorField, publisherField, priceField);
        setFieldDimensions(yearSpinner, genreComboBox, quantitySpinner);

        Font labelFont = new Font("Arial", Font.BOLD, 14);

        // ISBN
        gbc.gridx = 0;
        gbc.gridy = 0;
        addLabel(formPanel, "Mã ISBN:", gbc);
        gbc.gridx = 1;
        addField(formPanel, isbnField, gbc);

        // Tiêu đề
        gbc.gridx = 0;
        gbc.gridy = 1;
        addLabel(formPanel, "Tên sách:", gbc);
        gbc.gridx = 1;
        addField(formPanel, titleField, gbc);

        // Tác giả
        gbc.gridx = 0;
        gbc.gridy = 2;
        addLabel(formPanel, "Tác giả:", gbc);
        gbc.gridx = 1;
        addField(formPanel, authorField, gbc);

        // Nhà xuất bản
        gbc.gridx = 0;
        gbc.gridy = 3;
        addLabel(formPanel, "Nhà xuất bản:", gbc);
        gbc.gridx = 1;
        addField(formPanel, publisherField, gbc);

        // Năm xuất bản
        gbc.gridx = 0;
        gbc.gridy = 4;
        addLabel(formPanel, "Năm xuất bản:", gbc);
        gbc.gridx = 1;
        formPanel.add(yearSpinner, gbc);

        // Thể loại
        gbc.gridx = 0;
        gbc.gridy = 5;
        addLabel(formPanel, "Thể loại:", gbc);
        gbc.gridx = 1;
        formPanel.add(genreComboBox, gbc);

        // Giá
        gbc.gridx = 0;
        gbc.gridy = 6;
        addLabel(formPanel, "Giá (VND):", gbc);
        gbc.gridx = 1;
        formPanel.add(priceField, gbc);

        // Số lượng
        gbc.gridx = 0;
        gbc.gridy = 7;
        addLabel(formPanel, "Số lượng:", gbc);
        gbc.gridx = 1;
        formPanel.add(quantitySpinner, gbc);

        add(titlePanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
    }

    private void setFieldDimensions(JComponent... components) {
        for (JComponent component : components) {
            component.setPreferredSize(new Dimension(250, 30));
        }
    }

    private void addLabel(JPanel panel, String text, GridBagConstraints gbc) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(70, 70, 70));
        panel.add(label, gbc);
    }

    private void addField(JPanel panel, JComponent field, GridBagConstraints gbc) {
        JPanel fieldPanel = new JPanel(new BorderLayout());
        fieldPanel.setBackground(Color.WHITE);
        fieldPanel.add(field, BorderLayout.CENTER);
        panel.add(fieldPanel, gbc);
    }

    private void populateFields() {
        isbnField.setText(originalBook.getISBN());
        titleField.setText(originalBook.getTitle());
        authorField.setText(originalBook.getAuthor());
        publisherField.setText(originalBook.getPublisher());
        yearSpinner.setValue(originalBook.getPublishYear());

        for (int i = 0; i < genreComboBox.getItemCount(); i++) {
            if (genreComboBox.getItemAt(i).equals(originalBook.getGenre())) {
                genreComboBox.setSelectedIndex(i);
                break;
            }
        }

        priceField.setValue(originalBook.getPrice());
        quantitySpinner.setValue(originalBook.getQuantity());
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
                JOptionPane.showMessageDialog(this, "Tất cả các trường đều là bắt buộc!", "Lỗi xác thực", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            return new Book(isbn, title, author, publisher, year, genre, price, quantity);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Nhập liệu không hợp lệ. Vui lòng kiểm tra lại thông tin.", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }
}
