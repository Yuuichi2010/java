package presentation.frames.statistics;

import business.BookService;
import business.LoanService;
import business.ReaderService;
import business.StatisticsService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class StatisticsPanel extends JPanel {
    private JTabbedPane tabbedPane;
    private JPanel bookStatsPanel;
    private JPanel readerStatsPanel;

    private JLabel totalBooksLabel;
    private JLabel borrowedBooksLabel;
    private JLabel availableBooksLabel;
    private JLabel totalReadersLabel;
    private JLabel activeLoansLabel;
    private JLabel overdueLoansLabel;

    private JTable genreTable;
    private JTable readerGenderTable;
    private JTable popularBooksTable;
    private JTable activeReadersTable;

    private StatisticsService statisticsService;
    private BookService bookService;
    private ReaderService readerService;
    private LoanService loanService;

    public StatisticsPanel() {
        statisticsService = new StatisticsService();
        bookService = new BookService();
        readerService = new ReaderService();
        loanService = new LoanService();

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(new Color(245, 245, 250));

        // Tạo header với tiêu đề
        JPanel headerPanel = createHeaderPanel("Thống kê Thư viện");

        // Tạo tabbed pane cho các thống kê khác nhau
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        tabbedPane.setBackground(Color.WHITE);
        tabbedPane.setForeground(new Color(51, 51, 51));

        // Tạo các tab thống kê
        bookStatsPanel = createBookStatsPanel();
        readerStatsPanel = createReaderStatsPanel();

        // Thêm các tab vào tabbedPane
        tabbedPane.addTab("Thống kê Sách", new ImageIcon(), bookStatsPanel, "Xem thống kê về sách");
        tabbedPane.addTab("Thống kê Độc giả", new ImageIcon(), readerStatsPanel, "Xem thống kê về độc giả");

        // Thêm các thành phần vào panel chính
        add(headerPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);

        // Tải dữ liệu thống kê
        loadStatistics();
    }

    private JPanel createHeaderPanel(String title) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth(), h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(51, 102, 153), 0, h, new Color(34, 67, 101));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        panel.setPreferredSize(new Dimension(800, 80));
        panel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBorder(new EmptyBorder(15, 0, 15, 0));

        panel.add(titleLabel, BorderLayout.CENTER);

        // Thêm ngày hiện tại bên phải
        JLabel dateLabel = new JLabel("Ngày: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setBorder(new EmptyBorder(0, 0, 0, 20));

        panel.add(dateLabel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createBookStatsPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Panel cards chứa các thống kê số liệu
        JPanel cardsPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        cardsPanel.setBackground(Color.WHITE);

        // Tạo các card hiển thị thông tin
        totalBooksLabel = new JLabel("0", JLabel.CENTER);
        totalBooksLabel.setFont(new Font("Arial", Font.BOLD, 24));
        totalBooksLabel.setForeground(new Color(51, 102, 153));

        borrowedBooksLabel = new JLabel("0", JLabel.CENTER);
        borrowedBooksLabel.setFont(new Font("Arial", Font.BOLD, 24));
        borrowedBooksLabel.setForeground(new Color(205, 92, 92));

        availableBooksLabel = new JLabel("0", JLabel.CENTER);
        availableBooksLabel.setFont(new Font("Arial", Font.BOLD, 24));
        availableBooksLabel.setForeground(new Color(46, 139, 87));

        cardsPanel.add(createStatCard("Tổng số sách", totalBooksLabel, new Color(51, 102, 153)));
        cardsPanel.add(createStatCard("Đang được mượn", borrowedBooksLabel, new Color(205, 92, 92)));
        cardsPanel.add(createStatCard("Có sẵn", availableBooksLabel, new Color(46, 139, 87)));

        // Tạo bảng thống kê sách theo thể loại
        JPanel genreTablePanel = new JPanel(new BorderLayout());
        genreTablePanel.setBackground(Color.WHITE);
        genreTablePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Phân bố sách theo thể loại",
                1,
                0,
                new Font("Arial", Font.BOLD, 14),
                new Color(51, 51, 51)
        ));

        String[] genreColumns = {"Thể loại", "Số lượng", "Tỷ lệ (%)"};
        DefaultTableModel genreModel = new DefaultTableModel(genreColumns, 0);
        genreTable = new JTable(genreModel);
        styleTable(genreTable);

        genreTablePanel.add(new JScrollPane(genreTable), BorderLayout.CENTER);

        // Tạo bảng sách phổ biến nhất
        JPanel popularBooksPanel = new JPanel(new BorderLayout());
        popularBooksPanel.setBackground(Color.WHITE);
        popularBooksPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Sách mượn nhiều nhất",
                1,
                0,
                new Font("Arial", Font.BOLD, 14),
                new Color(51, 51, 51)
        ));

        String[] popularColumns = {"Tên sách", "Tác giả", "Số lần mượn"};
        DefaultTableModel popularModel = new DefaultTableModel(popularColumns, 0);
        popularBooksTable = new JTable(popularModel);
        styleTable(popularBooksTable);

        popularBooksPanel.add(new JScrollPane(popularBooksTable), BorderLayout.CENTER);

        // Thêm các bảng vào panelCon
        JPanel tablesPanel = new JPanel(new GridLayout(2, 1, 0, 15));
        tablesPanel.setBackground(Color.WHITE);
        tablesPanel.add(genreTablePanel);
        tablesPanel.add(popularBooksPanel);

        // Thêm tất cả vào panel chính
        panel.add(cardsPanel, BorderLayout.NORTH);
        panel.add(tablesPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createReaderStatsPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Panel cards chứa các thống kê số liệu
        JPanel cardsPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        cardsPanel.setBackground(Color.WHITE);

        // Tạo các card hiển thị thông tin
        totalReadersLabel = new JLabel("0", JLabel.CENTER);
        totalReadersLabel.setFont(new Font("Arial", Font.BOLD, 24));
        totalReadersLabel.setForeground(new Color(51, 102, 153));

        activeLoansLabel = new JLabel("0", JLabel.CENTER);
        activeLoansLabel.setFont(new Font("Arial", Font.BOLD, 24));
        activeLoansLabel.setForeground(new Color(46, 139, 87));

        overdueLoansLabel = new JLabel("0", JLabel.CENTER);
        overdueLoansLabel.setFont(new Font("Arial", Font.BOLD, 24));
        overdueLoansLabel.setForeground(new Color(205, 92, 92));

        cardsPanel.add(createStatCard("Tổng số độc giả", totalReadersLabel, new Color(51, 102, 153)));
        cardsPanel.add(createStatCard("Phiếu mượn đang hoạt động", activeLoansLabel, new Color(46, 139, 87)));
        cardsPanel.add(createStatCard("Phiếu mượn quá hạn", overdueLoansLabel, new Color(205, 92, 92)));

        // Tạo bảng thống kê độc giả theo giới tính
        JPanel genderTablePanel = new JPanel(new BorderLayout());
        genderTablePanel.setBackground(Color.WHITE);
        genderTablePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Phân bố độc giả theo giới tính",
                1,
                0,
                new Font("Arial", Font.BOLD, 14),
                new Color(51, 51, 51)
        ));

        String[] genderColumns = {"Giới tính", "Số lượng", "Tỷ lệ (%)"};
        DefaultTableModel genderModel = new DefaultTableModel(genderColumns, 0);
        readerGenderTable = new JTable(genderModel);
        styleTable(readerGenderTable);

        genderTablePanel.add(new JScrollPane(readerGenderTable), BorderLayout.CENTER);

        // Tạo bảng độc giả mượn nhiều nhất
        JPanel activeReadersPanel = new JPanel(new BorderLayout());
        activeReadersPanel.setBackground(Color.WHITE);
        activeReadersPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Độc giả mượn sách nhiều nhất",
                1,
                0,
                new Font("Arial", Font.BOLD, 14),
                new Color(51, 51, 51)
        ));

        String[] activeColumns = {"Mã độc giả", "Họ tên", "Số sách đã mượn"};
        DefaultTableModel activeModel = new DefaultTableModel(activeColumns, 0);
        activeReadersTable = new JTable(activeModel);
        styleTable(activeReadersTable);

        activeReadersPanel.add(new JScrollPane(activeReadersTable), BorderLayout.CENTER);

        // Thêm các bảng vào panel con
        JPanel tablesPanel = new JPanel(new GridLayout(2, 1, 0, 15));
        tablesPanel.setBackground(Color.WHITE);
        tablesPanel.add(genderTablePanel);
        tablesPanel.add(activeReadersPanel);

        // Thêm tất cả vào panel chính
        panel.add(cardsPanel, BorderLayout.NORTH);
        panel.add(tablesPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStatCard(String title, JLabel valueLabel, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(color, 2),
                new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(70, 70, 70));

        // Thêm icon nếu cần
        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        iconPanel.setBackground(Color.WHITE);

        JPanel contentPanel = new JPanel(new GridLayout(3, 1));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.add(titleLabel);
        contentPanel.add(valueLabel);
        contentPanel.add(new JLabel(" ", JLabel.CENTER)); // Khoảng trống

        card.add(contentPanel, BorderLayout.CENTER);

        return card;
    }

    private void styleTable(JTable table) {
        // Thiết lập font và màu sắc
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setIntercellSpacing(new Dimension(10, 5));
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(224, 236, 254));
        table.setSelectionForeground(Color.BLACK);
        table.setShowVerticalLines(false);

        // Thiết lập header
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 15));
        header.setForeground(new Color(51, 51, 51));
        header.setBackground(new Color(240, 240, 240));
        header.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        // Căn giữa dữ liệu
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private void loadStatistics() {
        try {
            // Cập nhật số liệu sách
            int totalBooks = bookService.getTotalBooks();
            int borrowedBooks = loanService.getBorrowedBooksCount();
            int availableBooks = totalBooks - borrowedBooks;

            totalBooksLabel.setText(totalBooks + " quyển");
            borrowedBooksLabel.setText(borrowedBooks + " quyển");
            availableBooksLabel.setText(availableBooks + " quyển");

            // Cập nhật số liệu độc giả
            int totalReaders = readerService.getTotalReaders();
            int activeLoans = loanService.getAllLoans().size();
            int overdueLoans = loanService.getOverdueLoans().size();

            totalReadersLabel.setText(totalReaders + " người");
            activeLoansLabel.setText(activeLoans + " phiếu");
            overdueLoansLabel.setText(overdueLoans + " phiếu");

            // Cập nhật bảng thể loại
            DefaultTableModel genreModel = (DefaultTableModel) genreTable.getModel();
            genreModel.setRowCount(0);

            List<Object[]> genreStats = bookService.getBookCountByGenre();
            for (Object[] row : genreStats) {
                String genre = (String) row[0];
                int count = (int) row[1];
                double percentage = (double) count / totalBooks * 100;

                genreModel.addRow(new Object[]{
                        genre,
                        count,
                        String.format("%.2f", percentage)
                });
            }

            // Cập nhật bảng giới tính
            DefaultTableModel genderModel = (DefaultTableModel) readerGenderTable.getModel();
            genderModel.setRowCount(0);

            List<Object[]> genderStats = readerService.getReaderCountByGender();
            for (Object[] row : genderStats) {
                String gender = (String) row[0];
                int count = (int) row[1];
                double percentage = (double) count / totalReaders * 100;

                genderModel.addRow(new Object[]{
                        gender,
                        count,
                        String.format("%.2f", percentage)
                });
            }

            // Cập nhật bảng sách phổ biến
            DefaultTableModel popularModel = (DefaultTableModel) popularBooksTable.getModel();
            popularModel.setRowCount(0);

            Map<String, Integer> popularBooks = statisticsService.getMostPopularBooks(10);
            for (Map.Entry<String, Integer> entry : popularBooks.entrySet()) {
                String bookInfo = entry.getKey();
                int count = entry.getValue();

                // Tách thông tin sách
                String title = bookInfo;
                String author = "";

                // Nếu thông tin sách chứa thông tin tác giả
                int separatorIndex = bookInfo.lastIndexOf(" - ");
                if (separatorIndex > 0) {
                    title = bookInfo.substring(0, separatorIndex);
                    author = bookInfo.substring(separatorIndex + 3);
                }

                popularModel.addRow(new Object[]{
                        title,
                        author,
                        count
                });
            }

            // Cập nhật bảng độc giả tích cực
            DefaultTableModel activeModel = (DefaultTableModel) activeReadersTable.getModel();
            activeModel.setRowCount(0);

            Map<String, Integer> activeReaders = statisticsService.getMostActiveReaders(10);
            for (Map.Entry<String, Integer> entry : activeReaders.entrySet()) {
                String readerInfo = entry.getKey();
                int count = entry.getValue();

                // Tách thông tin độc giả
                String id = "";
                String name = readerInfo;

                // Nếu thông tin độc giả chứa thông tin mã
                int separatorIndex = readerInfo.lastIndexOf(" (");
                if (separatorIndex > 0 && readerInfo.endsWith(")")) {
                    name = readerInfo.substring(0, separatorIndex);
                    id = readerInfo.substring(separatorIndex + 2, readerInfo.length() - 1);
                }

                activeModel.addRow(new Object[]{
                        id,
                        name,
                        count
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Không thể tải dữ liệu thống kê: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}