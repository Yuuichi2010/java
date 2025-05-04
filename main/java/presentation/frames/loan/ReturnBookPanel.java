package presentation.frames.loan;

import business.LoanService;
import entities.Loan;
import entities.LoanDetail;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ReturnBookPanel extends JPanel {
    private JTextField loanIDField;
    private JButton searchLoanButton;
    private JLabel loanInfoLabel;
    private JLabel readerInfoLabel;
    private JLabel fineLabel;

    private JTable bookTable;
    private DefaultTableModel bookTableModel;

    private JButton returnBooksButton;
    private JButton cancelButton;

    private Loan selectedLoan;
    private LoanService loanService;
    private DateTimeFormatter dateFormatter;

    private double totalFine;

    public ReturnBookPanel() {
        loanService = new LoanService();
        dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        totalFine = 0.0;

        initComponents();
        layoutComponents();
        registerListeners();
    }

    private void initComponents() {
        loanIDField = new JTextField(10);
        searchLoanButton = new JButton("Tìm phiếu mượn");
        loanInfoLabel = new JLabel("Phiếu mượn: Chưa chọn");
        readerInfoLabel = new JLabel("Độc giả: Chưa chọn");
        fineLabel = new JLabel("Tổng tiền phạt: 0 VNĐ");

        // Book table
        String[] columnNames = {"ISBN", "Tên sách", "Tác giả", "Trạng thái", "Trả", "Mất"};
        bookTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4 || column == 5; // Chỉ cột "Trả" và "Mất" có thể sửa
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return (columnIndex == 4 || columnIndex == 5) ? Boolean.class : String.class;
            }
        };
        bookTable = new JTable(bookTableModel);

        returnBooksButton = new JButton("Xác nhận trả sách");
        returnBooksButton.setEnabled(false);
        cancelButton = new JButton("Hủy");
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Header panel
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth(), h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(255, 140, 0), 0, h, new Color(235, 120, 0));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        headerPanel.setPreferredSize(new Dimension(800, 80));
        headerPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Quản lý Trả Sách");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBorder(new EmptyBorder(15, 0, 15, 0));

        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Main content panel
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Loan search panel
        JPanel loanPanel = new JPanel(new BorderLayout());
        loanPanel.setBackground(Color.WHITE);
        loanPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(255, 140, 0), 1),
                "Thông tin Phiếu mượn",
                0,
                0,
                new Font("Arial", Font.BOLD, 14),
                new Color(255, 140, 0)
        ));

        JPanel loanSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        loanSearchPanel.setBackground(Color.WHITE);
        loanSearchPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel loanIDLabel = new JLabel("Mã phiếu mượn:");
        loanIDLabel.setFont(new Font("Arial", Font.BOLD, 14));

        loanIDField.setPreferredSize(new Dimension(150, 35));
        loanIDField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        loanIDField.setFont(new Font("Arial", Font.PLAIN, 14));

        styleButton(searchLoanButton, new Color(51, 102, 153));

        loanSearchPanel.add(loanIDLabel);
        loanSearchPanel.add(loanIDField);
        loanSearchPanel.add(searchLoanButton);

        JPanel loanInfoPanel = new JPanel(new GridLayout(3, 1, 0, 5));
        loanInfoPanel.setBackground(Color.WHITE);
        loanInfoPanel.setBorder(new EmptyBorder(0, 10, 10, 10));

        loanInfoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        readerInfoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        fineLabel.setFont(new Font("Arial", Font.BOLD, 16));
        fineLabel.setForeground(new Color(205, 92, 92));

        loanInfoPanel.add(loanInfoLabel);
        loanInfoPanel.add(readerInfoLabel);
        loanInfoPanel.add(fineLabel);

        loanPanel.add(loanSearchPanel, BorderLayout.NORTH);
        loanPanel.add(loanInfoPanel, BorderLayout.CENTER);

        // Book panel
        JPanel bookPanel = new JPanel(new BorderLayout());
        bookPanel.setBackground(Color.WHITE);
        bookPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(46, 139, 87), 1),
                "Danh sách Sách cần trả",
                0,
                0,
                new Font("Arial", Font.BOLD, 14),
                new Color(46, 139, 87)
        ));

        // Thiết lập style cho bảng
        bookTable.setRowHeight(30);
        bookTable.setIntercellSpacing(new Dimension(10, 5));
        bookTable.setGridColor(new Color(230, 230, 230));
        bookTable.setSelectionBackground(new Color(224, 236, 254));
        bookTable.setSelectionForeground(Color.BLACK);
        bookTable.setFont(new Font("Arial", Font.PLAIN, 14));
        bookTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        bookTable.getTableHeader().setForeground(new Color(51, 51, 51));
        bookTable.getTableHeader().setBackground(new Color(240, 240, 240));

        // Thiết lập kích thước cột
        bookTable.getColumnModel().getColumn(0).setPreferredWidth(120);  // ISBN
        bookTable.getColumnModel().getColumn(1).setPreferredWidth(250);  // Title
        bookTable.getColumnModel().getColumn(2).setPreferredWidth(150);  // Author
        bookTable.getColumnModel().getColumn(3).setPreferredWidth(100);  // Status
        bookTable.getColumnModel().getColumn(4).setPreferredWidth(50);   // Return
        bookTable.getColumnModel().getColumn(5).setPreferredWidth(50);   // Lost

        JScrollPane bookScrollPane = new JScrollPane(bookTable);
        bookScrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        bookPanel.add(bookScrollPane, BorderLayout.CENTER);

        // Action panel
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setBackground(Color.WHITE);
        actionPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)),
                new EmptyBorder(15, 0, 0, 0)
        ));

        styleButton(returnBooksButton, new Color(46, 139, 87));
        styleButton(cancelButton, new Color(150, 150, 150));

        actionPanel.add(returnBooksButton);
        actionPanel.add(cancelButton);

        // Thêm các panel vào content panel
        contentPanel.add(loanPanel, BorderLayout.NORTH);
        contentPanel.add(bookPanel, BorderLayout.CENTER);
        contentPanel.add(actionPanel, BorderLayout.SOUTH);

        // Thêm các panel vào panel chính
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    private void registerListeners() {
        // Tìm kiếm phiếu mượn
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
                            "Không tìm thấy phiếu mượn với mã: " + loanID,
                            "Không tìm thấy",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    resetLoanInfo();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Mã phiếu mượn không hợp lệ. Vui lòng nhập số.",
                        "Lỗi nhập liệu",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // Xử lý checkbox trả/mất
        bookTable.addPropertyChangeListener(e -> {
            if ("tableCellEditor".equals(e.getPropertyName())) {
                if (!bookTable.isEditing()) {
                    int editingRow = bookTable.getEditingRow();
                    int editingColumn = bookTable.getEditingColumn();

                    // Nếu ô vừa chỉnh sửa là checkbox
                    if (editingRow != -1 && (editingColumn == 4 || editingColumn == 5)) {
                        Boolean value = (Boolean) bookTableModel.getValueAt(editingRow, editingColumn);

                        // Nếu checkbox đã được chọn
                        if (value != null && value) {
                            // Bỏ chọn checkbox khác trong cùng dòng
                            int otherColumn = (editingColumn == 4) ? 5 : 4;
                            bookTableModel.setValueAt(false, editingRow, otherColumn);

                            // Cập nhật tổng tiền phạt
                            calculateTotalFine();
                        }
                    }
                }
            }
        });

        // Xác nhận trả sách
        returnBooksButton.addActionListener(e -> {
            if (selectedLoan != null) {
                List<String> returnedISBNs = new ArrayList<>();
                List<String> lostISBNs = new ArrayList<>();

                // Xử lý danh sách sách trả và mất
                for (int i = 0; i < bookTableModel.getRowCount(); i++) {
                    String isbn = (String) bookTableModel.getValueAt(i, 0);
                    Boolean returned = (Boolean) bookTableModel.getValueAt(i, 4);
                    Boolean lost = (Boolean) bookTableModel.getValueAt(i, 5);

                    // Kiểm tra trạng thái sách
                    String status = (String) bookTableModel.getValueAt(i, 3);
                    if (!"Borrowed".equals(status) && !"Đang mượn".equals(status)) {
                        continue;
                    }

                    if (returned != null && returned) {
                        returnedISBNs.add(isbn);
                    } else if (lost != null && lost) {
                        lostISBNs.add(isbn);
                    }
                }

                // Nếu không có sách nào được chọn
                if (returnedISBNs.isEmpty() && lostISBNs.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Vui lòng chọn sách cần trả hoặc báo mất.",
                            "Chưa chọn sách",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                // Xác nhận trước khi trả
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Xác nhận trả " + returnedISBNs.size() + " sách và báo mất "
                                + lostISBNs.size() + " sách?\n\n"
                                + "Tổng tiền phạt: " + formatCurrency(totalFine),
                        "Xác nhận trả sách",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    // Cập nhật phiếu mượn
                    boolean success = loanService.returnLoan(
                            selectedLoan.getLoanID(),
                            returnedISBNs,
                            lostISBNs
                    );

                    if (success) {
                        String message = "Trả sách thành công!\n\n";
                        if (totalFine > 0) {
                            message += "Tổng tiền phạt: " + formatCurrency(totalFine) + "\n";
                        }

                        JOptionPane.showMessageDialog(
                                this,
                                message,
                                "Thành công",
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        // Làm mới thông tin phiếu mượn
                        selectedLoan = loanService.getLoanByID(selectedLoan.getLoanID());
                        updateLoanInfo();
                        updateBookTable();
                    } else {
                        JOptionPane.showMessageDialog(
                                this,
                                "Không thể xử lý trả sách. Vui lòng thử lại sau.",
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
        });

        // Hủy
        cancelButton.addActionListener(e -> {
            resetForm();
        });
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.BLACK);  // Chỉnh màu chữ thành đen
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color.darker(), 1),  // Đổi viền tối hơn
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Thêm hiệu ứng hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter()); // Nút sáng lên khi hover
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(color.darker(), 2),  // Đổi viền sáng hơn khi hover
                        BorderFactory.createEmptyBorder(8, 15, 8, 15)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);  // Trở lại màu nền ban đầu khi rời khỏi
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(color.darker(), 1),
                        BorderFactory.createEmptyBorder(8, 15, 8, 15)
                ));
            }
        });
    }

    private void updateLoanInfo() {
        if (selectedLoan != null) {
            // Hiển thị thông tin phiếu mượn
            String status = selectedLoan.isReturned() ? "Đã trả" : "Đang mượn";
            String loanDate = selectedLoan.getLoanDate().format(dateFormatter);
            String expectedReturnDate = selectedLoan.getExpectedReturnDate().format(dateFormatter);
            String actualReturnDate = selectedLoan.getActualReturnDate() != null ?
                    selectedLoan.getActualReturnDate().format(dateFormatter) : "Chưa trả";

            loanInfoLabel.setText("<html>Phiếu mượn #" + selectedLoan.getLoanID() +
                    " | Trạng thái: " + status +
                    " | Ngày mượn: " + loanDate +
                    " | Hạn trả: " + expectedReturnDate +
                    " | Ngày trả: " + actualReturnDate + "</html>");

            // Hiển thị thông tin độc giả
            if (selectedLoan.getReader() != null) {
                readerInfoLabel.setText("Độc giả: " + selectedLoan.getReader().getFullName() +
                        " (Mã: " + selectedLoan.getReader().getReaderID() + ")");
            } else {
                readerInfoLabel.setText("Độc giả: Không có thông tin");
            }

            // Cập nhật tiền phạt
            calculateTotalFine();
        }
    }

    private void resetLoanInfo() {
        selectedLoan = null;
        loanInfoLabel.setText("Phiếu mượn: Chưa chọn");
        readerInfoLabel.setText("Độc giả: Chưa chọn");
        fineLabel.setText("Tổng tiền phạt: 0 VNĐ");
        bookTableModel.setRowCount(0);
        returnBooksButton.setEnabled(false);
        totalFine = 0.0;
    }

    private void updateBookTable() {
        // Xóa dữ liệu cũ
        bookTableModel.setRowCount(0);

        if (selectedLoan != null && selectedLoan.getLoanDetails() != null) {
            for (LoanDetail detail : selectedLoan.getLoanDetails()) {
                Object[] row = new Object[6];

                row[0] = detail.getISBN();

                // Thông tin sách
                if (detail.getBook() != null) {
                    row[1] = detail.getBook().getTitle();
                    row[2] = detail.getBook().getAuthor();
                } else {
                    row[1] = "Không có thông tin";
                    row[2] = "Không có thông tin";
                }

                // Trạng thái
                row[3] = detail.getStatus();

                // Mặc định checkbox trả và mất là false
                row[4] = false;
                row[5] = false;

                bookTableModel.addRow(row);

                // Vô hiệu hóa checkbox nếu sách đã được trả hoặc báo mất
                int lastRow = bookTableModel.getRowCount() - 1;
                if (!"Borrowed".equals(detail.getStatus()) && !"Đang mượn".equals(detail.getStatus())) {
                    // Đánh dấu theo trạng thái hiện tại
                    if ("Returned".equals(detail.getStatus()) || "Đã trả".equals(detail.getStatus())) {
                        bookTableModel.setValueAt(true, lastRow, 4);
                    } else if ("Lost".equals(detail.getStatus()) || "Mất".equals(detail.getStatus())) {
                        bookTableModel.setValueAt(true, lastRow, 5);
                    }

                    // Vô hiệu hóa chỉnh sửa
                    DefaultCellEditor editor4 = (DefaultCellEditor) bookTable.getColumnModel().getColumn(4).getCellEditor();
                    DefaultCellEditor editor5 = (DefaultCellEditor) bookTable.getColumnModel().getColumn(5).getCellEditor();

                    editor4.getComponent().setEnabled(false);
                    editor5.getComponent().setEnabled(false);
                }
            }
        }
    }

    private void calculateTotalFine() {
        if (selectedLoan == null) {
            totalFine = 0.0;
            fineLabel.setText("Tổng tiền phạt: 0 VNĐ");
            return;
        }

        totalFine = 0.0;

        // Tính phí quá hạn
        if (selectedLoan.isOverdue()) {
            long daysLate;

            if (selectedLoan.isReturned()) {
                // Nếu đã trả, tính số ngày trễ từ ngày dự kiến đến ngày thực tế
                daysLate = ChronoUnit.DAYS.between(
                        selectedLoan.getExpectedReturnDate(),
                        selectedLoan.getActualReturnDate()
                );
            } else {
                // Nếu chưa trả, tính số ngày trễ từ ngày dự kiến đến hiện tại
                daysLate = ChronoUnit.DAYS.between(
                        selectedLoan.getExpectedReturnDate(),
                        LocalDate.now()
                );
            }

            // Chỉ tính tiền phạt nếu trễ hạn
            if (daysLate > 0) {
                totalFine += daysLate * 5000.0; // 5,000 VNĐ/ngày
            }
        }

        // Tính phí sách bị mất
        for (int i = 0; i < bookTableModel.getRowCount(); i++) {
            Boolean lost = (Boolean) bookTableModel.getValueAt(i, 5);

            // Nếu sách được đánh dấu là mất
            if (lost != null && lost) {
                String isbn = (String) bookTableModel.getValueAt(i, 0);

                // Tìm thông tin chi tiết sách
                for (LoanDetail detail : selectedLoan.getLoanDetails()) {
                    if (detail.getISBN().equals(isbn) && detail.getBook() != null) {
                        // Phạt mất sách = 200% giá sách
                        totalFine += detail.getBook().getPrice() * 2.0;
                        break;
                    }
                }
            }
        }

        // Cập nhật nhãn
        fineLabel.setText("Tổng tiền phạt: " + formatCurrency(totalFine));
    }

    private String formatCurrency(double amount) {
        // Định dạng tiền tệ Việt Nam
        return String.format("%,.0f VNĐ", amount);
    }

    private void resetForm() {
        loanIDField.setText("");
        resetLoanInfo();
    }
}