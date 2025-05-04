-- Create database
CREATE DATABASE IF NOT EXISTS Library_Management_System;
USE Library_Management_System;

-- Create tables
CREATE TABLE IF NOT EXISTS Librarian (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    fullName VARCHAR(100) NOT NULL,
    email VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS Reader (
    readerID VARCHAR(20) PRIMARY KEY,
    fullName NVARCHAR(100) NOT NULL,
    identityCard VARCHAR(20) NOT NULL UNIQUE,
    dateOfBirth DATE NOT NULL,
    gender VARCHAR(10) NOT NULL,
    email VARCHAR(100),
    address NVARCHAR(255),
    registrationDate DATE NOT NULL,
    expirationDate DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS Book (
    ISBN VARCHAR(20) PRIMARY KEY,
    title NVARCHAR(255) NOT NULL,
    author NVARCHAR(100) NOT NULL,
    publisher NVARCHAR(100) NOT NULL,
    publishYear INT NOT NULL,
    genre NVARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL
);

CREATE TABLE IF NOT EXISTS LoanRecord (
    loanID INT AUTO_INCREMENT PRIMARY KEY,
    readerID VARCHAR(20) NOT NULL,
    loanDate DATE NOT NULL,
    expectedReturnDate DATE NOT NULL,
    actualReturnDate DATE,
    FOREIGN KEY (readerID) REFERENCES Reader(readerID)
);

CREATE TABLE IF NOT EXISTS LoanDetail (
    loanDetailID INT AUTO_INCREMENT PRIMARY KEY,
    loanID INT NOT NULL,
    ISBN VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'Borrowed', -- 'Borrowed', 'Returned', 'Lost'
    fine DECIMAL(10, 2) DEFAULT 0,
    FOREIGN KEY (loanID) REFERENCES LoanRecord(loanID),
    FOREIGN KEY (ISBN) REFERENCES Book(ISBN)
);