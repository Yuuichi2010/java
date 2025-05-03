package presentation.frames.book;

import business.BookService;
import entities.Book;
import presentation.models.BookTableModel;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BookListPanel extends JPanel {
    private JTable bookTable;
    private BookTableModel bookTableModel;
    private BookService bookService;
    private JTextField searchField;
    private JButton searchButton;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton refreshButton;

    public BookListPanel() {
        bookService = new BookService();
        initComponents();
        layoutComponents();
        registerListeners();
        loadBooks();
    }

    private void initComponents() {
        bookTableModel = new BookTableModel();
        bookTable = new JTable(bookTableModel);
        bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookTable.setAutoCreateRowSorter(true);

        searchField = new JTextField(20);
        searchButton = new JButton("Search by Title");
        addButton = new JButton("Add New Book");
        editButton = new JButton("Edit Book");
        deleteButton = new JButton("Delete Book");
        refreshButton = new JButton("Refresh");

        // Initially disable edit and delete buttons
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }

    private void layoutComponents() {
        setLayout(new BorderLayout(0, 10));

        // Header panel with title and search
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(51, 102, 153));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("Book Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Search panel with rounded border
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setOpaque(false);

        searchField.setPreferredSize(new Dimension(250, 30));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(2, 10, 2, 10)
        ));

        searchButton.setBackground(new Color(230, 230, 230));
        searchButton.setForeground(new Color(60, 60, 60));
        searchButton.setFocusPainted(false);

        refreshButton.setBackground(new Color(230, 230, 230));
        refreshButton.setForeground(new Color(60, 60, 60));
        refreshButton.setFocusPainted(false);

        searchPanel.add(new JLabel("Search: "));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(refreshButton);
        headerPanel.add(searchPanel, BorderLayout.EAST);

        // Table panel with border
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        // Style the table
        bookTable.setRowHeight(30);
        bookTable.setIntercellSpacing(new Dimension(10, 5));
        bookTable.setGridColor(new Color(230, 230, 230));
        bookTable.setSelectionBackground(new Color(224, 236, 254));
        bookTable.setSelectionForeground(Color.BLACK);
        bookTable.setShowVerticalLines(false);
        bookTable.setFont(new Font("Arial", Font.PLAIN, 14));

        // Set header appearance
        bookTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        bookTable.getTableHeader().setForeground(new Color(70, 70, 70));
        bookTable.getTableHeader().setBackground(new Color(240, 240, 240));
        bookTable.getTableHeader().setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        // Add column width settings
        bookTable.getColumnModel().getColumn(0).setPreferredWidth(120); // ISBN
        bookTable.getColumnModel().getColumn(1).setPreferredWidth(250); // Title
        bookTable.getColumnModel().getColumn(2).setPreferredWidth(150); // Author
        bookTable.getColumnModel().getColumn(3).setPreferredWidth(120); // Publisher
        bookTable.getColumnModel().getColumn(4).setPreferredWidth(70);  // Year
        bookTable.getColumnModel().getColumn(5).setPreferredWidth(100); // Genre
        bookTable.getColumnModel().getColumn(6).setPreferredWidth(80);  // Price
        bookTable.getColumnModel().getColumn(7).setPreferredWidth(70);  // Quantity

        // Add a scroll pane to the table
        JScrollPane scrollPane = new JScrollPane(bookTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        addButton.setBackground(new Color(92, 184, 92));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        addButton.setFont(new Font("Arial", Font.BOLD, 14));

        editButton.setBackground(new Color(66, 139, 202));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        editButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        editButton.setFont(new Font("Arial", Font.BOLD, 14));

        deleteButton.setBackground(new Color(217, 83, 79));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Add info panel
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(new Color(245, 245, 250));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Book count label
        JLabel bookCountLabel = new JLabel("Total Books: 0");
        bookCountLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Update book count when loading books
        loadBooksWithCallback(() -> {
            int totalBooks = bookTableModel.getRowCount();
            bookCountLabel.setText("Total Books: " + totalBooks);
        });

        infoPanel.add(bookCountLabel, BorderLayout.WEST);

        // Add components to main panel
        add(headerPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Method to load books with a callback
    private void loadBooksWithCallback(Runnable callback) {
        List<Book> books = bookService.getAllBooks();
        updateBookTable(books);
        if (callback != null) {
            callback.run();
        }
    }

    // Add a method to show the add book dialog
    public void showAddBookDialog() {
        AddBookPanel addBookPanel = new AddBookPanel();
        addBookPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JDialog dialog = new JDialog();
        dialog.setTitle("Add New Book");
        dialog.setModal(true);
        dialog.setSize(500, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setContentPane(addBookPanel);

        // Add buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        // Style buttons
        saveButton.setBackground(new Color(92, 184, 92));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);

        cancelButton.setBackground(new Color(230, 230, 230));
        cancelButton.setForeground(new Color(60, 60, 60));
        cancelButton.setFocusPainted(false);

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        saveButton.addActionListener(e -> {
            Book book = addBookPanel.getBook();
            if (book != null) {
                boolean success = bookService.addBook(book);
                if (success) {
                    JOptionPane.showMessageDialog(
                            dialog,
                            "Book added successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    dialog.dispose();
                    loadBooks();
                } else {
                    JOptionPane.showMessageDialog(
                            dialog,
                            "Failed to add book. Please check your input.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        // Add button panel to the dialog
        dialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void registerListeners() {
        // Enable/disable buttons based on table selection
        bookTable.getSelectionModel().addListSelectionListener(e -> {
            boolean rowSelected = bookTable.getSelectedRow() >= 0;
            editButton.setEnabled(rowSelected);
            deleteButton.setEnabled(rowSelected);
        });

        // Search button listener
        searchButton.addActionListener(e -> {
            String searchText = searchField.getText().trim();
            if (!searchText.isEmpty()) {
                List<Book> books = bookService.searchBooksByTitle(searchText);
                updateBookTable(books);
            } else {
                loadBooks();
            }
        });

        // Add button listener
        addButton.addActionListener(e -> {
            AddBookPanel addBookPanel = new AddBookPanel();
            int result = JOptionPane.showConfirmDialog(
                    this,
                    addBookPanel,
                    "Add New Book",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                Book book = addBookPanel.getBook();
                if (book != null) {
                    boolean success = bookService.addBook(book);
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Book added successfully!");
                        loadBooks();
                    } else {
                        JOptionPane.showMessageDialog(
                                this,
                                "Failed to add book. Please check your input.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
        });

        // Edit button listener
        editButton.addActionListener(e -> {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow >= 0) {
                int modelRow = bookTable.convertRowIndexToModel(selectedRow);
                Book selectedBook = bookTableModel.getBookAt(modelRow);

                EditBookPanel editBookPanel = new EditBookPanel(selectedBook);
                int result = JOptionPane.showConfirmDialog(
                        this,
                        editBookPanel,
                        "Edit Book",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

                if (result == JOptionPane.OK_OPTION) {
                    Book updatedBook = editBookPanel.getBook();
                    if (updatedBook != null) {
                        boolean success = bookService.updateBook(updatedBook);
                        if (success) {
                            JOptionPane.showMessageDialog(this, "Book updated successfully!");
                            loadBooks();
                        } else {
                            JOptionPane.showMessageDialog(
                                    this,
                                    "Failed to update book. Please check your input.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }
                    }
                }
            }
        });

        // Delete button listener
        deleteButton.addActionListener(e -> {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow >= 0) {
                int modelRow = bookTable.convertRowIndexToModel(selectedRow);
                Book selectedBook = bookTableModel.getBookAt(modelRow);

                int confirmation = JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete the book: " + selectedBook.getTitle() + "?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (confirmation == JOptionPane.YES_OPTION) {
                    boolean success = bookService.deleteBook(selectedBook.getISBN());
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Book deleted successfully!");
                        loadBooks();
                    } else {
                        JOptionPane.showMessageDialog(
                                this,
                                "Failed to delete book. It may be currently borrowed.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
        });

        // Refresh button listener
        refreshButton.addActionListener(e -> {
            searchField.setText("");
            loadBooks();
        });
    }

    public void loadBooks() {
        List<Book> books = bookService.getAllBooks();
        updateBookTable(books);
    }

    private void updateBookTable(List<Book> books) {
        bookTableModel.setBooks(books);
        bookTableModel.fireTableDataChanged();
    }
}