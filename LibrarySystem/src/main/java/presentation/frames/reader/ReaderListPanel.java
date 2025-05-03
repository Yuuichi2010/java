package presentation.frames.reader;

import business.ReaderService;
import entities.Reader;
import presentation.models.ReaderTableModel;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ReaderListPanel extends JPanel {
    private JTable readerTable;
    private ReaderTableModel readerTableModel;
    private ReaderService readerService;
    private JTextField searchField;
    private JComboBox<String> searchTypeComboBox;
    private JButton searchButton;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton refreshButton;
    private JLabel readerCountLabel;

    public ReaderListPanel() {
        readerService = new ReaderService();
        initComponents();
        layoutComponents();
        registerListeners();
        loadReaders();
    }

    private void initComponents() {
        readerTableModel = new ReaderTableModel();
        readerTable = new JTable(readerTableModel);
        readerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        readerTable.setAutoCreateRowSorter(true);

        // Create search components
        String[] searchTypes = {"Name", "ID Card"};
        searchTypeComboBox = new JComboBox<>(searchTypes);
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        addButton = new JButton("Add Reader");
        editButton = new JButton("Edit Reader");
        deleteButton = new JButton("Delete Reader");
        refreshButton = new JButton("Refresh");
        readerCountLabel = new JLabel("Total Readers: 0");

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

        JLabel titleLabel = new JLabel("Reader Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setOpaque(false);

        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(2, 10, 2, 10)
        ));

        searchTypeComboBox.setPreferredSize(new Dimension(100, 30));

        // Style buttons
        searchButton.setBackground(new Color(230, 230, 230));
        searchButton.setForeground(new Color(60, 60, 60));
        searchButton.setFocusPainted(false);

        refreshButton.setBackground(new Color(230, 230, 230));
        refreshButton.setForeground(new Color(60, 60, 60));
        refreshButton.setFocusPainted(false);

        JLabel searchByLabel = new JLabel("Search by: ");
        searchByLabel.setForeground(Color.WHITE);
        searchPanel.add(searchByLabel);
        searchPanel.add(searchTypeComboBox);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(refreshButton);

        headerPanel.add(searchPanel, BorderLayout.EAST);

        // Table panel with border
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        // Style the table
        readerTable.setRowHeight(30);
        readerTable.setIntercellSpacing(new Dimension(10, 5));
        readerTable.setGridColor(new Color(230, 230, 230));
        readerTable.setSelectionBackground(new Color(224, 236, 254));
        readerTable.setSelectionForeground(Color.BLACK);
        readerTable.setShowVerticalLines(false);
        readerTable.setFont(new Font("Arial", Font.PLAIN, 14));

        // Set header appearance
        readerTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        readerTable.getTableHeader().setForeground(new Color(70, 70, 70));
        readerTable.getTableHeader().setBackground(new Color(240, 240, 240));
        readerTable.getTableHeader().setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        // Add a scroll pane to the table
        JScrollPane scrollPane = new JScrollPane(readerTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Info panel
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(new Color(245, 245, 250));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Reader count label
        readerCountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        infoPanel.add(readerCountLabel, BorderLayout.WEST);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        // Style action buttons
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

        // Add components to main panel
        add(headerPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void registerListeners() {
        // Enable/disable buttons based on table selection
        readerTable.getSelectionModel().addListSelectionListener(e -> {
            boolean rowSelected = readerTable.getSelectedRow() >= 0;
            editButton.setEnabled(rowSelected);
            deleteButton.setEnabled(rowSelected);
        });

        // Search button listener
        searchButton.addActionListener(e -> {
            String searchText = searchField.getText().trim();
            String searchType = (String) searchTypeComboBox.getSelectedItem();

            if (!searchText.isEmpty()) {
                List<Reader> readers;
                if ("Name".equals(searchType)) {
                    readers = readerService.searchReadersByName(searchText);
                } else { // ID Card
                    Reader reader = readerService.getReaderByIdCard(searchText);
                    readers = reader != null ? List.of(reader) : List.of();
                }
                updateReaderTable(readers);
            } else {
                loadReaders();
            }
        });

        // Add button listener
        addButton.addActionListener(e -> {
            showAddReaderDialog();
        });

        // Edit button listener
        editButton.addActionListener(e -> {
            int selectedRow = readerTable.getSelectedRow();
            if (selectedRow >= 0) {
                int modelRow = readerTable.convertRowIndexToModel(selectedRow);
                Reader selectedReader = readerTableModel.getReaderAt(modelRow);

                showEditReaderDialog(selectedReader);
            }
        });

        // Delete button listener
        deleteButton.addActionListener(e -> {
            int selectedRow = readerTable.getSelectedRow();
            if (selectedRow >= 0) {
                int modelRow = readerTable.convertRowIndexToModel(selectedRow);
                Reader selectedReader = readerTableModel.getReaderAt(modelRow);

                int confirmation = JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete the reader: " + selectedReader.getFullName() + "?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (confirmation == JOptionPane.YES_OPTION) {
                    boolean success = readerService.deleteReader(selectedReader.getReaderID());
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Reader deleted successfully!");
                        loadReaders();
                    } else {
                        JOptionPane.showMessageDialog(
                                this,
                                "Failed to delete reader. The reader may have active loans.",
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
            loadReaders();
        });
    }

    public void loadReaders() {
        List<Reader> readers = readerService.getAllReaders();
        updateReaderTable(readers);
        // Update reader count
        readerCountLabel.setText("Total Readers: " + readers.size());
    }

    private void updateReaderTable(List<Reader> readers) {
        readerTableModel.setReaders(readers);
        readerTableModel.fireTableDataChanged();
        // Update reader count
        readerCountLabel.setText("Total Readers: " + readers.size());
    }

    // Method to show dialog for adding a new reader
    public void showAddReaderDialog() {
        AddReaderPanel addReaderPanel = new AddReaderPanel();
        addReaderPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JDialog dialog = new JDialog();
        dialog.setTitle("Add New Reader");
        dialog.setModal(true);
        dialog.setSize(600, 600);
        dialog.setLocationRelativeTo(this);
        dialog.setContentPane(addReaderPanel);

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
            Reader reader = addReaderPanel.getReader();
            if (reader != null) {
                boolean success = readerService.addReader(reader);
                if (success) {
                    JOptionPane.showMessageDialog(
                            dialog,
                            "Reader added successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    dialog.dispose();
                    loadReaders();
                } else {
                    JOptionPane.showMessageDialog(
                            dialog,
                            "Failed to add reader. Please check your input.",
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

    // Method to show dialog for editing a reader
    private void showEditReaderDialog(Reader reader) {
        EditReaderPanel editReaderPanel = new EditReaderPanel(reader);
        editReaderPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JDialog dialog = new JDialog();
        dialog.setTitle("Edit Reader");
        dialog.setModal(true);
        dialog.setSize(600, 600);
        dialog.setLocationRelativeTo(this);
        dialog.setContentPane(editReaderPanel);

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
            Reader updatedReader = editReaderPanel.getReader();
            if (updatedReader != null) {
                boolean success = readerService.updateReader(updatedReader);
                if (success) {
                    JOptionPane.showMessageDialog(
                            dialog,
                            "Reader updated successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    dialog.dispose();
                    loadReaders();
                } else {
                    JOptionPane.showMessageDialog(
                            dialog,
                            "Failed to update reader. Please check your input.",
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
}