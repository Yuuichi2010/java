import java.awt.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class BorrowReturnScreen extends JFrame {
    private DefaultTableModel tableModel;
    private JTable transactionTable;
    private TableRowSorter<DefaultTableModel> sorter;

    public BorrowReturnScreen() {
        super("Borrow / Return Management");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel chính
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JLabel titleLabel = new JLabel("Borrow / Return Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(50, 50, 50));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel nhập thông tin giao dịch
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        inputPanel.setOpaque(false);
        
        JLabel readerIdLabel = new JLabel("Reader ID:");
        readerIdLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JTextField readerIdField = new JTextField(8);
        readerIdField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JLabel borrowDateLabel = new JLabel("Borrow Date:");
        borrowDateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JTextField borrowDateField = new JTextField(8);
        borrowDateField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JLabel expectedReturnLabel = new JLabel("Expected Return:");
        expectedReturnLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JTextField expectedReturnField = new JTextField(8);
        expectedReturnField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JButton btnProcess = new JButton("Process Transaction");
        styleButton(btnProcess);

        inputPanel.add(readerIdLabel);
        inputPanel.add(readerIdField);
        inputPanel.add(borrowDateLabel);
        inputPanel.add(borrowDateField);
        inputPanel.add(expectedReturnLabel);
        inputPanel.add(expectedReturnField);
        inputPanel.add(btnProcess);
        mainPanel.add(inputPanel, BorderLayout.PAGE_START);

        // Khởi tạo bảng dữ liệu giao dịch
        String[] columns = {"Transaction ID", "Reader ID", "Book ISBN", "Borrow Date", "Expected Return", "Actual Return", "Fine"};
        tableModel = new DefaultTableModel(columns, 0);
        transactionTable = new JTable(tableModel);
        transactionTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        transactionTable.setRowHeight(25);

        // Thêm dữ liệu mẫu vào bảng
        Object[][] sampleData = {
            {"T001", "R001", "978-3-16-148410-0", "01/01/2023", "08/01/2023", "08/01/2023", "0"},
            {"T002", "R002", "978-0-13-149505-0", "02/01/2023", "09/01/2023", "09/01/2023", "0"},
            {"T003", "R003", "978-1-4028-9462-6", "03/01/2023", "10/01/2023", "10/01/2023", "0"},
            {"T004", "R004", "978-0-262-13472-9", "04/01/2023", "11/01/2023", "11/01/2023", "0"},
            {"T005", "R005", "978-0-596-52068-7", "05/01/2023", "12/01/2023", "12/01/2023", "0"},
            {"T006", "R006", "9783161484100", "06/01/2023", "13/01/2023", "13/01/2023", "0"}
        };
        for (Object[] row : sampleData) {
            tableModel.addRow(row);
        }
        
        // Thêm hàm lọc vào bảng sử dụng TableRowSorter
        sorter = new TableRowSorter<>(tableModel);
        transactionTable.setRowSorter(sorter);

        // Tạo panel lọc dữ liệu
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filterPanel.setOpaque(false);
        JLabel filterLabel = new JLabel("Filter:");
        filterLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JTextField filterField = new JTextField(20);
        filterField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        filterPanel.add(filterLabel);
        filterPanel.add(filterField);

        // Sử dụng DocumentListener để cập nhật bộ lọc khi nội dung thay đổi
        filterField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { applyFilter(); }
            public void removeUpdate(DocumentEvent e) { applyFilter(); }
            public void changedUpdate(DocumentEvent e) { applyFilter(); }
            
            private void applyFilter() {
                String text = filterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    // Lọc tất cả các cột, không phân biệt chữ hoa chữ thường
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(transactionTable);

        // Tạo một panel để chứa bảng và filter
        JPanel tablePanel = new JPanel(new BorderLayout(10, 10));
        tablePanel.setOpaque(false);
        tablePanel.add(filterPanel, BorderLayout.NORTH);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        // Panel chứa các nút thao tác bổ sung
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        bottomPanel.setOpaque(false);
        JButton btnCalculateFine = new JButton("Calculate Fine");
        styleButton(btnCalculateFine);
        bottomPanel.add(btnCalculateFine);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Xử lý sự kiện (demo)
        btnProcess.addActionListener(e -> {
            String readerId = readerIdField.getText().trim();
            String borrowDate = borrowDateField.getText().trim();
            String expectedReturn = expectedReturnField.getText().trim();
            JOptionPane.showMessageDialog(this, "Processing transaction for Reader: " + readerId);
            // Thêm dòng dữ liệu vào bảng sau khi xử lý giao dịch
            Object[] newRow = {"T007", readerId, "978-3-16-148410-0", borrowDate, expectedReturn, "", "0"};
            tableModel.addRow(newRow);
        });
        
        btnCalculateFine.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Calculate fine functionality not implemented.");
        });

        add(mainPanel);
    }
    
    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(new Color(60, 149, 246));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new BorrowReturnScreen().setVisible(true));
    }
}
