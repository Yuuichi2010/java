package presentation.models;

import entities.Reader;
import utils.DateUtils;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ReaderTableModel extends AbstractTableModel {
    private List<Reader> readers;
    private final String[] columnNames = {"ID", "Full Name", "ID Card", "Gender", "Date of Birth", "Email", "Registration Date", "Expiration Date"};

    public ReaderTableModel() {
        this.readers = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return readers.size();
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
        return String.class;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Reader reader = readers.get(rowIndex);

        switch (columnIndex) {
            case 0: return reader.getReaderID();
            case 1: return reader.getFullName();
            case 2: return reader.getIdentityCard();
            case 3: return reader.getGender();
            case 4: return reader.getDateOfBirth() != null ? DateUtils.formatDisplayDate(reader.getDateOfBirth()) : "";
            case 5: return reader.getEmail();
            case 6: return reader.getRegistrationDate() != null ? DateUtils.formatDisplayDate(reader.getRegistrationDate()) : "";
            case 7: return reader.getExpirationDate() != null ? DateUtils.formatDisplayDate(reader.getExpirationDate()) : "";
            default: return null;
        }
    }

    public void setReaders(List<Reader> readers) {
        this.readers = readers;
    }

    public Reader getReaderAt(int rowIndex) {
        return readers.get(rowIndex);
    }
}