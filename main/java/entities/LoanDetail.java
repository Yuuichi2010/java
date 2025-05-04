package entities;

public class LoanDetail {
    private int loanDetailID;
    private int loanID;
    private String ISBN;
    private String status; // "Borrowed", "Returned", "Lost"
    private double fine;

    // For association with Book
    private Book book;

    // Constructors
    public LoanDetail() {
    }

    public LoanDetail(int loanID, String ISBN) {
        this.loanID = loanID;
        this.ISBN = ISBN;
        this.status = "Borrowed";
        this.fine = 0.0;
    }

    public LoanDetail(int loanDetailID, int loanID, String ISBN, String status, double fine) {
        this.loanDetailID = loanDetailID;
        this.loanID = loanID;
        this.ISBN = ISBN;
        this.status = status;
        this.fine = fine;
    }

    // Getters and Setters
    public int getLoanDetailID() {
        return loanDetailID;
    }

    public void setLoanDetailID(int loanDetailID) {
        this.loanDetailID = loanDetailID;
    }

    public int getLoanID() {
        return loanID;
    }

    public void setLoanID(int loanID) {
        this.loanID = loanID;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    // Business methods
    public boolean isBorrowed() {
        return "Borrowed".equals(status);
    }

    public boolean isReturned() {
        return "Returned".equals(status);
    }

    public boolean isLost() {
        return "Lost".equals(status);
    }

    public void markAsReturned() {
        this.status = "Returned";
    }

    public void markAsLost() {
        this.status = "Lost";
        if (book != null) {
            // Fine for lost book is 200% of the book's price
            this.fine = book.getPrice() * 2.0;
        }
    }

    @Override
    public String toString() {
        return "LoanDetail [ID=" + loanDetailID + ", Book=" + ISBN + ", Status=" + status + "]";
    }
}