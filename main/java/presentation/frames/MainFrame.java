package presentation.frames;

import business.BookService;
import business.LoanService;
import business.ReaderService;
import business.StatisticsService;
import presentation.frames.book.BookListPanel;
import presentation.frames.loan.CreateLoanPanel;
import presentation.frames.loan.LoanListPanel;
import presentation.frames.loan.ReturnBookPanel;
import presentation.frames.reader.ReaderListPanel;
import presentation.frames.statistics.StatisticsPanel;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {
    private JMenuBar menuBar;
    private JPanel contentPanel;
    private CardLayout cardLayout;

    private JPanel dashboardPanel;
    private JButton readersButton, booksButton, loansButton, returnsButton, loanListButton, statisticsButton;

    private String username;

    // Service objects
    private BookService bookService;
    private ReaderService readerService;
    private LoanService loanService;
    private StatisticsService statisticsService;

    public MainFrame(String username) {
        this.username = username;

        // Initialize services
        bookService = new BookService();
        readerService = new ReaderService();
        loanService = new LoanService();
        statisticsService = new StatisticsService();

        // Thiết lập giao diện
        setTitle("Hệ thống Quản lý Thư viện - " + username);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Khởi tạo các thành phần giao diện - đúng thứ tự
        initButtons();
        initComponents();
        createMenus();
        setupLayout();
        registerListeners();

        // Hiển thị dashboard lúc khởi động
        cardLayout.show(contentPanel, "dashboard");
    }

    // Tách phần khởi tạo nút để đảm bảo nút đã được tạo trước khi sử dụng
    private void initButtons() {
        // Khởi tạo các nút điều hướng
        readersButton = new JButton("Mở Quản lý Độc giả");
        setupButton(readersButton, new Color(70, 130, 180));

        booksButton = new JButton("Mở Quản lý Sách");
        setupButton(booksButton, new Color(46, 139, 87));

        loansButton = new JButton("Mở Mượn Sách");
        setupButton(loansButton, new Color(205, 92, 92));

        returnsButton = new JButton("Mở Trả Sách");
        setupButton(returnsButton, new Color(255, 140, 0));

        loanListButton = new JButton("Mở Danh sách mượn");
        setupButton(loanListButton, new Color(60, 179, 113));

        statisticsButton = new JButton("Mở Thống kê");
        setupButton(statisticsButton, new Color(123, 104, 238));
    }

    private void setupButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.BLACK); // Đổi màu chữ thành đen
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color.darker(), 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        addButtonHoverEffect(button, color);
    }

    private void initComponents() {
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        // Tạo dashboard panel sau khi đã khởi tạo các nút
        dashboardPanel = createDashboardPanel();

        // Thêm các panel vào cardLayout
        contentPanel.add(dashboardPanel, "dashboard");
        contentPanel.add(new ReaderListPanel(), "viewReaders");
        contentPanel.add(new BookListPanel(), "viewBooks");
        contentPanel.add(new CreateLoanPanel(), "createLoan");
        contentPanel.add(new ReturnBookPanel(), "returnBooks");
        contentPanel.add(new LoanListPanel(), "loanList");
        contentPanel.add(new StatisticsPanel(), "statistics");
    }

    private void addButtonHoverEffect(JButton button, Color baseColor) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor);
            }
        });
    }

    private void createMenus() {
        menuBar = new JMenuBar();

        // Tạo button Dashboard để quay về trang chính
        JButton dashboardButton = new JButton("Dashboard");
        dashboardButton.setBackground(new Color(51, 102, 153));
        dashboardButton.setForeground(Color.BLACK); // Đổi màu chữ thành đen
        dashboardButton.setFocusPainted(false);
        dashboardButton.setBorderPainted(false);
        dashboardButton.addActionListener(e -> cardLayout.show(contentPanel, "dashboard"));

        // Thêm button Dashboard vào menuBar
        menuBar.add(dashboardButton);

        // Thêm các menu thông thường
        JMenu fileMenu = new JMenu("Hệ thống");
        JMenuItem logoutMenuItem = new JMenuItem("Đăng xuất");
        JMenuItem exitMenuItem = new JMenuItem("Thoát");

        logoutMenuItem.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    MainFrame.this,
                    "Bạn có chắc chắn muốn đăng xuất?",
                    "Xác nhận đăng xuất",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new LoginFrame().setVisible(true);
            }
        });

        exitMenuItem.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    MainFrame.this,
                    "Bạn có chắc chắn muốn thoát?",
                    "Xác nhận thoát",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        fileMenu.add(logoutMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        // Thêm label người dùng hiện tại ở bên phải
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.setOpaque(false);
        JLabel userLabel = new JLabel("Xin chào, " + username + "!");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        userPanel.add(userLabel);

        JButton logoutButton = new JButton("Đăng xuất");
        logoutButton.setBackground(new Color(220, 53, 69));
        logoutButton.setForeground(Color.BLACK); // Đổi màu chữ thành đen
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    MainFrame.this,
                    "Bạn có chắc chắn muốn đăng xuất?",
                    "Xác nhận đăng xuất",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new LoginFrame().setVisible(true);
            }
        });

        userPanel.add(logoutButton);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(userPanel);

        setJMenuBar(menuBar);
    }

    private void setupLayout() {
        // Thiết lập layout chính
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // Thêm status bar
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        statusBar.setPreferredSize(new Dimension(getWidth(), 25));

        JLabel statusLabel = new JLabel("  Đăng nhập với tài khoản: " + username);
        statusBar.add(statusLabel, BorderLayout.WEST);

        getContentPane().add(statusBar, BorderLayout.SOUTH);
    }

    private void registerListeners() {
        // Xử lý sự kiện cho các button trên dashboard
        readersButton.addActionListener(e -> cardLayout.show(contentPanel, "viewReaders"));
        booksButton.addActionListener(e -> cardLayout.show(contentPanel, "viewBooks"));
        loansButton.addActionListener(e -> cardLayout.show(contentPanel, "createLoan"));
        returnsButton.addActionListener(e -> cardLayout.show(contentPanel, "returnBooks"));
        loanListButton.addActionListener(e -> cardLayout.show(contentPanel, "loanList"));
        statisticsButton.addActionListener(e -> cardLayout.show(contentPanel, "statistics"));

        // Xử lý sự kiện đóng cửa sổ
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                        MainFrame.this,
                        "Bạn có chắc chắn muốn thoát?",
                        "Xác nhận thoát",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    private JPanel createDashboardPanel() {
        JPanel dashboard = new JPanel(new BorderLayout());

        // Header panel với tiêu đề
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(51, 102, 153), 0, getHeight(), new Color(34, 67, 101));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setPreferredSize(new Dimension(dashboard.getWidth(), 120));
        headerPanel.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Hệ thống Quản lý Thư viện", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 32));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        headerPanel.add(welcomeLabel, BorderLayout.CENTER);

        // Panel chứa các ô chức năng
        JPanel tilesPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        tilesPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        tilesPanel.setBackground(new Color(245, 245, 250));

        // Tạo các ô chức năng
        tilesPanel.add(createDashboardTile("Quản lý Độc giả", "Xem và quản lý thông tin độc giả", new Color(70, 130, 180), readersButton));
        tilesPanel.add(createDashboardTile("Quản lý Sách", "Xem và quản lý kho sách", new Color(46, 139, 87), booksButton));
        tilesPanel.add(createDashboardTile("Mượn Sách", "Tạo phiếu mượn sách mới", new Color(205, 92, 92), loansButton));
        tilesPanel.add(createDashboardTile("Trả Sách", "Xử lý trả sách và tính phí phạt", new Color(255, 140, 0), returnsButton));
        tilesPanel.add(createDashboardTile("Danh sách mượn", "Xem danh sách phiếu mượn", new Color(60, 179, 113), loanListButton));
        tilesPanel.add(createDashboardTile("Thống kê", "Xem thống kê dữ liệu thư viện", new Color(123, 104, 238), statisticsButton));

        // Panel thống kê nhanh
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));
        statsPanel.setBackground(new Color(245, 245, 250));

        // Lấy dữ liệu thống kê
        int totalBooks = 0;
        int totalReaders = 0;
        int borrowedBooks = 0;

        try {
            totalBooks = bookService.getTotalBooks();
            totalReaders = readerService.getTotalReaders();
            borrowedBooks = loanService.getBorrowedBooksCount();
        } catch (Exception e) {
            // Xử lý trường hợp không thể lấy dữ liệu
            System.err.println("Không thể lấy dữ liệu thống kê: " + e.getMessage());
        }

        // Thêm các ô thống kê
        statsPanel.add(createStatPanel("Tổng số sách", totalBooks + " quyển", new Color(92, 184, 92)));
        statsPanel.add(createStatPanel("Tổng số độc giả", totalReaders + " người", new Color(66, 139, 202)));
        statsPanel.add(createStatPanel("Sách đang mượn", borrowedBooks + " quyển", new Color(240, 173, 78)));

        // Thêm các panel vào dashboard
        JPanel contentWrapper = new JPanel(new BorderLayout());
        contentWrapper.setBackground(new Color(245, 245, 250));
        contentWrapper.add(tilesPanel, BorderLayout.CENTER);
        contentWrapper.add(statsPanel, BorderLayout.SOUTH);

        dashboard.add(headerPanel, BorderLayout.NORTH);
        dashboard.add(contentWrapper, BorderLayout.CENTER);

        return dashboard;
    }

    private JPanel createDashboardTile(String title, String description, Color color, JButton buttonParam) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(color, 2));

        // Panel tiêu đề
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(color);
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Panel mô tả
        JPanel descPanel = new JPanel();
        descPanel.setBackground(Color.WHITE);
        descPanel.setLayout(new BorderLayout());
        descPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descLabel.setForeground(Color.BLACK);
        descPanel.add(descLabel, BorderLayout.CENTER);

        // Panel nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        // Sử dụng nút được truyền vào
        buttonParam.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color.darker(), 1),
                BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));

        buttonPanel.add(buttonParam);

        // Thêm các panel vào panel chính
        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(descPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Thêm hiệu ứng hover
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panel.setBorder(BorderFactory.createLineBorder(color, 3));
                buttonParam.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panel.setBorder(BorderFactory.createLineBorder(color, 2));
                buttonParam.setBackground(color);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonParam.doClick();
            }
        });

        return panel;
    }

    private JPanel createStatPanel(String title, String value, Color color) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Tiêu đề
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(new Color(70, 70, 70));

        // Giá trị
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valueLabel.setForeground(color);

        // Thêm thành phần vào panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);

        // Căn giữa các thành phần
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(valueLabel);

        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }
}