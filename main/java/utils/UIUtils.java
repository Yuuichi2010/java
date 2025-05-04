package utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Lớp tiện ích để áp dụng style đồng nhất cho toàn bộ ứng dụng
 */
public class UIUtils {

    // Màu sắc chính
    public static final Color PRIMARY_COLOR = new Color(51, 102, 153);
    public static final Color SUCCESS_COLOR = new Color(92, 184, 92);
    public static final Color INFO_COLOR = new Color(66, 139, 202);
    public static final Color WARNING_COLOR = new Color(240, 173, 78);
    public static final Color DANGER_COLOR = new Color(217, 83, 79);
    public static final Color NEUTRAL_COLOR = new Color(150, 150, 150);

    // Font chữ
    public static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 24);
    public static final Font HEADING_FONT = new Font("Arial", Font.BOLD, 18);
    public static final Font NORMAL_FONT = new Font("Arial", Font.PLAIN, 14);
    public static final Font BOLD_FONT = new Font("Arial", Font.BOLD, 14);

    /**
     * Áp dụng style cho nút bấm để chữ hiển thị rõ ràng
     */
    public static void applyButtonStyle(JButton button, String text, Color bgColor) {
        // Thiết lập text
        button.setText(text);

        // Thiết lập font chữ đậm và lớn
        button.setFont(BOLD_FONT);

        // Màu nền và chữ có độ tương phản cao
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);

        // Thêm viền để nút nổi bật
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 2),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));

        // Không vẽ viền focus để chữ rõ hơn
        button.setFocusPainted(false);

        // Đảm bảo opaque để hiển thị màu nền
        button.setOpaque(true);

        // Thiết lập bóng cho nút
        button.setBorderPainted(true);

        // Con trỏ tay khi hover
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Thêm hiệu ứng hover để tăng tính tương tác
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(brighten(bgColor, 0.2f));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
    }

    /**
     * Tạo một gradient panel với màu sắc chỉ định và tiêu đề rõ ràng
     */
    public static JPanel createHeaderPanel(String title, Color startColor, Color endColor) {
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth(), h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, startColor, 0, h, endColor);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        headerPanel.setPreferredSize(new Dimension(800, 80));
        headerPanel.setLayout(new BorderLayout());

        // Tạo title với hiệu ứng đổ bóng để chữ nổi bật
        JLabel titleLabel = new JLabel(title) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                // Vẽ đổ bóng cho chữ
                g2d.setColor(new Color(0, 0, 0, 100));
                g2d.drawString(getText(), 2, getHeight() - 9);

                // Vẽ chữ chính
                g2d.setColor(getForeground());
                g2d.drawString(getText(), 0, getHeight() - 10);
            }
        };
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBorder(new EmptyBorder(15, 0, 15, 0));

        headerPanel.add(titleLabel, BorderLayout.CENTER);

        return headerPanel;
    }

    /**
     * Áp dụng style cho trường văn bản với viền rõ ràng
     */
    public static void applyTextFieldStyle(JTextField textField) {
        textField.setPreferredSize(new Dimension(200, 35));
        textField.setFont(NORMAL_FONT);

        // Thêm viền để field nổi bật
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Màu nền nhẹ để dễ phân biệt
        textField.setBackground(new Color(252, 252, 252));
    }

    /**
     * Áp dụng style cho bảng với các đường viền và chữ rõ ràng
     */
    public static void applyTableStyle(JTable table) {
        // Thiết lập font chữ đậm và lớn
        table.setFont(NORMAL_FONT);

        // Tăng kích thước dòng để dễ đọc
        table.setRowHeight(35);

        // Khoảng cách giữa các ô
        table.setIntercellSpacing(new Dimension(10, 5));

        // Màu lưới rõ ràng
        table.setGridColor(new Color(200, 200, 200));

        // Màu nền khi chọn
        table.setSelectionBackground(new Color(224, 236, 254));
        table.setSelectionForeground(Color.BLACK);

        // Thiết lập header
        JTableHeader header = table.getTableHeader();
        header.setFont(BOLD_FONT);
        header.setForeground(new Color(51, 51, 51));
        header.setBackground(new Color(240, 240, 240));
        header.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        header.setReorderingAllowed(false);

        // Đảm bảo hiển thị đầy đủ tiêu đề cột
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Căn giữa nội dung các ô
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    /**
     * Hàm tạo màu sáng hơn cho hiệu ứng hover
     */
    private static Color brighten(Color color, float fraction) {
        int red = (int) Math.min(255, color.getRed() + 255 * fraction);
        int green = (int) Math.min(255, color.getGreen() + 255 * fraction);
        int blue = (int) Math.min(255, color.getBlue() + 255 * fraction);

        return new Color(red, green, blue);
    }

    /**
     * Thiết lập style cho combo box
     */
    public static void applyComboBoxStyle(JComboBox<?> comboBox) {
        comboBox.setFont(NORMAL_FONT);
        comboBox.setPreferredSize(new Dimension(150, 35));
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1));

        // Tăng kích thước các mục trong dropdown
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                label.setBorder(new EmptyBorder(5, 10, 5, 10));
                return label;
            }
        });
    }

    /**
     * Thiết lập style cho các nút ở phần dưới
     */
    public static JPanel createButtonPanel(JButton addButton, JButton editButton, JButton deleteButton) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panel.setBackground(Color.WHITE);

        // Áp dụng style cho các nút
        applyButtonStyle(addButton, "Thêm", SUCCESS_COLOR);
        applyButtonStyle(editButton, "Sửa", INFO_COLOR);
        applyButtonStyle(deleteButton, "Xóa", DANGER_COLOR);

        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);

        return panel;
    }

    /**
     * Áp dụng style toàn cục cho ứng dụng
     */
    public static void applyGlobalStyle() {
        try {
            // Sử dụng look and feel của hệ thống
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Thiết lập font mặc định
            UIManager.put("Button.font", BOLD_FONT);
            UIManager.put("Label.font", NORMAL_FONT);
            UIManager.put("TextField.font", NORMAL_FONT);
            UIManager.put("Table.font", NORMAL_FONT);
            UIManager.put("TableHeader.font", BOLD_FONT);
            UIManager.put("ComboBox.font", NORMAL_FONT);

            // Tăng độ tương phản cho một số thành phần UI
            UIManager.put("Button.background", new Color(240, 240, 240));
            UIManager.put("Button.foreground", Color.BLACK);
            UIManager.put("Button.border", BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}