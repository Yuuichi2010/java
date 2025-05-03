package presentation.frames.loan;

import business.BookService;
import business.LoanService;
import business.ReaderService;
import entities.Book;
import entities.Loan;
import entities.LoanDetail;
import entities.Reader;

import javax.swing.*;
        import javax.swing.table.DefaultTableModel;
import java.awt.*;
        import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class CreateLoanPanel extends JPanel {
    private JTextField readerIDField;
    private JButton searchReaderButton;
    private JLabel readerNameLabel;
    private JLabel readerStatusLabel;

    private JTextField isbnField;
    private JButton addBookButton;
    private JTable bookTable;
    private DefaultTableModel bookTableModel;

    private JButton createLoanButton;
    private JButton cancelButton;

    private Reader selectedReader;
    private List<Book> selectedBooks;

    private ReaderService readerService;
    private BookService bookService;
    private LoanService loanService;

    public CreateLoanPanel() {
        readerService = new ReaderService();
        bookService = new BookService();
        loanService = new LoanService();
        selectedBooks = new ArrayList<>();

        initComponents();
        layoutComponents();
        registerListeners();
    }

    private void initComponents() {
        readerIDField = new JTextField(15);
        searchReaderButton = new JButton("Search Reader");
        readerNameLabel = new JLabel("Reader: Not selected");
        readerStatusLabel = new JLabel("Status: N/A");

        isbnField = new JTextField(15);
        addBookButton = new JButton("Add Book");
        addBookButton.setEnabled(false);

        // Book table
        String[] columnNames = {"ISBN", "Title", "Author", "Remove"};
        bookTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Only the "Remove" column is editable
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 3 ? Boolean.class : String.class;
            }
        };
        bookTable = new JTable(bookTableModel);
        bookTable.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(new JCheckBox()));

        createLoanButton = new JButton("Create Loan");
        createLoanButton.setEnabled(false);
        cancelButton = new JButton("Cancel");
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());

        // Reader panel
        JPanel readerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        readerPanel.setBorder(BorderFactory.createTitledBorder("Reader Information"));
        readerPanel.add(new JLabel("Reader ID:"));
        readerPanel.add(readerIDField);
        readerPanel.add(searchReaderButton);

        JPanel readerInfoPanel = new JPanel(new GridLayout(2, 1));
        readerInfoPanel.add(readerNameLabel);
        readerInfoPanel.add(readerStatusLabel);
        readerPanel.add(readerInfoPanel);

        // Book panel
        JPanel bookPanel = new JPanel(new BorderLayout());
        bookPanel.setBorder(BorderFactory.createTitledBorder("Book Selection"));

        JPanel bookSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bookSearchPanel.add(new JLabel("ISBN:"));
        bookSearchPanel.add(isbnField);
        bookSearchPanel.add(addBookButton);

        bookPanel.add(bookSearchPanel, BorderLayout.NORTH);
        bookPanel.add(new JScrollPane(bookTable), BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(createLoanButton);
        buttonPanel.add(cancelButton);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(readerPanel, BorderLayout.NORTH);
        mainPanel.add(bookPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void registerListeners() {
        // Search reader button
        searchReaderButton.addActionListener(e -> {
            String readerID = readerIDField.getText().trim();
            if (!readerID.isEmpty()) {
                selectedReader = readerService.getReaderByID(readerID);
                if (selectedReader != null) {
                    updateReaderInfo();
                    addBookButton.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Reader not found. Please check the ID.",
                            "Reader Not Found",
                            JOptionPane.ERROR_MESSAGE
                    );
                    resetReaderInfo();
                }
            }
        });

        // Add book button
        addBookButton.addActionListener(e -> {
            String isbn = isbnField.getText().trim();
            if (!isbn.isEmpty()) {
                Book book = bookService.getBookByISBN(isbn);
                if (book != null) {
                    if (bookService.isBookAvailable(isbn)) {
                        // Check if book is already in the list
                        boolean alreadyAdded = false;
                        for (Book b : selectedBooks) {
                            if (b.getISBN().equals(isbn)) {
                                alreadyAdded = true;
                                break;
                            }
                        }

                        if (!alreadyAdded) {
                            selectedBooks.add(book);
                            updateBookTable();
                            isbnField.setText("");
                            createLoanButton.setEnabled(!selectedBooks.isEmpty());
                        } else {
                            JOptionPane.showMessageDialog(
                                    this,
                                    "This book is already added to the loan.",
                                    "Duplicate Book",
                                    JOptionPane.WARNING_MESSAGE
                            );
                        }
                    } else {
                        JOptionPane.showMessageDialog(
                                this,
                                "This book is not available for loan.",
                                "Book Not Available",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Book not found. Please check the ISBN.",
                            "Book Not Found",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        // Book table - remove button
        bookTable.addPropertyChangeListener(e -> {
            if ("tableCellEditor".equals(e.getPropertyName())) {
                if (!bookTable.isEditing()) {
                    // Check if a "Remove" checkbox was checked
                    for (int i = 0; i < bookTableModel.getRowCount(); i++) {
                        Boolean remove = (Boolean) bookTableModel.getValueAt(i, 3);
                        if (remove != null && remove) {
                            selectedBooks.remove(i);
                            updateBookTable();
                            createLoanButton.setEnabled(!selectedBooks.isEmpty());
                            break;
                        }
                    }
                }
            }
        });

        // Create loan button
        createLoanButton.addActionListener(e -> {
            if (selectedReader != null && !selectedBooks.isEmpty()) {
                // Check if reader's card is expired
                if (selectedReader.getExpirationDate().isBefore(LocalDate.now())) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Reader's card has expired. Cannot create loan.",
                            "Expired Card",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                // Create loan
                Loan loan = new Loan(selectedReader.getReaderID(), LocalDate.now());

                // Add loan details
                for (Book book : selectedBooks) {
                    LoanDetail detail = new LoanDetail(0, book.getISBN());
                    loan.addLoanDetail(detail);
                }

                // Save loan to database
                int loanID = loanService.createLoan(loan);
                if (loanID > 0) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Loan created successfully! Loan ID: " + loanID,
                            "Loan Created",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    resetForm();
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Failed to create loan. Please try again.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        // Cancel button
        cancelButton.addActionListener(e -> resetForm());
    }

    private void updateReaderInfo() {
        if (selectedReader != null) {
            readerNameLabel.setText("Reader: " + selectedReader.getFullName());

            // Check if reader's card is expired
            boolean isExpired = selectedReader.getExpirationDate().isBefore(LocalDate.now());
            if (isExpired) {
                readerStatusLabel.setText("Status: Card Expired on " +
                        selectedReader.getExpirationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                readerStatusLabel.setForeground(Color.RED);
                addBookButton.setEnabled(false);
            } else {
                readerStatusLabel.setText("Status: Active until " +
                        selectedReader.getExpirationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                readerStatusLabel.setForeground(Color.BLACK);
                addBookButton.setEnabled(true);
            }
        }
    }

    private void resetReaderInfo() {
        selectedReader = null;
        readerNameLabel.setText("Reader: Not selected");
        readerStatusLabel.setText("Status: N/A");
        readerStatusLabel.setForeground(Color.BLACK);
        addBookButton.setEnabled(false);
    }

    private void updateBookTable() {
        // Clear the table
        bookTableModel.setRowCount(0);

        // Add books to the table
        for (Book book : selectedBooks) {
            Vector<Object> row = new Vector<>();
            row.add(book.getISBN());
            row.add(book.getTitle());
            row.add(book.getAuthor());
            row.add(false);
            bookTableModel.addRow(row);
        }
    }

    private void resetForm() {
        readerIDField.setText("");
        isbnField.setText("");
        resetReaderInfo();
        selectedBooks.clear();
        updateBookTable();
        createLoanButton.setEnabled(false);
    }
}