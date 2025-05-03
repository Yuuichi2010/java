package entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Loan {
    private int loanID;
    private String readerID;
    private LocalDate loanDate;
    private LocalDate expectedReturnDate;
    private LocalDate actualReturnDate;
    private List<LoanDetail> loanDetails;

    // For association with Reader
    private Reader reader;

    // Constructors
    public Loan() {
        this.loanDetails = new ArrayList<>();
    }

    public Loan(String readerID, LocalDate loanDate) {
        this.readerID = readerID;
        this.loanDate = loanDate;
        // By default, the expected return date is 7 days after the loan date
        this.expectedReturnDate = loanDate.plusDays(7);
        this.loanDetails = new ArrayList<>();
    }

    public Loan(int loanID, String readerID, LocalDate loanDate, LocalDate expectedReturnDate, LocalDate actualReturnDate) {
        this.loanID = loanID;
        this.readerID = readerID;
        this.loanDate = loanDate;
        this.expectedReturnDate = expectedReturnDate;
        this.actualReturnDate = actualReturnDate;
        this.loanDetails = new ArrayList<>();
    }

    // Getters and Setters
    public int getLoanID() {
        return loanID;
    }

    public void setLoanID(int loanID) {
        this.loanID = loanID;
    }

    public String getReaderID() {
        return readerID;
    }

    public void setReaderID(String readerID) {
        this.readerID = readerID;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public void setExpectedReturnDate(LocalDate expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
    }

    public LocalDate getActualReturnDate() {
        return actualReturnDate;
    }

    public void setActualReturnDate(LocalDate actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    public List<LoanDetail> getLoanDetails() {
        return loanDetails;
    }

    public void setLoanDetails(List<LoanDetail> loanDetails) {
        this.loanDetails = loanDetails;
    }

    public void addLoanDetail(LoanDetail loanDetail) {
        this.loanDetails.add(loanDetail);
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    // Business methods
    public boolean isReturned() {
        return actualReturnDate != null;
    }

    public boolean isOverdue() {
        if (isReturned()) {
            return actualReturnDate.isAfter(expectedReturnDate);
        } else {
            return LocalDate.now().isAfter(expectedReturnDate);
        }
    }

    public int getOverdueDays() {
        if (!isOverdue()) {
            return 0;
        }

        if (isReturned()) {
            return (int) (actualReturnDate.toEpochDay() - expectedReturnDate.toEpochDay());
        } else {
            return (int) (LocalDate.now().toEpochDay() - expectedReturnDate.toEpochDay());
        }
    }

    public double calculateOverdueFine() {
        final double DAILY_FINE = 5000.0; // 5,000 VND per day
        return getOverdueDays() * DAILY_FINE;
    }

    @Override
    public String toString() {
        return "Loan [ID=" + loanID + ", Reader=" + readerID + ", Date=" + loanDate + "]";
    }
}