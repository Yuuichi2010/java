package presentation.frames.reader;

import business.ReaderService;
import entities.Reader;
import presentation.models.ReaderTableModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
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
    private TableRowSorter<ReaderTableModel> sorter;
    private DateTimeFormatter dateFormatter;

    public ReaderListPanel() {
        readerService = new ReaderService();
        dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        initComponents();
        layoutComponents();
        registerListeners();
        loadReaders();
    }

    private void initComponents() {
        readerTableModel = new ReaderTableModel();
        readerTable = new JTable(readerTableModel);
        readerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Tạo sorter để sắp xếp và lọc
        sorter = new TableRowSorter<>(readerTableModel);
        readerTable.setRowSorter(sorter);

        String[] searchTypes = {"Tên", "CMND/CCCD", "Mã độc giả"};
        searchTypeComboBox = new JComboBox<>(searchTypes);
        searchField = new JTextField(20);
        searchButton = new JButton("Tìm kiếm");
        refreshButton = new JButton("Làm mới");

        addButton = new JButton("Thêm độc giả");
        editButton = new JButton("Sửa");
        deleteButton = new JButton("Xóa");

        readerCountLabel = new JLabel("Tổng số độc giả: 0");

        // Thiết lập các nút không hoạt động ban đầu
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Panel tiêu đề
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth(), h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(70, 130, 180), 0, h, new Color(51, 102, 153));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        headerPanel.setPreferredSize(new Dimension(800, 80));
        headerPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Quản lý Độc giả");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBorder(new EmptyBorder(15, 0, 15, 0));

        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Panel tìm kiếm
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
                new EmptyBorder(15, 15, 15, 15)
        ));

        // Thiết lập style cho các thành phần tìm kiếm
        searchField.setPreferredSize(new Dimension(250, 35));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));

        searchTypeComboBox.setPreferredSize(new Dimension(150, 35));
        searchTypeComboBox.setFont(new Font("Arial", Font.PLAIN, 14));

        styleButton(searchButton, new Color(91, 109, 194));
        styleButton(refreshButton, new Color(150, 150, 150));

        JLabel searchByLabel = new JLabel("Tìm theo:");
        searchByLabel.setFont(new Font("Arial", Font.BOLD, 14));

        searchPanel.add(searchByLabel);
        searchPanel.add(searchTypeComboBox);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(refreshButton);

        // Panel bảng
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Thiết lập style cho bảng
        readerTable.setRowHeight(35);
        readerTable.setIntercellSpacing(new Dimension(10, 5));
        readerTable.setGridColor(new Color(230, 230, 230));
        readerTable.setSelectionBackground(new Color(224, 236, 254));
        readerTable.setSelectionForeground(Color.BLACK);
        readerTable.setFont(new Font("Arial", Font.PLAIN, 14));

        // Căn giữa nội dung các cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < readerTable.getColumnCount(); i++) {
            readerTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Thiết lập header
        JTableHeader header = readerTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setForeground(new Color(51, 51, 51));
        header.setBackground(new Color(240, 240, 240));
        header.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        // Thiết lập kích thước cột
        readerTable.getColumnModel().getColumn(0).setPreferredWidth(80);   // ID
        readerTable.getColumnModel().getColumn(1).setPreferredWidth(180);  // Họ tên
        readerTable.getColumnModel().getColumn(2).setPreferredWidth(120);  // CMND
        readerTable.getColumnModel().getColumn(3).setPreferredWidth(80);   // Giới tính
        readerTable.getColumnModel().getColumn(4).setPreferredWidth(100);  // Ngày sinh
        readerTable.getColumnModel().getColumn(5).setPreferredWidth(150);  // Email
        readerTable.getColumnModel().getColumn(6).setPreferredWidth(100);  // Ngày đăng ký
        readerTable.getColumnModel().getColumn(7).setPreferredWidth(100);  // Ngày hết hạn

        JScrollPane scrollPane = new JScrollPane(readerTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Panel thông tin và nút bấm
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)),
                new EmptyBorder(15, 15, 15, 15)
        ));

        // Thiết lập style cho infoLabel
        readerCountLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Panel nút bấm
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        styleButton(addButton, new Color(88, 186, 60));
        styleButton(editButton, new Color(66, 139, 202));
        styleButton(deleteButton, new Color(217, 83, 79));

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        infoPanel.add(readerCountLabel, BorderLayout.WEST);
        infoPanel.add(buttonPanel, BorderLayout.EAST);

        // Thêm các panel vào panel chính
        add(headerPanel, BorderLayout.NORTH);
        add(searchPanel, BorderLayout.PAGE_START);
        add(tablePanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);
    }

    private void registerListeners() {
        // Lắng nghe sự kiện chọn dòng trong bảng
        readerTable.getSelectionModel().addListSelectionListener(e -> {
            boolean rowSelected = readerTable.getSelectedRow() >= 0;
            editButton.setEnabled(rowSelected);
            deleteButton.setEnabled(rowSelected);
        });

        // Lắng nghe sự kiện nhấp đúp chuột để mở sửa độc giả
        readerTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editSelectedReader();
                }
            }
        });

        // Lắng nghe sự kiện tìm kiếm
        searchButton.addActionListener(e -> {
            String searchText = searchField.getText().trim();
            String searchType = (String) searchTypeComboBox.getSelectedItem();

            if (!searchText.isEmpty()) {
                List<Reader> readers;

                if ("Tên".equals(searchType)) {
                    readers = readerService.searchReadersByName(searchText);
                } else if ("CMND/CCCD".equals(searchType)) {
                    Reader reader = readerService.getReaderByIdCard(searchText);
                    readers = reader != null ? List.of(reader) : List.of();
                } else { // Mã độc giả
                    Reader reader = readerService.getReaderByID(searchText);
                    readers = reader != null ? List.of(reader) : List.of();
                }

                updateReaderTable(readers);

                if (readers.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Không tìm thấy độc giả phù hợp.",
                            "Không tìm thấy",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            } else {
                loadReaders();
            }
        });

        // Lắng nghe sự kiện làm mới
        refreshButton.addActionListener(e -> {
            searchField.setText("");
            loadReaders();
        });

        // Lắng nghe sự kiện thêm độc giả
        addButton.addActionListener(e -> {
            showAddReaderDialog();
        });

        // Lắng nghe sự kiện sửa độc giả
        editButton.addActionListener(e -> {
            editSelectedReader();
        });

        // Lắng nghe sự kiện xóa độc giả
        deleteButton.addActionListener(e -> {
            deleteSelectedReader();
        });
    }

    private void styleButton(JButton button, Color color) {
        // Đặt nền và màu chữ đen
        button.setBackground(color);
        button.setForeground(Color.BLACK);

        // Phông chữ cho nút
        button.setFont(new Font("Arial", Font.BOLD, 14));

        // Không viền khi chọn
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color.darker(), 1),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hiệu ứng hover với hiệu ứng bo góc
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

    public void loadReaders() {
        List<Reader> readers = readerService.getAllReaders();
        updateReaderTable(readers);
    }

    private void updateReaderTable(List<Reader> readers) {
        readerTableModel.setReaders(readers);
        readerTableModel.fireTableDataChanged();
        readerCountLabel.setText("Tổng số độc giả: " + readers.size());
    }

    private void editSelectedReader() {
        int selectedRow = readerTable.getSelectedRow();
        if (selectedRow >= 0) {
            int modelRow = readerTable.convertRowIndexToModel(selectedRow);
            Reader selectedReader = readerTableModel.getReaderAt(modelRow);

            showEditReaderDialog(selectedReader);
        }
    }

    private void deleteSelectedReader() {
        int selectedRow = readerTable.getSelectedRow();
        if (selectedRow >= 0) {
            int modelRow = readerTable.convertRowIndexToModel(selectedRow);
            Reader selectedReader = readerTableModel.getReaderAt(modelRow);

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc chắn muốn xóa độc giả '" + selectedReader.getFullName() + "'?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = readerService.deleteReader(selectedReader.getReaderID());
                if (success) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Xóa độc giả thành công!",
                            "Thành công",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    loadReaders();
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Không thể xóa độc giả. Độc giả có thể đang mượn sách.",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }
    }

    public void showAddReaderDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Thêm Độc giả Mới");
        dialog.setModal(true);
        dialog.setSize(600, 650);
        dialog.setLocationRelativeTo(this);

        // Tạo panel thêm độc giả
        AddReaderPanel addReaderPanel = new AddReaderPanel();

        // Tạo panel nút bấm
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
            Reader reader = addReaderPanel.getReader();
            if (reader != null) {
                boolean success = readerService.addReader(reader);
                if (success) {
                    JOptionPane.showMessageDialog(
                            dialog,
                            "Thêm độc giả thành công!",
                            "Thành công",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    dialog.dispose();
                    loadReaders();
                } else {
                    JOptionPane.showMessageDialog(
                            dialog,
                            "Không thể thêm độc giả. Vui lòng kiểm tra lại thông tin.",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Thiết lập dialog
        dialog.setLayout(new BorderLayout());
        dialog.add(addReaderPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void showEditReaderDialog(Reader reader) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Sửa Thông Tin Độc giả");
        dialog.setModal(true);
        dialog.setSize(600, 650);
        dialog.setLocationRelativeTo(this);

        // Tạo panel sửa độc giả
        EditReaderPanel editReaderPanel = new EditReaderPanel(reader);

        // Tạo panel nút bấm
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
            Reader updatedReader = editReaderPanel.getReader();
            if (updatedReader != null) {
                boolean success = readerService.updateReader(updatedReader);
                if (success) {
                    JOptionPane.showMessageDialog(
                            dialog,
                            "Cập nhật độc giả thành công!",
                            "Thành công",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    dialog.dispose();
                    loadReaders();
                } else {
                    JOptionPane.showMessageDialog(
                            dialog,
                            "Không thể cập nhật độc giả. Vui lòng kiểm tra lại thông tin.",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Thiết lập dialog
        dialog.setLayout(new BorderLayout());
        dialog.add(editReaderPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}