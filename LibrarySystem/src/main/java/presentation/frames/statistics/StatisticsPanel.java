package presentation.frames.statistics;

import business.BookService;
import business.LoanService;
import business.ReaderService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Vector;

public class StatisticsPanel extends JPanel {
    private JLabel totalBooksLabel;
    private JLabel totalReadersLabel;
    private JLabel borrowedBooksLabel;

    private JTable genreStatsTable;
    private DefaultTableModel genreStatsModel;

    private JTable genderStatsTable;
    private DefaultTableModel genderStatsModel;

    private JButton refreshButton;

    private BookService bookService;
    private ReaderService readerService;
    private LoanService loanService;

    public StatisticsPanel() {
        bookService = new BookService();
        readerService = new ReaderService();
        loanService = new LoanService();

        initComponents();
        layoutComponents();
        registerListeners();
        loadStatistics();
    }

    private void initComponents() {
        totalBooksLabel = new JLabel("Total Books: Loading...");
        totalReadersLabel = new JLabel("Total Readers: Loading...");
        borrowedBooksLabel = new JLabel("Books Currently Borrowed: Loading...");

        // Genre statistics table
        String[] genreColumns = {"Genre", "Count"};
        genreStatsModel = new DefaultTableModel(genreColumns, 0);
        genreStatsTable = new JTable(genreStatsModel);

        // Gender statistics table
        String[] genderColumns = {"Gender", "Count"};
        genderStatsModel = new DefaultTableModel(genderColumns, 0);
        genderStatsTable = new JTable(genderStatsModel);

        refreshButton = new JButton("Refresh Statistics");
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());

        // Main panel with border and background
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 245, 250));

        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(51, 102, 153));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("Library Statistics Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        refreshButton.setBackground(new Color(230, 230, 230));
        refreshButton.setForeground(new Color(60, 60, 60));
        refreshButton.setFocusPainted(false);
        refreshButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JPanel refreshPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        refreshPanel.setOpaque(false);
        refreshPanel.add(refreshButton);
        headerPanel.add(refreshPanel, BorderLayout.EAST);

        // Summary cards panel
        JPanel summaryCardsPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        summaryCardsPanel.setOpaque(false);

        // Total books card
        JPanel booksCard = createStatCard(totalBooksLabel, "📚", new Color(46, 139, 87));

        // Total readers card
        JPanel readersCard = createStatCard(totalReadersLabel, "👥", new Color(70, 130, 180));

        // Borrowed books card
        JPanel borrowedCard = createStatCard(borrowedBooksLabel, "📖", new Color(205, 92, 92));

        summaryCardsPanel.add(booksCard);
        summaryCardsPanel.add(readersCard);
        summaryCardsPanel.add(borrowedCard);

        // Table panels
        JPanel tablesPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        tablesPanel.setOpaque(false);

        // Style the tables
        styleTable(genreStatsTable);
        styleTable(genderStatsTable);

        // Genre statistics panel
        JPanel genrePanel = new JPanel(new BorderLayout(0, 10));
        genrePanel.setBackground(Color.WHITE);
        genrePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel genreTitle = new JLabel("Books by Genre");
        genreTitle.setFont(new Font("Arial", Font.BOLD, 16));
        genreTitle.setForeground(new Color(70, 70, 70));

        genrePanel.add(genreTitle, BorderLayout.NORTH);
        genrePanel.add(new JScrollPane(genreStatsTable), BorderLayout.CENTER);

        // Gender statistics panel
        JPanel genderPanel = new JPanel(new BorderLayout(0, 10));
        genderPanel.setBackground(Color.WHITE);
        genderPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel genderTitle = new JLabel("Readers by Gender");
        genderTitle.setFont(new Font("Arial", Font.BOLD, 16));
        genderTitle.setForeground(new Color(70, 70, 70));

        genderPanel.add(genderTitle, BorderLayout.NORTH);
        genderPanel.add(new JScrollPane(genderStatsTable), BorderLayout.CENTER);

        tablesPanel.add(genrePanel);
        tablesPanel.add(genderPanel);

        // Add everything to the main panel
        JPanel contentPanel = new JPanel(new BorderLayout(0, 15));
        contentPanel.setOpaque(false);
        contentPanel.add(summaryCardsPanel, BorderLayout.NORTH);
        contentPanel.add(tablesPanel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createStatCard(JLabel statLabel, String icon, Color color) {
        // Create panel with white background and border
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Create icon label
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 36));
        iconLabel.setForeground(color);

        // Style the stat label
        statLabel.setFont(new Font("Arial", Font.BOLD, 18));
        statLabel.setForeground(new Color(70, 70, 70));

        // Add a small color indicator on top
        JPanel colorBar = new JPanel();
        colorBar.setBackground(color);
        colorBar.setPreferredSize(new Dimension(cardPanel.getWidth(), 5));

        // Layout the card
        JPanel contentPanel = new JPanel(new BorderLayout(10, 0));
        contentPanel.setOpaque(false);
        contentPanel.add(iconLabel, BorderLayout.WEST);
        contentPanel.add(statLabel, BorderLayout.CENTER);

        cardPanel.add(colorBar, BorderLayout.NORTH);
        cardPanel.add(contentPanel, BorderLayout.CENTER);

        return cardPanel;
    }

    private void styleTable(JTable table) {
        // Set table appearance
        table.setRowHeight(25);
        table.setIntercellSpacing(new Dimension(10, 5));
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(224, 236, 254));
        table.setSelectionForeground(Color.BLACK);
        table.setShowVerticalLines(false);
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        // Set header appearance
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setForeground(new Color(70, 70, 70));
        table.getTableHeader().setBackground(new Color(240, 240, 240));
        table.getTableHeader().setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
    }

    private void registerListeners() {
        refreshButton.addActionListener(e -> loadStatistics());
    }

    private void loadStatistics() {
        // Load summary statistics
        int totalBooks = bookService.getTotalBooks();
        int totalReaders = readerService.getTotalReaders();
        int borrowedBooks = loanService.getBorrowedBooksCount();

        totalBooksLabel.setText("Total Books: " + totalBooks);
        totalReadersLabel.setText("Total Readers: " + totalReaders);
        borrowedBooksLabel.setText("Books Currently Borrowed: " + borrowedBooks);

        // Load genre statistics
        loadGenreStatistics();

        // Load gender statistics
        loadGenderStatistics();
    }

    private void loadGenreStatistics() {
        // Clear the table
        genreStatsModel.setRowCount(0);

        // Get book count by genre
        List<Object[]> genreStats = bookService.getBookCountByGenre();

        // Add rows to the table
        for (Object[] row : genreStats) {
            Vector<Object> tableRow = new Vector<>();
            tableRow.add(row[0]); // Genre
            tableRow.add(row[1]); // Count
            genreStatsModel.addRow(tableRow);
        }
    }

    private void loadGenderStatistics() {
        // Clear the table
        genderStatsModel.setRowCount(0);

        // Get reader count by gender
        List<Object[]> genderStats = readerService.getReaderCountByGender();

        // Add rows to the table
        for (Object[] row : genderStats) {
            Vector<Object> tableRow = new Vector<>();
            tableRow.add(row[0]); // Gender
            tableRow.add(row[1]); // Count
            genderStatsModel.addRow(tableRow);
        }
    }
}