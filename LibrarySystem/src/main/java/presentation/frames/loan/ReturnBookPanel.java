package presentation.frames.loan;

import business.LoanService;
import entities.Loan;
import entities.LoanDetail;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.EventObject;

public class ReturnBookPanel extends JPanel {
    private JTextField loanIDField;
    private JButton searchLoanButton;
    private JLabel loanInfoLabel;
    private JLabel readerInfoLabel;

    private JTable bookTable;
    private DefaultTableModel bookTableModel;

    private JButton returnBooksButton;
    private JButton cancelButton;

    private Loan selectedLoan;
    private LoanService loanService;

    public ReturnBookPanel() {
        loanService = new LoanService();

        initComponents();
        layoutComponents();
        registerListeners();
    }

    private void initComponents() {
        loanIDField = new JTextField(10);
        searchLoanButton = new JButton("Search Loan");
        loanInfoLabel = new JLabel("Loan: Not selected");
        readerInfoLabel = new JLabel("Reader: N/A");

        // Book table
        String[] columnNames = {"ISBN", "Title", "Author", "Status", "Return", "Lost"};
        bookTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4 || column == 5; // Only the "Return" and "Lost" columns are editable
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return (columnIndex == 4 || columnIndex == 5) ? Boolean.class : String.class;
            }
        };
        bookTable = new JTable(bookTableModel);
        bookTable.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(new JCheckBox()));
        bookTable.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(new JCheckBox()));

        returnBooksButton = new JButton("Return Books");
        returnBooksButton.setEnabled(false);
        cancelButton = new JButton("Cancel");
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());

        // Loan search panel
        JPanel loanPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        loanPanel.setBorder(BorderFactory.createTitledBorder("Loan Information"));
        loanPanel.add(new JLabel("Loan ID:"));
        loanPanel.add(loanIDField);
        loanPanel.add(searchLoanButton);

        JPanel loanInfoPanel = new JPanel(new GridLayout(2, 1));
        loanInfoPanel.add(loanInfoLabel);
        loanInfoPanel.add(readerInfoLabel);
        loanPanel.add(loanInfoPanel);

        // Book panel
        JPanel bookPanel = new JPanel(new BorderLayout());
        bookPanel.setBorder(BorderFactory.createTitledBorder("Books to Return"));
        bookPanel.add(new JScrollPane(bookTable), BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(returnBooksButton);
        buttonPanel.add(cancelButton);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(loanPanel, BorderLayout.NORTH);
        mainPanel.add(bookPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void registerListeners() {
        // Search loan button
        searchLoanButton.addActionListener(e -> {
            try {
                int loanID = Integer.parseInt(loanIDField.getText().trim());
                selectedLoan = loanService.getLoanByID(loanID);
                if (selectedLoan != null) {
                    updateLoanInfo();
                    updateBookTable();
                    returnBooksButton.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Loan not found. Please check the ID.",
                            "Loan Not Found",
                            JOptionPane.ERROR_MESSAGE
                    );
                    resetLoanInfo();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Invalid loan ID. Please enter a valid number.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // Book table - handle mutual exclusivity between Return and Lost
        bookTable.addPropertyChangeListener(e -> {
            if ("tableCellEditor".equals(e.getPropertyName())) {
                if (!bookTable.isEditing()) {
                    int editingRow = bookTable.getEditingRow();
                    int editingColumn = bookTable.getEditingColumn();

                    // If a checkbox was just edited
                    if (editingRow != -1 && (editingColumn == 4 || editingColumn == 5)) {
                        Boolean value = (Boolean) bookTableModel.getValueAt(editingRow, editingColumn);

                        // If checkbox was checked
                        if (value != null && value) {
                            // Uncheck the other checkbox in the same row
                            int otherColumn = (editingColumn == 4) ? 5 : 4;
                            bookTableModel.setValueAt(false, editingRow, otherColumn);
                        }
                    }
                }
            }
        });

        // Return books button
        returnBooksButton.addActionListener(e -> {
            if (selectedLoan != null) {
                List<String> returnedISBNs = new ArrayList<>();
                List<String> lostISBNs = new ArrayList<>();

                // Collect returned and lost books
                for (int i = 0; i < bookTableModel.getRowCount(); i++) {
                    String isbn = (String) bookTableModel.getValueAt(i, 0);
                    Boolean returned = (Boolean) bookTableModel.getValueAt(i, 4);
                    Boolean lost = (Boolean) bookTableModel.getValueAt(i, 5);

                    // Check if book is already returned
                    String status = (String) bookTableModel.getValueAt(i, 3);
                    if (!"Borrowed".equals(status)) {
                        continue;
                    }

                    if (returned != null && returned) {
                        returnedISBNs.add(isbn);
                    } else if (lost != null && lost) {
                        lostISBNs.add(isbn);
                    }
                }

                // If no books selected, show message
                if (returnedISBNs.isEmpty() && lostISBNs.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Please select books to return or mark as lost.",
                            "No Books Selected",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                // Update loan in database
                boolean success = loanService.returnLoan(selectedLoan.getLoanID(), returnedISBNs, lostISBNs);
                if (success) {
                    // Calculate total fine
                    double totalFine = loanService.calculateTotalFine(selectedLoan.getLoanID());

                    // Show success message with fine information
                    String message = "Books processed successfully!";
                    if (totalFine > 0) {
                        message += "\n\nTotal fine: " + String.format("%,.2f", totalFine) + " VND";
                    }

                    JOptionPane.showMessageDialog(
                            this,
                            message,
                            "Return Processed",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    // Refresh loan information
                    selectedLoan = loanService.getLoanByID(selectedLoan.getLoanID());
                    updateLoanInfo();
                    updateBookTable();
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Failed to process return. Please try again.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        // Cancel button
        cancelButton.addActionListener(e -> resetForm());
    }

    private void updateLoanInfo() {
        if (selectedLoan != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            String loanStatus = selectedLoan.isReturned() ? "Returned" : "Active";
            String loanDate = selectedLoan.getLoanDate().format(formatter);
            String expectedReturnDate = selectedLoan.getExpectedReturnDate().format(formatter);
            String actualReturnDate = selectedLoan.getActualReturnDate() != null ?
                    selectedLoan.getActualReturnDate().format(formatter) : "Not returned";

            loanInfoLabel.setText("<html>Loan: #" + selectedLoan.getLoanID() +
                    " | Status: " + loanStatus +
                    " | Loan Date: " + loanDate +
                    " | Expected Return: " + expectedReturnDate +
                    " | Actual Return: " + actualReturnDate + "</html>");

            if (selectedLoan.getReader() != null) {
                readerInfoLabel.setText("Reader: " + selectedLoan.getReader().getFullName() +
                        " (ID: " + selectedLoan.getReader().getReaderID() + ")");
            } else {
                readerInfoLabel.setText("Reader: Unknown");
            }
        }
    }

    private void resetLoanInfo() {
        selectedLoan = null;
        loanInfoLabel.setText("Loan: Not selected");
        readerInfoLabel.setText("Reader: N/A");
        bookTableModel.setRowCount(0);
        returnBooksButton.setEnabled(false);
    }

    private void updateBookTable() {
        // Clear the table
        bookTableModel.setRowCount(0);

        if (selectedLoan != null && selectedLoan.getLoanDetails() != null) {
            for (LoanDetail detail : selectedLoan.getLoanDetails()) {
                Vector<Object> row = new Vector<>();

                String isbn = detail.getISBN();
                row.add(isbn);

                if (detail.getBook() != null) {
                    row.add(detail.getBook().getTitle());
                    row.add(detail.getBook().getAuthor());
                } else {
                    row.add("Unknown");
                    row.add("Unknown");
                }

                row.add(detail.getStatus());

                // Return and Lost checkboxes - disabled if already returned or lost
                boolean isBorrowed = detail.isBorrowed();
                row.add(false);
                row.add(false);

                bookTableModel.addRow(row);

                // Disable checkboxes for already returned or lost books
                if (!isBorrowed) {
                    int lastRow = bookTableModel.getRowCount() - 1;
                    bookTable.setEnabled(true);
                    bookTable.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(
                            new JCheckBox()) {
                        @Override
                        public boolean isCellEditable(EventObject anEvent) {
                            return isBorrowed;
                        }
                    });
                    bookTable.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(
                            new JCheckBox()) {
                        @Override
                        public boolean isCellEditable(EventObject anEvent) {
                            return isBorrowed;
                        }
                    });
                }
            }
        }
    }

    private void resetForm() {
        loanIDField.setText("");
        resetLoanInfo();
    }
}