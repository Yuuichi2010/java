package presentation.models;

import entities.Loan;
import entities.LoanDetail;
import utils.DateUtils;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class LoanTableModel extends AbstractTableModel {
    private List<Loan> loans;
    private final String[] columnNames = {"Loan ID", "Reader", "Loan Date", "Expected Return", "Status", "Books", "Fine"};

    public LoanTableModel() {
        this.loans = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return loans.size();
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
        if (columnIndex == 6) { // Fine
            return Double.class;
        }
        return String.class;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Loan loan = loans.get(rowIndex);

        switch (columnIndex) {
            case 0: return loan.getLoanID();
            case 1:
                // Hiển thị tên độc giả nếu có
                if (loan.getReader() != null) {
                    return loan.getReader().getFullName();
                } else {
                    return loan.getReaderID();
                }
            case 2: return DateUtils.formatDisplayDate(loan.getLoanDate());
            case 3: return DateUtils.formatDisplayDate(loan.getExpectedReturnDate());
            case 4:
                if (loan.isReturned()) {
                    return "Returned";
                } else if (loan.isOverdue()) {
                    return "Overdue";
                } else {
                    return "Active";
                }
            case 5:
                // Đếm số sách được mượn
                if (loan.getLoanDetails() != null) {
                    return loan.getLoanDetails().size() + " book(s)";
                } else {
                    return "0 book(s)";
                }
            case 6:
                // Tính tổng tiền phạt
                double totalFine = 0.0;

                // Phạt trễ hạn
                if (loan.isOverdue()) {
                    if (loan.isReturned()) {
                        // Tính số ngày trễ từ dự kiến đến thực tế
                        long daysLate = ChronoUnit.DAYS.between(
                                loan.getExpectedReturnDate(),
                                loan.getActualReturnDate()
                        );
                        totalFine += Math.max(0, daysLate) * 5000.0; // 5,000 VND/ngày
                    } else {
                        // Tính số ngày trễ từ dự kiến đến hiện tại
                        long daysLate = ChronoUnit.DAYS.between(
                                loan.getExpectedReturnDate(),
                                LocalDate.now()
                        );
                        totalFine += Math.max(0, daysLate) * 5000.0; // 5,000 VND/ngày
                    }
                }

                // Cộng thêm tiền phạt mất sách
                if (loan.getLoanDetails() != null) {
                    for (LoanDetail detail : loan.getLoanDetails()) {
                        if (detail.isLost() && detail.getBook() != null) {
                            totalFine += detail.getBook().getPrice() * 2.0; // 200% giá sách
                        }
                    }
                }

                return totalFine;
            default: return null;
        }
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    public Loan getLoanAt(int rowIndex) {
        return loans.get(rowIndex);
    }
}