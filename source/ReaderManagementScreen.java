import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ReaderManagementScreen extends JFrame {
    private DefaultTableModel tableModel;
    private JTable readerTable;

    public ReaderManagementScreen() {
        super("Reader Management");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel chính
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JLabel titleLabel = new JLabel("Reader Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(50, 50, 50));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setOpaque(false);
        JLabel searchLabel = new JLabel("Search Reader:");
        searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JTextField searchField = new JTextField(20);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JButton searchButton = new JButton("Search");
        styleButton(searchButton);
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        mainPanel.add(searchPanel, BorderLayout.PAGE_START);

        // Bảng hiển thị độc giả
        String[] columns = {"Reader ID", "Full Name", "ID Card", "DOB", "Gender", "Email"};
        tableModel = new DefaultTableModel(columns, 0);
        readerTable = new JTable(tableModel);
        readerTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        readerTable.setRowHeight(25);
        JScrollPane tableScrollPane = new JScrollPane(readerTable);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Panel chứa các nút CRUD
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        bottomPanel.setOpaque(false);
        JButton btnAdd = new JButton("Add");
        JButton btnEdit = new JButton("Edit");
        JButton btnDelete = new JButton("Delete");
        styleButton(btnAdd); 
        styleButton(btnEdit); 
        styleButton(btnDelete);
        bottomPanel.add(btnAdd);
        bottomPanel.add(btnEdit);
        bottomPanel.add(btnDelete);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Xử lý sự kiện (demo)
        searchButton.addActionListener(e -> {
            String query = searchField.getText().trim();
            JOptionPane.showMessageDialog(this, "Searching for: " + query);
        });
        btnAdd.addActionListener(e -> JOptionPane.showMessageDialog(this, "Add reader functionality not implemented."));
        btnEdit.addActionListener(e -> JOptionPane.showMessageDialog(this, "Edit reader functionality not implemented."));
        btnDelete.addActionListener(e -> JOptionPane.showMessageDialog(this, "Delete reader functionality not implemented."));

        // Dữ liệu mẫu
        Object[] sampleRow = {"R001", "Nguyen Van A", "123456789", "01/01/1990", "Male", "a@example.com"};
        tableModel.addRow(sampleRow);

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
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch(Exception ex){ ex.printStackTrace(); }
        SwingUtilities.invokeLater(() -> new ReaderManagementScreen().setVisible(true));
    }
}
