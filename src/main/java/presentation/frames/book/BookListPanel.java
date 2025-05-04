package presentation.frames.book;

import business.BookService;
import entities.Book;
import presentation.models.BookTableModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class BookListPanel extends JPanel {
    private JTable bookTable;
    private BookTableModel bookTableModel;
    private BookService bookService;
    private JTextField searchField;
    private JButton searchTitleButton;
    private JButton searchISBNButton;
    private JButton refreshButton;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JLabel bookCountLabel;
    private TableRowSorter<BookTableModel> sorter;
    private NumberFormat currencyFormat;

    public BookListPanel() {
        bookService = new BookService();
        currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        initComponents();
        layoutComponents();
        registerListeners();
        loadBooks();
    }

    private void initComponents() {
        bookTableModel = new BookTableModel();
        bookTable = new JTable(bookTableModel);
        bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        sorter = new TableRowSorter<>(bookTableModel);
        bookTable.setRowSorter(sorter);

        searchField = new JTextField(20);
        searchTitleButton = new JButton("Tìm theo Tên sách");
        searchISBNButton = new JButton("Tìm theo ISBN");
        refreshButton = new JButton("Làm mới");

        addButton = new JButton("Thêm Sách");
        editButton = new JButton("Sửa");
        deleteButton = new JButton("Xóa");

        bookCountLabel = new JLabel("Tổng số sách: 0");

        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel headerPanel = new JPanel() {
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
        headerPanel.setPreferredSize(new Dimension(800, 80));
        headerPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Quản lý Sách");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBorder(new EmptyBorder(15, 0, 15, 0));

        headerPanel.add(titleLabel, BorderLayout.CENTER);

        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
                new EmptyBorder(15, 15, 15, 15)
        ));

        searchField.setPreferredSize(new Dimension(250, 35));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));

        styleButton(searchTitleButton, new Color(51, 102, 153));
        styleButton(searchISBNButton, new Color(51, 102, 153));
        styleButton(refreshButton, new Color(150, 150, 150));

        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchPanel.add(searchField);
        searchPanel.add(searchTitleButton);
        searchPanel.add(searchISBNButton);
        searchPanel.add(refreshButton);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        bookTable.setRowHeight(30);
        bookTable.setIntercellSpacing(new Dimension(10, 5));
        bookTable.setGridColor(new Color(230, 230, 230));
        bookTable.setSelectionBackground(new Color(224, 236, 254));
        bookTable.setSelectionForeground(Color.BLACK);
        bookTable.setFont(new Font("Arial", Font.PLAIN, 14));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < bookTable.getColumnCount(); i++) {
            bookTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        bookTable.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (value instanceof Double) {
                    setText(currencyFormat.format(value));
                }

                setHorizontalAlignment(JLabel.RIGHT);
                return c;
            }
        });

        JTableHeader header = bookTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setForeground(new Color(51, 51, 51));
        header.setBackground(new Color(240, 240, 240));
        header.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        bookTable.getColumnModel().getColumn(0).setPreferredWidth(120); // ISBN
        bookTable.getColumnModel().getColumn(1).setPreferredWidth(230); // Title
        bookTable.getColumnModel().getColumn(2).setPreferredWidth(140); // Author
        bookTable.getColumnModel().getColumn(3).setPreferredWidth(140); // Publisher
        bookTable.getColumnModel().getColumn(4).setPreferredWidth(70);  // Year
        bookTable.getColumnModel().getColumn(5).setPreferredWidth(100); // Genre
        bookTable.getColumnModel().getColumn(6).setPreferredWidth(100); // Price
        bookTable.getColumnModel().getColumn(7).setPreferredWidth(70);  // Quantity

        JScrollPane scrollPane = new JScrollPane(bookTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)),
                new EmptyBorder(15, 15, 15, 15)
        ));

        bookCountLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        styleButton(addButton, new Color(92, 184, 92));
        styleButton(editButton, new Color(66, 139, 202));
        styleButton(deleteButton, new Color(217, 83, 79));

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        infoPanel.add(bookCountLabel, BorderLayout.WEST);
        infoPanel.add(buttonPanel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);
        add(searchPanel, BorderLayout.PAGE_START);
        add(tablePanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);
    }

    private void registerListeners() {
        bookTable.getSelectionModel().addListSelectionListener(e -> {
            boolean rowSelected = bookTable.getSelectedRow() >= 0;
            editButton.setEnabled(rowSelected);
            deleteButton.setEnabled(rowSelected);
        });

        bookTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editSelectedBook();
                }
            }
        });

        searchTitleButton.addActionListener(e -> {
            String searchText = searchField.getText().trim();
            if (!searchText.isEmpty()) {
                List<Book> books = bookService.searchBooksByTitle(searchText);
                updateBookTable(books);
            } else {
                loadBooks();
            }
        });

        searchISBNButton.addActionListener(e -> {
            String searchText = searchField.getText().trim();
            if (!searchText.isEmpty()) {
                Book book = bookService.getBookByISBN(searchText);
                if (book != null) {
                    updateBookTable(List.of(book));
                } else {
                    updateBookTable(List.of());
                    JOptionPane.showMessageDialog(
                            this,
                            "Không tìm thấy sách với ISBN: " + searchText,
                            "Không tìm thấy",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            } else {
                loadBooks();
            }
        });

        refreshButton.addActionListener(e -> {
            searchField.setText("");
            loadBooks();
        });

        addButton.addActionListener(e -> {
            showAddBookDialog();
        });

        editButton.addActionListener(e -> {
            editSelectedBook();
        });

        deleteButton.addActionListener(e -> {
            deleteSelectedBook();
        });
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color.darker(), 1),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.brighter());
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(color.darker(), 2),
                        BorderFactory.createEmptyBorder(8, 15, 8, 15)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(color.darker(), 1),
                        BorderFactory.createEmptyBorder(8, 15, 8, 15)
                ));
            }
        });
    }

    public void loadBooks() {
        List<Book> books = bookService.getAllBooks();
        updateBookTable(books);
    }

    private void updateBookTable(List<Book> books) {
        bookTableModel.setBooks(books);
        bookTableModel.fireTableDataChanged();

        int totalBooks = 0;
        for (Book book : books) {
            totalBooks += book.getQuantity();
        }

        bookCountLabel.setText("Tổng số sách: " + totalBooks + " quyển");
    }

    private void editSelectedBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow >= 0) {
            int modelRow = bookTable.convertRowIndexToModel(selectedRow);
            Book selectedBook = bookTableModel.getBookAt(modelRow);

            showEditBookDialog(selectedBook);
        }
    }

    private void deleteSelectedBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow >= 0) {
            int modelRow = bookTable.convertRowIndexToModel(selectedRow);
            Book selectedBook = bookTableModel.getBookAt(modelRow);

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc chắn muốn xóa sách '" + selectedBook.getTitle() + "'?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = bookService.deleteBook(selectedBook.getISBN());
                if (success) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Xóa sách thành công!",
                            "Thành công",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    loadBooks();
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Không thể xóa sách. Sách có thể đang được mượn.",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }
    }

    public void showAddBookDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Thêm Sách Mới");
        dialog.setModal(true);
        dialog.setSize(550, 600);
        dialog.setLocationRelativeTo(this);

        AddBookPanel addBookPanel = new AddBookPanel();

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)),
                new EmptyBorder(15, 15, 15, 15)
        ));

        JButton saveButton = new JButton("Lưu");
        JButton cancelButton = new JButton("Hủy");

        styleButton(saveButton, new Color(92, 184, 92));
        styleButton(cancelButton, new Color(150, 150, 150));

        saveButton.addActionListener(e -> {
            Book book = addBookPanel.getBook();
            if (book != null) {
                boolean success = bookService.addBook(book);
                if (success) {
                    JOptionPane.showMessageDialog(
                            dialog,
                            "Thêm sách thành công!",
                            "Thành công",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    dialog.dispose();
                    loadBooks();
                } else {
                    JOptionPane.showMessageDialog(
                            dialog,
                            "Không thể thêm sách. Vui lòng kiểm tra lại thông tin.",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        dialog.setLayout(new BorderLayout());
        dialog.add(addBookPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void showEditBookDialog(Book book) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Sửa Thông Tin Sách");
        dialog.setModal(true);
        dialog.setSize(550, 600);
        dialog.setLocationRelativeTo(this);

        EditBookPanel editBookPanel = new EditBookPanel(book);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)),
                new EmptyBorder(15, 15, 15, 15)
        ));

        JButton saveButton = new JButton("Lưu");
        JButton cancelButton = new JButton("Hủy");

        styleButton(saveButton, new Color(92, 184, 92));
        styleButton(cancelButton, new Color(150, 150, 150));

        saveButton.addActionListener(e -> {
            Book updatedBook = editBookPanel.getBook();
            if (updatedBook != null) {
                boolean success = bookService.updateBook(updatedBook);
                if (success) {
                    JOptionPane.showMessageDialog(
                            dialog,
                            "Cập nhật sách thành công!",
                            "Thành công",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    dialog.dispose();
                    loadBooks();
                } else {
                    JOptionPane.showMessageDialog(
                            dialog,
                            "Không thể cập nhật sách. Vui lòng kiểm tra lại thông tin.",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        dialog.setLayout(new BorderLayout());
        dialog.add(editBookPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}
