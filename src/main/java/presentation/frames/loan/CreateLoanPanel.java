package presentation.frames.loan;

import business.BookService;
import business.LoanService;
import business.ReaderService;
import entities.Book;
import entities.Loan;
import entities.LoanDetail;
import entities.Reader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CreateLoanPanel extends JPanel {
    private JTextField readerIDField;
    private JButton searchReaderButton;
    private JLabel readerNameLabel;
    private JLabel readerStatusLabel;

    private JTextField isbnField;
    private JButton addBookButton;
    private JTable bookTable;
    private DefaultTableModel bookTableModel;

    private JLabel loanDateLabel;
    private JLabel returnDateLabel;
    private JButton createLoanButton;
    private JButton cancelButton;

    private Reader selectedReader;
    private List<Book> selectedBooks;

    private ReaderService readerService;
    private BookService bookService;
    private LoanService loanService;

    private DateTimeFormatter dateFormatter;

    public CreateLoanPanel() {
        readerService = new ReaderService();
        bookService = new BookService();
        loanService = new LoanService();
        selectedBooks = new ArrayList<>();
        dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        initComponents();
        layoutComponents();
        registerListeners();
    }

    private void initComponents() {
        readerIDField = new JTextField(15);
        searchReaderButton = new JButton("Tìm");
        readerNameLabel = new JLabel("Độc giả: Chưa chọn");
        readerStatusLabel = new JLabel("Trạng thái: N/A");

        isbnField = new JTextField(15);
        addBookButton = new JButton("Thêm sách");
        addBookButton.setEnabled(false);

        // Book table
        String[] columnNames = {"ISBN", "Tên sách", "Tác giả", "Thể loại", "Xóa"};
        bookTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Chỉ cột "Xóa" có thể sửa
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 4 ? Boolean.class : String.class;
            }
        };
        bookTable = new JTable(bookTableModel);

        // Thiết lập ngày mượn và ngày trả dự kiến
        LocalDate today = LocalDate.now();
        LocalDate returnDate = today.plusDays(7);

        loanDateLabel = new JLabel("Ngày mượn: " + today.format(dateFormatter));
        returnDateLabel = new JLabel("Ngày trả dự kiến: " + returnDate.format(dateFormatter));

        createLoanButton = new JButton("Tạo phiếu mượn");
        createLoanButton.setEnabled(false);
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
                GradientPaint gp = new GradientPaint(0, 0, new Color(205, 92, 92), 0, h, new Color(179, 68, 68));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        headerPanel.setPreferredSize(new Dimension(800, 80));
        headerPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Lập Phiếu Mượn Sách");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBorder(new EmptyBorder(15, 0, 15, 0));

        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Main content panel
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Reader panel
        JPanel readerPanel = new JPanel(new BorderLayout());
        readerPanel.setBackground(Color.WHITE);
        readerPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(205, 92, 92), 1),
                "Thông tin Độc giả",
                0,
                0,
                new Font("Arial", Font.BOLD, 14),
                new Color(205, 92, 92)
        ));

        JPanel readerSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        readerSearchPanel.setBackground(Color.WHITE);
        readerSearchPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel readerIDLabel = new JLabel("Mã độc giả:");
        readerIDLabel.setFont(new Font("Arial", Font.BOLD, 14));

        readerIDField.setPreferredSize(new Dimension(150, 35));
        readerIDField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        readerIDField.setFont(new Font("Arial", Font.PLAIN, 14));

        styleButton(searchReaderButton, new Color(51, 102, 153));

        readerSearchPanel.add(readerIDLabel);
        readerSearchPanel.add(readerIDField);
        readerSearchPanel.add(searchReaderButton);

        JPanel readerInfoPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        readerInfoPanel.setBackground(Color.WHITE);
        readerInfoPanel.setBorder(new EmptyBorder(0, 10, 10, 10));

        readerNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        readerStatusLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        readerInfoPanel.add(readerNameLabel);
        readerInfoPanel.add(readerStatusLabel);

        readerPanel.add(readerSearchPanel, BorderLayout.NORTH);
        readerPanel.add(readerInfoPanel, BorderLayout.CENTER);

        // Book panel
        JPanel bookPanel = new JPanel(new BorderLayout());
        bookPanel.setBackground(Color.WHITE);
        bookPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(46, 139, 87), 1),
                "Danh sách Sách mượn",
                0,
                0,
                new Font("Arial", Font.BOLD, 14),
                new Color(46, 139, 87)
        ));

        JPanel bookSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bookSearchPanel.setBackground(Color.WHITE);
        bookSearchPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel isbnLabel = new JLabel("ISBN:");
        isbnLabel.setFont(new Font("Arial", Font.BOLD, 14));

        isbnField.setPreferredSize(new Dimension(200, 35));
        isbnField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        isbnField.setFont(new Font("Arial", Font.PLAIN, 14));

        styleButton(addBookButton, new Color(46, 139, 87));

        bookSearchPanel.add(isbnLabel);
        bookSearchPanel.add(isbnField);
        bookSearchPanel.add(addBookButton);

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
        bookTable.getColumnModel().getColumn(3).setPreferredWidth(100);  // Genre
        bookTable.getColumnModel().getColumn(4).setPreferredWidth(50);   // Remove

        JScrollPane bookScrollPane = new JScrollPane(bookTable);
        bookScrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        bookPanel.add(bookSearchPanel, BorderLayout.NORTH);
        bookPanel.add(bookScrollPane, BorderLayout.CENTER);

        // Loan info panel
        JPanel loanInfoPanel = new JPanel(new BorderLayout());
        loanInfoPanel.setBackground(Color.WHITE);
        loanInfoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)),
                new EmptyBorder(15, 0, 15, 0)
        ));

        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        datePanel.setBackground(Color.WHITE);

        loanDateLabel.setFont(new Font("Arial", Font.BOLD, 14));
        returnDateLabel.setFont(new Font("Arial", Font.BOLD, 14));

        datePanel.add(loanDateLabel);
        datePanel.add(returnDateLabel);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setBackground(Color.WHITE);

        styleButton(createLoanButton, new Color(205, 92, 92));
        styleButton(cancelButton, new Color(150, 150, 150));

        actionPanel.add(createLoanButton);
        actionPanel.add(cancelButton);

        loanInfoPanel.add(datePanel, BorderLayout.WEST);
        loanInfoPanel.add(actionPanel, BorderLayout.EAST);

        // Thêm các panel vào content panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(readerPanel, BorderLayout.NORTH);

        contentPanel.add(topPanel, BorderLayout.NORTH);
        contentPanel.add(bookPanel, BorderLayout.CENTER);
        contentPanel.add(loanInfoPanel, BorderLayout.SOUTH);

        // Thêm các panel vào panel chính
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    private void registerListeners() {
        // Tìm kiếm độc giả
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
                            "Không tìm thấy độc giả với mã: " + readerID,
                            "Không tìm thấy",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    resetReaderInfo();
                }
            }
        });

        // Thêm sách
        addBookButton.addActionListener(e -> {
            String isbn = isbnField.getText().trim();
            if (!isbn.isEmpty()) {
                Book book = bookService.getBookByISBN(isbn);
                if (book != null) {
                    if (bookService.isBookAvailable(isbn)) {
                        // Kiểm tra sách đã được thêm chưa
                        boolean alreadyAdded = selectedBooks.stream()
                                .anyMatch(b -> b.getISBN().equals(isbn));

                        if (!alreadyAdded) {
                            selectedBooks.add(book);
                            updateBookTable();
                            isbnField.setText("");
                            createLoanButton.setEnabled(!selectedBooks.isEmpty());
                        } else {
                            JOptionPane.showMessageDialog(
                                    this,
                                    "Sách này đã được thêm vào phiếu mượn.",
                                    "Sách đã tồn tại",
                                    JOptionPane.WARNING_MESSAGE
                            );
                        }
                    } else {
                        JOptionPane.showMessageDialog(
                                this,
                                "Sách này không còn sẵn sàng để mượn.",
                                "Sách không khả dụng",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Không tìm thấy sách với ISBN: " + isbn,
                            "Không tìm thấy",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });

        // Xử lý sự kiện khi checkbox trong bảng được chọn
        bookTable.addPropertyChangeListener(e -> {
            if ("tableCellEditor".equals(e.getPropertyName())) {
                if (!bookTable.isEditing()) {
                    // Kiểm tra xem ô nào đã được đánh dấu xóa
                    for (int i = 0; i < bookTableModel.getRowCount(); i++) {
                        Boolean remove = (Boolean) bookTableModel.getValueAt(i, 4);
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

        // Tạo phiếu mượn
        createLoanButton.addActionListener(e -> {
            if (selectedReader != null && !selectedBooks.isEmpty()) {
                // Kiểm tra thẻ độc giả hết hạn chưa
                if (selectedReader.getExpirationDate().isBefore(LocalDate.now())) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Thẻ độc giả đã hết hạn. Vui lòng gia hạn thẻ trước khi mượn sách.",
                            "Thẻ hết hạn",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                // Tạo phiếu mượn
                Loan loan = new Loan(selectedReader.getReaderID(), LocalDate.now());
                loan.setExpectedReturnDate(LocalDate.now().plusDays(7));

                // Thêm chi tiết mượn
                for (Book book : selectedBooks) {
                    LoanDetail detail = new LoanDetail(0, book.getISBN());
                    loan.addLoanDetail(detail);
                }

                // Lưu phiếu mượn
                int loanID = loanService.createLoan(loan);
                if (loanID > 0) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Tạo phiếu mượn thành công!\nMã phiếu mượn: " + loanID +
                                    "\nNgày mượn: " + loan.getLoanDate().format(dateFormatter) +
                                    "\nNgày trả dự kiến: " + loan.getExpectedReturnDate().format(dateFormatter),
                            "Thành công",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    resetForm();
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Không thể tạo phiếu mượn. Vui lòng kiểm tra lại thông tin.",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE
                    );
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
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Thêm hiệu ứng hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
    }

    private void updateReaderInfo() {
        if (selectedReader != null) {
            readerNameLabel.setText("Độc giả: " + selectedReader.getFullName());

            // Kiểm tra thẻ hết hạn chưa
            boolean isExpired = selectedReader.getExpirationDate().isBefore(LocalDate.now());
            if (isExpired) {
                readerStatusLabel.setText("Trạng thái: Thẻ hết hạn ngày " +
                        selectedReader.getExpirationDate().format(dateFormatter));
                readerStatusLabel.setForeground(Color.RED);
                addBookButton.setEnabled(false);
            } else {
                readerStatusLabel.setText("Trạng thái: Thẻ còn hạn đến ngày " +
                        selectedReader.getExpirationDate().format(dateFormatter));
                readerStatusLabel.setForeground(new Color(46, 139, 87));
                addBookButton.setEnabled(true);
            }
        }
    }

    private void resetReaderInfo() {
        selectedReader = null;
        readerNameLabel.setText("Độc giả: Chưa chọn");
        readerStatusLabel.setText("Trạng thái: N/A");
        readerStatusLabel.setForeground(Color.BLACK);
        addBookButton.setEnabled(false);
    }

    private void updateBookTable() {
        // Xóa dữ liệu cũ
        bookTableModel.setRowCount(0);

        // Thêm sách vào bảng
        for (Book book : selectedBooks) {
            Object[] row = new Object[5];
            row[0] = book.getISBN();
            row[1] = book.getTitle();
            row[2] = book.getAuthor();
            row[3] = book.getGenre();
            row[4] = false; // Checkbox xóa

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

        // Cập nhật ngày
        LocalDate today = LocalDate.now();
        LocalDate returnDate = today.plusDays(7);

        loanDateLabel.setText("Ngày mượn: " + today.format(dateFormatter));
        returnDateLabel.setText("Ngày trả dự kiến: " + returnDate.format(dateFormatter));
    }
}