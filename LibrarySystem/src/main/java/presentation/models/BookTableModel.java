package presentation.models;

import entities.Book;

import javax.swing.table.AbstractTableModel;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BookTableModel extends AbstractTableModel {
    private List<Book> books;
    private final String[] columnNames = {"ISBN", "Title", "Author", "Publisher", "Year", "Genre", "Price", "Quantity"};
    private final NumberFormat currencyFormat;

    public BookTableModel() {
        this.books = new ArrayList<>();
        // Định dạng tiền tệ Việt Nam
        this.currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    }

    @Override
    public int getRowCount() {
        return books.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 4: // Year
                return Integer.class;
            case 6: // Price
                return Double.class;
            case 7: // Quantity
                return Integer.class;
            default:
                return String.class;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Book book = books.get(rowIndex);

        switch (columnIndex) {
            case 0: return book.getISBN();
            case 1: return book.getTitle();
            case 2: return book.getAuthor();
            case 3: return book.getPublisher();
            case 4: return book.getPublishYear();
            case 5: return book.getGenre();
            case 6: return book.getPrice();
            case 7: return book.getQuantity();
            default: return null;
        }
    }

    // Xóa @Override vì đây không phải phương thức ghi đè
    public String getFormattedValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 6) {
            // Trả về giá tiền đã định dạng
            Book book = books.get(rowIndex);
            return currencyFormat.format(book.getPrice());
        }
        return getValueAt(rowIndex, columnIndex).toString();
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Book getBookAt(int rowIndex) {
        return books.get(rowIndex);
    }
}