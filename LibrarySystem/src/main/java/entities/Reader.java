package entities;

import java.time.LocalDate;

public class Reader {
    private String readerID;
    private String fullName;
    private String identityCard;
    private LocalDate dateOfBirth;
    private String gender;
    private String email;
    private String address;
    private LocalDate registrationDate;
    private LocalDate expirationDate;

    // Constructors
    public Reader() {
    }

    public Reader(String readerID, String fullName, String identityCard, LocalDate dateOfBirth,
                  String gender, String email, String address, LocalDate registrationDate) {
        this.readerID = readerID;
        this.fullName = fullName;
        this.identityCard = identityCard;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.registrationDate = registrationDate;
        // Expiration date is 48 months (4 years) from registration date
        this.expirationDate = registrationDate.plusMonths(48);
    }

    // Getters and Setters
    public String getReaderID() {
        return readerID;
    }

    public void setReaderID(String readerID) {
        this.readerID = readerID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
        // Update expiration date when registration date changes
        this.expirationDate = registrationDate.plusMonths(48);
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "Reader [ID=" + readerID + ", Name=" + fullName + ", ID Card=" + identityCard + "]";
    }
}