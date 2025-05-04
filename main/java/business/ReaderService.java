package business;

import data.ReaderDAO;
import entities.Reader;

import java.time.LocalDate;
import java.util.List;

public class ReaderService {
    private ReaderDAO readerDAO;

    public ReaderService() {
        this.readerDAO = new ReaderDAO();
    }

    public List<Reader> getAllReaders() {
        return readerDAO.getAllReaders();
    }

    public Reader getReaderByID(String readerID) {
        return readerDAO.getReaderByID(readerID);
    }

    public Reader getReaderByIdCard(String idCard) {
        return readerDAO.getReaderByIdCard(idCard);
    }

    public List<Reader> searchReadersByName(String name) {
        return readerDAO.searchReadersByName(name);
    }

    public boolean addReader(Reader reader) {
        // Validate reader data
        if (!validateReaderData(reader)) {
            return false;
        }

        // Check if reader with same ID card already exists
        Reader existingReader = readerDAO.getReaderByIdCard(reader.getIdentityCard());
        if (existingReader != null) {
            return false;
        }

        return readerDAO.addReader(reader);
    }

    public boolean updateReader(Reader reader) {
        // Validate reader data
        if (!validateReaderData(reader)) {
            return false;
        }

        // Check if reader exists
        Reader existingReader = readerDAO.getReaderByID(reader.getReaderID());
        if (existingReader == null) {
            return false;
        }

        // Check if ID card is being changed and if the new ID card already exists for another reader
        if (!existingReader.getIdentityCard().equals(reader.getIdentityCard())) {
            Reader readerWithSameIdCard = readerDAO.getReaderByIdCard(reader.getIdentityCard());
            if (readerWithSameIdCard != null && !readerWithSameIdCard.getReaderID().equals(reader.getReaderID())) {
                return false;
            }
        }

        return readerDAO.updateReader(reader);
    }

    public boolean deleteReader(String readerID) {
        // Check if reader exists
        Reader existingReader = readerDAO.getReaderByID(readerID);
        if (existingReader == null) {
            return false;
        }

        return readerDAO.deleteReader(readerID);
    }

    public int getTotalReaders() {
        return readerDAO.getTotalReaders();
    }

    public List<Object[]> getReaderCountByGender() {
        return readerDAO.getReaderCountByGender();
    }

    private boolean validateReaderData(Reader reader) {
        // Check for null values
        if (reader.getReaderID() == null || reader.getReaderID().trim().isEmpty()) {
            return false;
        }
        if (reader.getFullName() == null || reader.getFullName().trim().isEmpty()) {
            return false;
        }
        if (reader.getIdentityCard() == null || reader.getIdentityCard().trim().isEmpty()) {
            return false;
        }
        if (reader.getDateOfBirth() == null) {
            return false;
        }
        if (reader.getGender() == null || reader.getGender().trim().isEmpty()) {
            return false;
        }
        if (reader.getRegistrationDate() == null) {
            return false;
        }
        if (reader.getExpirationDate() == null) {
            return false;
        }

        // Validate date of birth (must be in the past)
        if (reader.getDateOfBirth().isAfter(LocalDate.now())) {
            return false;
        }

        // Validate registration date (must not be in the future)
        if (reader.getRegistrationDate().isAfter(LocalDate.now())) {
            return false;
        }

        // Validate expiration date (must be after registration date)
        if (reader.getExpirationDate().isBefore(reader.getRegistrationDate())) {
            return false;
        }

        return true;
    }
}