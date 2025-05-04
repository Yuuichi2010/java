package business;

import data.BookDAO;
import entities.Book;
import java.util.List;

public class BookService {
    private BookDAO bookDAO;

    public BookService() {
        this.bookDAO = new BookDAO();
    }

    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    public Book getBookByISBN(String ISBN) {
        return bookDAO.getBookByISBN(ISBN);
    }

    public List<Book> searchBooksByTitle(String title) {
        return bookDAO.searchBooksByTitle(title);
    }

    public boolean addBook(Book book) {
        // Validate book data
        if (book.getISBN() == null || book.getISBN().trim().isEmpty()) {
            return false;
        }
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            return false;
        }
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            return false;
        }
        if (book.getPublisher() == null || book.getPublisher().trim().isEmpty()) {
            return false;
        }
        if (book.getPublishYear() <= 0) {
            return false;
        }
        if (book.getGenre() == null || book.getGenre().trim().isEmpty()) {
            return false;
        }
        if (book.getPrice() <= 0) {
            return false;
        }
        if (book.getQuantity() <= 0) {
            return false;
        }

        // Check if book with same ISBN already exists
        Book existingBook = bookDAO.getBookByISBN(book.getISBN());
        if (existingBook != null) {
            return false;
        }

        return bookDAO.addBook(book);
    }

    public boolean updateBook(Book book) {
        // Validate book data
        if (book.getISBN() == null || book.getISBN().trim().isEmpty()) {
            return false;
        }
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            return false;
        }
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            return false;
        }
        if (book.getPublisher() == null || book.getPublisher().trim().isEmpty()) {
            return false;
        }
        if (book.getPublishYear() <= 0) {
            return false;
        }
        if (book.getGenre() == null || book.getGenre().trim().isEmpty()) {
            return false;
        }
        if (book.getPrice() <= 0) {
            return false;
        }
        if (book.getQuantity() < 0) {
            return false;
        }

        // Check if book exists
        Book existingBook = bookDAO.getBookByISBN(book.getISBN());
        if (existingBook == null) {
            return false;
        }

        return bookDAO.updateBook(book);
    }

    public boolean deleteBook(String ISBN) {
        // Check if book exists
        Book existingBook = bookDAO.getBookByISBN(ISBN);
        if (existingBook == null) {
            return false;
        }

        return bookDAO.deleteBook(ISBN);
    }

    public int getTotalBooks() {
        return bookDAO.getTotalBooks();
    }

    public List<Object[]> getBookCountByGenre() {
        return bookDAO.getBookCountByGenre();
    }

    public int getBorrowedBooksCount() {
        return bookDAO.getBorrowedBooksCount();
    }

    public boolean isBookAvailable(String ISBN) {
        Book book = bookDAO.getBookByISBN(ISBN);
        return book != null && book.getQuantity() > 0;
    }

    public boolean decreaseBookQuantity(String ISBN) {
        Book book = bookDAO.getBookByISBN(ISBN);
        if (book == null || book.getQuantity() <= 0) {
            return false;
        }

        book.setQuantity(book.getQuantity() - 1);
        return bookDAO.updateBook(book);
    }

    public boolean increaseBookQuantity(String ISBN) {
        Book book = bookDAO.getBookByISBN(ISBN);
        if (book == null) {
            return false;
        }

        book.setQuantity(book.getQuantity() + 1);
        return bookDAO.updateBook(book);
    }
}