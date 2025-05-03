package data;

import entities.Book;
import entities.Loan;
import entities.LoanDetail;
import entities.Reader;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {
    private Connection connection;
    private BookDAO bookDAO;
    private ReaderDAO readerDAO;

    public LoanDAO() {
        connection = DBConnection.getInstance().getConnection();
        bookDAO = new BookDAO();
        readerDAO = new ReaderDAO();
    }

    public List<Loan> getAllLoans() {
        List<Loan> loans = new ArrayList<>();
        String query = "SELECT * FROM LoanRecord";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Loan loan = extractLoanFromResultSet(rs);
                loadLoanDetails(loan);
                loans.add(loan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loans;
    }

    public List<Loan> getLoansByReaderID(String readerID) {
        List<Loan> loans = new ArrayList<>();
        String query = "SELECT * FROM LoanRecord WHERE readerID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, readerID);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Loan loan = extractLoanFromResultSet(rs);
                    loadLoanDetails(loan);
                    loans.add(loan);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loans;
    }

    public Loan getLoanByID(int loanID) {
        String query = "SELECT * FROM LoanRecord WHERE loanID = ?";
        Loan loan = null;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, loanID);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    loan = extractLoanFromResultSet(rs);
                    loadLoanDetails(loan);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loan;
    }

    public int createLoan(Loan loan) {
        String query = "INSERT INTO LoanRecord (readerID, loanDate, expectedReturnDate) VALUES (?, ?, ?)";
        int generatedLoanID = -1;

        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, loan.getReaderID());
            pstmt.setDate(2, Date.valueOf(loan.getLoanDate()));
            pstmt.setDate(3, Date.valueOf(loan.getExpectedReturnDate()));

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedLoanID = generatedKeys.getInt(1);
                        loan.setLoanID(generatedLoanID);

                        // Insert loan details
                        for (LoanDetail detail : loan.getLoanDetails()) {
                            detail.setLoanID(generatedLoanID);
                            createLoanDetail(detail);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return generatedLoanID;
    }

    public boolean createLoanDetail(LoanDetail loanDetail) {
        String query = "INSERT INTO LoanDetail (loanID, ISBN, status) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, loanDetail.getLoanID());
            pstmt.setString(2, loanDetail.getISBN());
            pstmt.setString(3, loanDetail.getStatus());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        loanDetail.setLoanDetailID(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateLoan(Loan loan) {
        String query = "UPDATE LoanRecord SET actualReturnDate = ? WHERE loanID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            if (loan.getActualReturnDate() != null) {
                pstmt.setDate(1, Date.valueOf(loan.getActualReturnDate()));
            } else {
                pstmt.setNull(1, Types.DATE);
            }
            pstmt.setInt(2, loan.getLoanID());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateLoanDetail(LoanDetail loanDetail) {
        String query = "UPDATE LoanDetail SET status = ?, fine = ? WHERE loanDetailID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, loanDetail.getStatus());
            pstmt.setDouble(2, loanDetail.getFine());
            pstmt.setInt(3, loanDetail.getLoanDetailID());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Loan> getOverdueLoans() {
        List<Loan> overdueLoans = new ArrayList<>();
        String query = "SELECT * FROM LoanRecord WHERE expectedReturnDate < CURRENT_DATE AND (actualReturnDate IS NULL OR actualReturnDate > expectedReturnDate)";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Loan loan = extractLoanFromResultSet(rs);
                loadLoanDetails(loan);
                overdueLoans.add(loan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return overdueLoans;
    }

    private Loan extractLoanFromResultSet(ResultSet rs) throws SQLException {
        int loanID = rs.getInt("loanID");
        String readerID = rs.getString("readerID");
        LocalDate loanDate = rs.getDate("loanDate").toLocalDate();
        LocalDate expectedReturnDate = rs.getDate("expectedReturnDate").toLocalDate();

        Date actualReturnDateSQL = rs.getDate("actualReturnDate");
        LocalDate actualReturnDate = (actualReturnDateSQL != null) ? actualReturnDateSQL.toLocalDate() : null;

        Loan loan = new Loan(loanID, readerID, loanDate, expectedReturnDate, actualReturnDate);

        // Load reader information
        Reader reader = readerDAO.getReaderByID(readerID);
        loan.setReader(reader);

        return loan;
    }

    private void loadLoanDetails(Loan loan) {
        String query = "SELECT * FROM LoanDetail WHERE loanID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, loan.getLoanID());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int loanDetailID = rs.getInt("loanDetailID");
                    int loanID = rs.getInt("loanID");
                    String ISBN = rs.getString("ISBN");
                    String status = rs.getString("status");
                    double fine = rs.getDouble("fine");

                    LoanDetail loanDetail = new LoanDetail(loanDetailID, loanID, ISBN, status, fine);

                    // Load book information
                    Book book = bookDAO.getBookByISBN(ISBN);
                    loanDetail.setBook(book);

                    loan.addLoanDetail(loanDetail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}