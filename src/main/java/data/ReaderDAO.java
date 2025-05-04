package data;

import entities.Reader;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReaderDAO {
    private Connection connection;

    public ReaderDAO() {
        connection = DBConnection.getInstance().getConnection();
    }

    public List<Reader> getAllReaders() {
        List<Reader> readers = new ArrayList<>();
        String query = "SELECT * FROM Reader";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Reader reader = extractReaderFromResultSet(rs);
                readers.add(reader);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return readers;
    }

    public Reader getReaderByID(String readerID) {
        String query = "SELECT * FROM Reader WHERE readerID = ?";
        Reader reader = null;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, readerID);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    reader = extractReaderFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reader;
    }

    public Reader getReaderByIdCard(String idCard) {
        String query = "SELECT * FROM Reader WHERE identityCard = ?";
        Reader reader = null;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, idCard);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    reader = extractReaderFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reader;
    }

    public List<Reader> searchReadersByName(String name) {
        List<Reader> readers = new ArrayList<>();
        String query = "SELECT * FROM Reader WHERE fullName LIKE ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, "%" + name + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Reader reader = extractReaderFromResultSet(rs);
                    readers.add(reader);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return readers;
    }

    public boolean addReader(Reader reader) {
        String query = "INSERT INTO Reader (readerID, fullName, identityCard, dateOfBirth, gender, email, address, registrationDate, expirationDate) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, reader.getReaderID());
            pstmt.setString(2, reader.getFullName());
            pstmt.setString(3, reader.getIdentityCard());
            pstmt.setDate(4, Date.valueOf(reader.getDateOfBirth()));
            pstmt.setString(5, reader.getGender());
            pstmt.setString(6, reader.getEmail());
            pstmt.setString(7, reader.getAddress());
            pstmt.setDate(8, Date.valueOf(reader.getRegistrationDate()));
            pstmt.setDate(9, Date.valueOf(reader.getExpirationDate()));

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateReader(Reader reader) {
        String query = "UPDATE Reader SET fullName = ?, identityCard = ?, dateOfBirth = ?, gender = ?, " +
                "email = ?, address = ?, registrationDate = ?, expirationDate = ? WHERE readerID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, reader.getFullName());
            pstmt.setString(2, reader.getIdentityCard());
            pstmt.setDate(3, Date.valueOf(reader.getDateOfBirth()));
            pstmt.setString(4, reader.getGender());
            pstmt.setString(5, reader.getEmail());
            pstmt.setString(6, reader.getAddress());
            pstmt.setDate(7, Date.valueOf(reader.getRegistrationDate()));
            pstmt.setDate(8, Date.valueOf(reader.getExpirationDate()));
            pstmt.setString(9, reader.getReaderID());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteReader(String readerID) {
        // Check if reader has any active loans
        if (hasActiveLoans(readerID)) {
            return false;
        }

        String query = "DELETE FROM Reader WHERE readerID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, readerID);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean hasActiveLoans(String readerID) {
        String query = "SELECT COUNT(*) FROM LoanRecord WHERE readerID = ? AND actualReturnDate IS NULL";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, readerID);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public int getTotalReaders() {
        String query = "SELECT COUNT(*) as total FROM Reader";
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

    public List<Object[]> getReaderCountByGender() {
        List<Object[]> result = new ArrayList<>();
        String query = "SELECT gender, COUNT(*) as count FROM Reader GROUP BY gender";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Object[] row = new Object[2];
                row[0] = rs.getString("gender");
                row[1] = rs.getInt("count");
                result.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    private Reader extractReaderFromResultSet(ResultSet rs) throws SQLException {
        Reader reader = new Reader();
        reader.setReaderID(rs.getString("readerID"));
        reader.setFullName(rs.getString("fullName"));
        reader.setIdentityCard(rs.getString("identityCard"));
        reader.setDateOfBirth(rs.getDate("dateOfBirth").toLocalDate());
        reader.setGender(rs.getString("gender"));
        reader.setEmail(rs.getString("email"));
        reader.setAddress(rs.getString("address"));
        reader.setRegistrationDate(rs.getDate("registrationDate").toLocalDate());
        reader.setExpirationDate(rs.getDate("expirationDate").toLocalDate());
        return reader;
    }
}