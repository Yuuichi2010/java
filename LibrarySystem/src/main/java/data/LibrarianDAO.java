package data;

import entities.Librarian;
import utils.PasswordUtils;

import java.sql.*;

public class LibrarianDAO {
    private Connection connection;

    public LibrarianDAO() {
        connection = DBConnection.getInstance().getConnection();
    }

    public boolean createLibrarian(Librarian librarian) {
        String query = "INSERT INTO Librarian (username, password, fullName, email) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, librarian.getUsername());

            // Hash the password before storing
            String hashedPassword = PasswordUtils.hashPassword(librarian.getPassword());
            pstmt.setString(2, hashedPassword);

            pstmt.setString(3, librarian.getFullName());
            pstmt.setString(4, librarian.getEmail());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Librarian getLibrarianByUsername(String username) {
        String query = "SELECT * FROM Librarian WHERE username = ?";
        Librarian librarian = null;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    librarian = new Librarian();
                    librarian.setUsername(rs.getString("username"));
                    librarian.setPassword(rs.getString("password"));
                    librarian.setFullName(rs.getString("fullName"));
                    librarian.setEmail(rs.getString("email"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return librarian;
    }

    public boolean checkCredentials(String username, String password) {
        String query = "SELECT password FROM Librarian WHERE username = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String storedHashedPassword = rs.getString("password");
                    return PasswordUtils.verifyPassword(password, storedHashedPassword);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateLibrarian(Librarian librarian) {
        String query = "UPDATE Librarian SET fullName = ?, email = ? WHERE username = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, librarian.getFullName());
            pstmt.setString(2, librarian.getEmail());
            pstmt.setString(3, librarian.getUsername());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateLibrarianPassword(String username, String newPassword) {
        String query = "UPDATE Librarian SET password = ? WHERE username = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            // Hash the new password before storing
            String hashedPassword = PasswordUtils.hashPassword(newPassword);
            pstmt.setString(1, hashedPassword);
            pstmt.setString(2, username);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean exists(String username) {
        String query = "SELECT COUNT(*) FROM Librarian WHERE username = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);

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
}