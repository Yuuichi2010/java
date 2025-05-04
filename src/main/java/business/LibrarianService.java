package business;

import data.LibrarianDAO;
import entities.Librarian;

public class LibrarianService {
    private LibrarianDAO librarianDAO;

    public LibrarianService() {
        this.librarianDAO = new LibrarianDAO();
    }

    public boolean registerLibrarian(Librarian librarian) {
        // Validate librarian data
        if (librarian.getUsername() == null || librarian.getUsername().trim().isEmpty()) {
            return false;
        }
        if (librarian.getPassword() == null || librarian.getPassword().trim().isEmpty()) {
            return false;
        }
        if (librarian.getFullName() == null || librarian.getFullName().trim().isEmpty()) {
            return false;
        }

        // Check if username already exists
        if (librarianDAO.exists(librarian.getUsername())) {
            return false;
        }

        return librarianDAO.createLibrarian(librarian);
    }

    public boolean authenticate(String username, String password) {
        // Validate input
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        if (password == null || password.trim().isEmpty()) {
            return false;
        }

        return librarianDAO.checkCredentials(username, password);
    }

    public Librarian getLibrarianByUsername(String username) {
        return librarianDAO.getLibrarianByUsername(username);
    }

    public boolean updateLibrarian(Librarian librarian) {
        // Validate librarian data
        if (librarian.getUsername() == null || librarian.getUsername().trim().isEmpty()) {
            return false;
        }
        if (librarian.getFullName() == null || librarian.getFullName().trim().isEmpty()) {
            return false;
        }

        return librarianDAO.updateLibrarian(librarian);
    }

    public boolean changePassword(String username, String oldPassword, String newPassword) {
        // Validate input
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        if (oldPassword == null || oldPassword.trim().isEmpty()) {
            return false;
        }
        if (newPassword == null || newPassword.trim().isEmpty()) {
            return false;
        }

        // Verify old password
        if (!authenticate(username, oldPassword)) {
            return false;
        }

        return librarianDAO.updateLibrarianPassword(username, newPassword);
    }
}