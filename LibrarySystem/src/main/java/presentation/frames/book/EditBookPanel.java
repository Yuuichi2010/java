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
        isbnField.setEditable(false); // ISBN cannot be modified
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

        // Title
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Title:"), gbc);

        gbc.gridx = 1;
        add(titleField, gbc);

        // Author
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Author:"), gbc);

        gbc.gridx = 1;
        add(authorField, gbc);

        // Publisher
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Publisher:"), gbc);

        gbc.gridx = 1;
        add(publisherField, gbc);

        // Year
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Publish Year:"), gbc);

        gbc.gridx = 1;
        add(yearSpinner, gbc);

        // Genre
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Genre:"), gbc);

        gbc.gridx = 1;
        add(genreComboBox, gbc);

        // Price
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("Price:"), gbc);

        gbc.gridx = 1;
        add(priceField, gbc);

        // Quantity
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(new JLabel("Quantity:"), gbc);

        gbc.gridx = 1;
        add(quantitySpinner, gbc);
    }

    private void populateFields() {
        isbnField.setText(originalBook.getISBN());
        titleField.setText(originalBook.getTitle());
        authorField.setText(originalBook.getAuthor());
        publisherField.setText(originalBook.getPublisher());
        yearSpinner.setValue(originalBook.getPublishYear());

        // Find and select the matching genre
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