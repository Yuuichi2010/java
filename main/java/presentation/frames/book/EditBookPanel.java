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
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // ISBN
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("ISBN:"), gbc);

        gbc.gridx = 1;
        add(isbnField, gbc);

        // Tên sách
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Tên sách:"), gbc);

        gbc.gridx = 1;
        add(titleField, gbc);

        // Tác giả
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Tác giả:"), gbc);

        gbc.gridx = 1;
        add(authorField, gbc);

        // Nhà xuất bản
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Nhà xuất bản:"), gbc);

        gbc.gridx = 1;
        add(publisherField, gbc);

        // Năm xuất bản
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Năm xuất bản:"), gbc);

        gbc.gridx = 1;
        add(yearSpinner, gbc);

        // Thể loại
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Thể loại:"), gbc);

        gbc.gridx = 1;
        add(genreComboBox, gbc);

        // Giá
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("Giá:"), gbc);

        gbc.gridx = 1;
        add(priceField, gbc);

        // Số lượng
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(new JLabel("Số lượng:"), gbc);

        gbc.gridx = 1;
        add(quantitySpinner, gbc);
    }

    private void populateFields() {
        isbnField.setText(originalBook.getISBN());
        titleField.setText(originalBook.getTitle());
        authorField.setText(originalBook.getAuthor());
        publisherField.setText(originalBook.getPublisher());
        yearSpinner.setValue(originalBook.getPublishYear());

        // Tìm và chọn thể loại phù hợp
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

            // Kiểm tra nhập liệu
            if (isbn.isEmpty() || title.isEmpty() || author.isEmpty() || publisher.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Tất cả các trường đều là bắt buộc!",
                        "Lỗi xác thực",
                        JOptionPane.ERROR_MESSAGE
                );
                return null;
            }

            return new Book(isbn, title, author, publisher, year, genre, price, quantity);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Nhập liệu không hợp lệ. Vui lòng kiểm tra lại thông tin.",
                    "Lỗi nhập liệu",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
            return null;
        }
    }
}
