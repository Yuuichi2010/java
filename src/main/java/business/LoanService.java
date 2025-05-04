package business;

import data.LoanDAO;
import entities.Book;
import entities.Loan;
import entities.LoanDetail;
import entities.Reader;

import java.time.LocalDate;
import java.util.List;

public class LoanService {
    private LoanDAO loanDAO;
    private BookService bookService;
    private ReaderService readerService;

    public LoanService() {
        this.loanDAO = new LoanDAO();
        this.bookService = new BookService();
        this.readerService = new ReaderService();
    }

    public List<Loan> getAllLoans() {
        return loanDAO.getAllLoans();
    }

    public List<Loan> getLoansByReaderID(String readerID) {
        return loanDAO.getLoansByReaderID(readerID);
    }

    public Loan getLoanByID(int loanID) {
        return loanDAO.getLoanByID(loanID);
    }

    public int createLoan(Loan loan) {
        // Validate loan data
        if (loan.getReaderID() == null || loan.getReaderID().trim().isEmpty()) {
            return -1;
        }
        if (loan.getLoanDate() == null) {
            loan.setLoanDate(LocalDate.now());
        }
        if (loan.getExpectedReturnDate() == null) {
            // By default, the expected return date is 7 days after the loan date
            loan.setExpectedReturnDate(loan.getLoanDate().plusDays(7));
        }
        if (loan.getLoanDetails() == null || loan.getLoanDetails().isEmpty()) {
            return -1;
        }

        // Check if reader exists
        Reader reader = readerService.getReaderByID(loan.getReaderID());
        if (reader == null) {
            return -1;
        }

        // Check if reader's card is expired
        if (reader.getExpirationDate().isBefore(LocalDate.now())) {
            return -1;
        }

        // Check if books are available
        for (LoanDetail detail : loan.getLoanDetails()) {
            if (!bookService.isBookAvailable(detail.getISBN())) {
                return -1;
            }
        }

        // Create loan
        int loanID = loanDAO.createLoan(loan);
        if (loanID > 0) {
            // Decrease book quantities
            for (LoanDetail detail : loan.getLoanDetails()) {
                bookService.decreaseBookQuantity(detail.getISBN());
            }
        }

        return loanID;
    }

    public boolean returnLoan(int loanID, List<String> returnedISBNs, List<String> lostISBNs) {
        Loan loan = loanDAO.getLoanByID(loanID);
        if (loan == null) {
            return false;
        }

        // Set actual return date if not already set
        if (loan.getActualReturnDate() == null) {
            loan.setActualReturnDate(LocalDate.now());
            loanDAO.updateLoan(loan);
        }

        // Process returned and lost books
        boolean success = true;
        for (LoanDetail detail : loan.getLoanDetails()) {
            if (returnedISBNs.contains(detail.getISBN())) {
                detail.markAsReturned();

                // Calculate fine if overdue
                if (loan.isOverdue()) {
                    detail.setFine(loan.calculateOverdueFine());
                }

                success &= loanDAO.updateLoanDetail(detail);

                // Increase book quantity
                bookService.increaseBookQuantity(detail.getISBN());
            } else if (lostISBNs.contains(detail.getISBN())) {
                detail.markAsLost();
                success &= loanDAO.updateLoanDetail(detail);
                // Lost books don't increase quantity
            }
        }

        return success;
    }

    public double calculateTotalFine(int loanID) {
        Loan loan = loanDAO.getLoanByID(loanID);
        if (loan == null) {
            return 0.0;
        }

        double totalFine = 0.0;
        for (LoanDetail detail : loan.getLoanDetails()) {
            totalFine += detail.getFine();
        }

        return totalFine;
    }

    public List<Loan> getOverdueLoans() {
        return loanDAO.getOverdueLoans();
    }

    public boolean isReaderHasOverdueLoans(String readerID) {
        List<Loan> loans = loanDAO.getLoansByReaderID(readerID);
        for (Loan loan : loans) {
            if (loan.isOverdue() && !loan.isReturned()) {
                return true;
            }
        }
        return false;
    }

    public int getBorrowedBooksCount() {
        return bookService.getBorrowedBooksCount();
    }
}