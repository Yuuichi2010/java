package business;

import data.BookDAO;
import data.LoanDAO;
import data.ReaderDAO;
import entities.Book;
import entities.Loan;
import entities.Reader;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsService {
    private BookService bookService;
    private ReaderService readerService;
    private LoanService loanService;

    public StatisticsService() {
        this.bookService = new BookService();
        this.readerService = new ReaderService();
        this.loanService = new LoanService();
    }

    // Thống kê số lượng sách trong thư viện
    public int getTotalBooks() {
        return bookService.getTotalBooks();
    }

    // Thống kê số lượng sách theo thể loại
    public List<Object[]> getBookCountByGenre() {
        return bookService.getBookCountByGenre();
    }

    // Thống kê số lượng độc giả
    public int getTotalReaders() {
        return readerService.getTotalReaders();
    }

    // Thống kê số lượng độc giả theo giới tính
    public List<Object[]> getReaderCountByGender() {
        return readerService.getReaderCountByGender();
    }

    // Thống kê số sách đang được mượn
    public int getBorrowedBooksCount() {
        return loanService.getBorrowedBooksCount();
    }

    // Thống kê số phiếu mượn
    public int getTotalLoans() {
        return loanService.getAllLoans().size();
    }

    // Thống kê số phiếu mượn quá hạn
    public int getOverdueLoansCount() {
        return loanService.getOverdueLoans().size();
    }

    // Thống kê tổng tiền phạt đã thu
    public double getTotalCollectedFines() {
        double totalFines = 0.0;
        List<Loan> loans = loanService.getAllLoans();

        for (Loan loan : loans) {
            if (loan.isReturned()) {
                totalFines += loanService.calculateTotalFine(loan.getLoanID());
            }
        }

        return totalFines;
    }

    // Thống kê sách phổ biến nhất
// Cập nhật phương thức thống kê sách mượn nhiều nhất để bao gồm tên tác giả
    public Map<String, Integer> getMostPopularBooks(int limit) {
        Map<String, Integer> popularBooks = new HashMap<>();
        List<Loan> loans = loanService.getAllLoans();

        // Đếm số lần mỗi sách được mượn và lấy tên tác giả
        for (Loan loan : loans) {
            for (var detail : loan.getLoanDetails()) {
                Book book = detail.getBook();
                if (book != null) {
                    String bookTitle = book.getTitle();
                    String author = book.getAuthor();  // Lấy tên tác giả của sách

                    // Thêm thông tin sách mượn kèm tác giả vào map
                    String displayText = bookTitle + " - " + author;  // Hiển thị tên sách và tác giả
                    popularBooks.put(displayText, popularBooks.getOrDefault(displayText, 0) + 1);
                }
            }
        }

        // Trả về danh sách sách mượn nhiều nhất với tên sách và tác giả
        return popularBooks;
    }

    // Thống kê độc giả mượn nhiều sách nhất
    public Map<String, Integer> getMostActiveReaders(int limit) {
        Map<String, Integer> activeReaders = new HashMap<>();
        List<Loan> loans = loanService.getAllLoans();

        // Đếm số sách mỗi độc giả đã mượn
        for (Loan loan : loans) {
            String readerName = loan.getReader() != null ? loan.getReader().getFullName() : loan.getReaderID();
            String readerID = loan.getReader() != null ? loan.getReader().getReaderID() : loan.getReaderID(); // Lấy mã độc giả
            int bookCount = loan.getLoanDetails().size();

            // Tạo chuỗi độc giả (Tên độc giả - Mã độc giả) để lưu vào Map
            String readerInfo = readerName + " (" + readerID + ")";
            activeReaders.put(readerInfo, activeReaders.getOrDefault(readerInfo, 0) + bookCount);
        }

        // Sắp xếp và lấy top N độc giả
        return activeReaders;
    }
}