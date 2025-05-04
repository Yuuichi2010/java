package data;

import entities.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private Connection connection;

    public BookDAO() {
        connection = DBConnection.getInstance().getConnection();
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM Book";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Book book = new Book();
                book.setISBN(rs.getString("ISBN"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setPublisher(rs.getString("publisher"));
                book.setPublishYear(rs.getInt("publishYear"));
                book.setGenre(rs.getString("genre"));
                book.setPrice(rs.getDouble("price"));
                book.setQuantity(rs.getInt("quantity"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    public Book getBookByISBN(String ISBN) {
        String query = "SELECT * FROM Book WHERE ISBN = ?";
        Book book = null;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, ISBN);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    book = new Book();
                    book.setISBN(rs.getString("ISBN"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    book.setPublisher(rs.getString("publisher"));
                    book.setPublishYear(rs.getInt("publishYear"));
                    book.setGenre(rs.getString("genre"));
                    book.setPrice(rs.getDouble("price"));
                    book.setQuantity(rs.getInt("quantity"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return book;
    }

    public List<Book> searchBooksByTitle(String title) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM Book WHERE title LIKE ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, "%" + title + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Book book = new Book();
                    book.setISBN(rs.getString("ISBN"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    book.setPublisher(rs.getString("publisher"));
                    book.setPublishYear(rs.getInt("publishYear"));
                    book.setGenre(rs.getString("genre"));
                    book.setPrice(rs.getDouble("price"));
                    book.setQuantity(rs.getInt("quantity"));
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    public boolean addBook(Book book) {
        String query = "INSERT INTO Book (ISBN, title, author, publisher, publishYear, genre, price, quantity) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, book.getISBN());
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getAuthor());
            pstmt.setString(4, book.getPublisher());
            pstmt.setInt(5, book.getPublishYear());
            pstmt.setString(6, book.getGenre());
            pstmt.setDouble(7, book.getPrice());
            pstmt.setInt(8, book.getQuantity());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBook(Book book) {
        String query = "UPDATE Book SET title = ?, author = ?, publisher = ?, publishYear = ?, " +
                "genre = ?, price = ?, quantity = ? WHERE ISBN = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getPublisher());
            pstmt.setInt(4, book.getPublishYear());
            pstmt.setString(5, book.getGenre());
            pstmt.setDouble(6, book.getPrice());
            pstmt.setInt(7, book.getQuantity());
            pstmt.setString(8, book.getISBN());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBook(String ISBN) {
        String query = "DELETE FROM Book WHERE ISBN = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, ISBN);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getTotalBooks() {
        String query = "SELECT SUM(quantity) as total FROM Book";
        int total = 0;

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    public List<Object[]> getBookCountByGenre() {
        List<Object[]> result = new ArrayList<>();
        String query = "SELECT genre, SUM(quantity) as count FROM Book GROUP BY genre";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Object[] row = new Object[2];
                row[0] = rs.getString("genre");
                row[1] = rs.getInt("count");
                result.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int getBorrowedBooksCount() {
        String query = "SELECT COUNT(*) as count FROM LoanDetail WHERE status = 'Borrowed'";
        int count = 0;

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }
}