package presentation.frames.loan;

import business.LoanService;
import entities.Loan;
import entities.LoanDetail;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class LoanListPanel extends JPanel {
    private LoanService loanService;
    private JTable loanTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> searchTypeCombo;
    private JLabel statusLabel;
    private JLabel totalFineLabel;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public LoanListPanel() {
        loanService = new LoanService();
        initComponents();
        setupLayout();
        loadData();
        registerListeners();
    }

    private void initComponents() {
        // Panel tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchField = new JTextField(20);
        searchTypeCombo = new JComboBox<>(new String[]{"Mã phiếu", "Mã độc giả"});
        JButton searchButton = new JButton("Tìm kiếm");
        JButton refreshButton = new JButton("Làm mới");
        JButton overdueButton = new JButton("Xem phiếu quá hạn");

        searchButton.setBackground(new Color(46, 139, 87));
        searchButton.setForeground(Color.BLACK);
        searchButton.setFont(new Font("Arial", Font.BOLD, 12));

        refreshButton.setBackground(new Color(70, 130, 180));
        refreshButton.setForeground(Color.BLACK);
        refreshButton.setFont(new Font("Arial", Font.BOLD, 12));

        overdueButton.setBackground(new Color(220, 53, 69));
        overdueButton.setForeground(Color.BLACK);
        overdueButton.setFont(new Font("Arial", Font.BOLD, 12));

        searchPanel.add(searchLabel);
        searchPanel.add(searchTypeCombo);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(refreshButton);
        searchPanel.add(overdueButton);

        // Bảng phiếu mượn
        String[] columnNames = {"Mã phiếu", "Mã độc giả", "Ngày mượn", "Ngày trả dự kiến",
                "Ngày trả thực tế", "Số sách", "Tiền phạt", "Trạng thái"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        loanTable = new JTable(tableModel);
        loanTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        loanTable.setRowHeight(25);
        loanTable.setFont(new Font("Arial", Font.PLAIN, 12));

        // Thiết lập độ rộng cột
        loanTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        loanTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        loanTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        loanTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        loanTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        loanTable.getColumnModel().getColumn(5).setPreferredWidth(80);
        loanTable.getColumnModel().getColumn(6).setPreferredWidth(100);
        loanTable.getColumnModel().getColumn(7).setPreferredWidth(100);

        // Thiết lập header
        JTableHeader header = loanTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 12));
        header.setBackground(new Color(240, 240, 240));

        // Bố trí status panel
        statusLabel = new JLabel("Sẵn sàng");
        totalFineLabel = new JLabel("Tổng tiền phạt: 0 VNĐ");
        totalFineLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalFineLabel.setForeground(new Color(220, 53, 69));

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.add(statusLabel);
        statusPanel.add(Box.createHorizontalStrut(50));
        statusPanel.add(totalFineLabel);

        // Tạo scroll pane cho bảng
        JScrollPane scrollPane = new JScrollPane(loanTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách phiếu mượn"));

        // Tạo panel chứa search và main content
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Thêm vào panel chính
        setLayout(new BorderLayout());
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);

        // Đăng ký các sự kiện
        searchButton.addActionListener(e -> searchLoans());
        refreshButton.addActionListener(e -> loadData());
        overdueButton.addActionListener(e -> loadOverdueLoans());
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(headerPanel.getWidth(), 50));

        JLabel titleLabel = new JLabel("DANH SÁCH PHIẾU MƯỢN", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        return headerPanel;
    }

    private void setupLayout() {
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void loadData() {
        try {
            List<Loan> loans = loanService.getAllLoans();
            updateTable(loans);
            statusLabel.setText("Đã tải " + loans.size() + " phiếu mượn");
            calculateTotalFine(loans);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải dữ liệu: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            statusLabel.setText("Lỗi khi tải dữ liệu");
        }
    }

    private void loadOverdueLoans() {
        try {
            List<Loan> loans = loanService.getOverdueLoans();
            updateTable(loans);
            statusLabel.setText("Đã tải " + loans.size() + " phiếu quá hạn");
            calculateTotalFine(loans);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải dữ liệu: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            statusLabel.setText("Lỗi khi tải dữ liệu");
        }
    }

    private void updateTable(List<Loan> loans) {
        tableModel.setRowCount(0);
        for (Loan loan : loans) {
            Object[] row = new Object[]{
                    loan.getLoanID(),
                    loan.getReaderID(),
                    localDateToString(loan.getLoanDate()),
                    localDateToString(loan.getExpectedReturnDate()),
                    loan.getActualReturnDate() != null ? localDateToString(loan.getActualReturnDate()) : "",
                    loan.getLoanDetails().size(),
                    String.format("%.0f VNĐ", loanService.calculateTotalFine(loan.getLoanID())),
                    getStatusText(loan)
            };
            tableModel.addRow(row);
        }
    }

    private String getStatusText(Loan loan) {
        if (loan.isReturned()) {
            return "Đã trả";
        } else if (loan.isOverdue()) {
            return "Quá hạn";
        } else {
            return "Đang mượn";
        }
    }

    private String localDateToString(LocalDate date) {
        if (date == null) {
            return "";
        }
        Date convertedDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return dateFormat.format(convertedDate);
    }

    private void searchLoans() {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            loadData();
            return;
        }

        try {
            String searchType = (String) searchTypeCombo.getSelectedItem();
            List<Loan> loans = null;

            switch (searchType) {
                case "Mã phiếu":
                    try {
                        int loanId = Integer.parseInt(searchText);
                        Loan loan = loanService.getLoanByID(loanId);
                        loans = new java.util.ArrayList<>();
                        if (loan != null) {
                            loans.add(loan);
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this,
                                "Mã phiếu phải là số!",
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    break;
                case "Mã độc giả":
                    loans = loanService.getLoansByReaderID(searchText);
                    break;
            }

            if (loans != null) {
                updateTable(loans);
                statusLabel.setText("Tìm thấy " + loans.size() + " kết quả");
                calculateTotalFine(loans);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tìm kiếm: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            statusLabel.setText("Lỗi khi tìm kiếm");
        }
    }

    private void calculateTotalFine(List<Loan> loans) {
        double totalFine = 0;
        for (Loan loan : loans) {
            totalFine += loanService.calculateTotalFine(loan.getLoanID());
        }
        totalFineLabel.setText(String.format("Tổng tiền phạt: %.0f VNĐ", totalFine));
    }

    private void registerListeners() {
        // Đăng ký sự kiện Enter cho search field
        searchField.addActionListener(e -> searchLoans());

        // Thêm sự kiện double click để xem chi tiết
        loanTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int row = loanTable.getSelectedRow();
                    if (row >= 0) {
                        int loanId = (int) tableModel.getValueAt(row, 0);
                        showLoanDetails(loanId);
                    }
                }
            }
        });
    }

    private void showLoanDetails(int loanId) {
        Loan loan = loanService.getLoanByID(loanId);
        if (loan == null) {
            JOptionPane.showMessageDialog(this,
                    "Không tìm thấy phiếu mượn!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Tạo nội dung hiển thị chi tiết
        StringBuilder details = new StringBuilder();
        details.append("=== CHI TIẾT PHIẾU MƯỢN ===\n\n");
        details.append("Mã phiếu: ").append(loan.getLoanID()).append("\n");
        details.append("Mã độc giả: ").append(loan.getReaderID()).append("\n");
        details.append("Ngày mượn: ").append(localDateToString(loan.getLoanDate())).append("\n");
        details.append("Ngày trả dự kiến: ").append(localDateToString(loan.getExpectedReturnDate())).append("\n");
        details.append("Ngày trả thực tế: ").append(loan.getActualReturnDate() != null ?
                localDateToString(loan.getActualReturnDate()) : "Chưa trả").append("\n");
        details.append("Trạng thái: ").append(getStatusText(loan)).append("\n\n");
        details.append("=== DANH SÁCH SÁCH ===\n");

        for (LoanDetail detail : loan.getLoanDetails()) {
            details.append("- ISBN: ").append(detail.getISBN()).append("\n");
            details.append("  Trạng thái: ").append(getDetailStatus(detail)).append("\n");
            details.append("  Tiền phạt: ").append(String.format("%.0f VNĐ", detail.getFine())).append("\n\n");
        }

        details.append("TỔNG TIỀN PHẠT: ").append(String.format("%.0f VNĐ", loanService.calculateTotalFine(loanId)));

        JTextArea textArea = new JTextArea(details.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        JOptionPane.showMessageDialog(this,
                scrollPane,
                "Chi tiết phiếu mượn #" + loanId,
                JOptionPane.INFORMATION_MESSAGE);
    }

    private String getDetailStatus(LoanDetail detail) {
        if (detail.isReturned()) {
            return "Đã trả";
        } else if (detail.isLost()) {
            return "Mất sách";
        } else {
            return "Chưa trả";
        }
    }
}