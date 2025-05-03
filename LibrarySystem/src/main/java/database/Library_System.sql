-- Create database
CREATE DATABASE IF NOT EXISTS Library_System;
USE Library_System;

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

-- Insert default librarian
INSERT INTO Librarian (username, password, fullName, email) 
VALUES ('admin', 'YWRtaW5zYWx0OkFTRGZnaDEyMzo=:1G9VX8O/JVeaHd+Shx+mAEMi9uJa4MdQAkflKC1S7ew=', 'System Administrator', 'admin@library.com');

-- Insert sample data for demonstration

-- Sample readers
INSERT INTO Reader (readerID, fullName, identityCard, dateOfBirth, gender, email, address, registrationDate, expirationDate)
VALUES
('R001', 'Nguyen Van A', '001234567890', '1990-05-15', 'Male', 'nguyenvana@email.com', '123 ABC Street, District 1, HCMC', '2023-01-10', '2027-01-10'),
('R002', 'Tran Thi B', '002345678901', '1995-08-22', 'Female', 'tranthib@email.com', '456 XYZ Street, District 2, HCMC', '2023-02-15', '2027-02-15'),
('R003', 'Le Van C', '003456789012', '1988-12-03', 'Male', 'levanc@email.com', '789 DEF Street, District 3, HCMC', '2023-03-20', '2027-03-20'),
('R004', 'Pham Thi D', '004567890123', '1992-04-18', 'Female', 'phamthid@email.com', '101 GHI Street, District 4, HCMC', '2023-04-25', '2027-04-25'),
('R005', 'Hoang Van E', '005678901234', '1985-11-30', 'Male', 'hoangvane@email.com', '202 JKL Street, District 5, HCMC', '2023-05-30', '2027-05-30');

-- Sample books
INSERT INTO Book (ISBN, title, author, publisher, publishYear, genre, price, quantity)
VALUES
('978-1-234-56789-0', 'Introduction to Java Programming', 'John Smith', 'Tech Publishing', 2022, 'Education', 350000, 10),
('978-2-345-67890-1', 'Data Structures and Algorithms', 'Jane Johnson', 'Academic Press', 2021, 'Education', 400000, 8),
('978-3-456-78901-2', 'The History of Vietnam', 'Nguyen Van X', 'Vietnam Publishing House', 2020, 'History', 250000, 15),
('978-4-567-89012-3', 'Modern Web Development', 'Michael Brown', 'Web Tech Books', 2023, 'Technology', 380000, 12),
('978-5-678-90123-4', 'Artificial Intelligence Basics', 'Sarah Davis', 'Future Press', 2022, 'Technology', 420000, 7),
('978-6-789-01234-5', 'Vietnamese Cuisine', 'Tran Thi Y', 'Food Publishers', 2021, 'Cooking', 300000, 20),
('978-7-890-12345-6', 'Business Management', 'Robert Wilson', 'Business Books', 2020, 'Business', 320000, 9),
('978-8-901-23456-7', 'English Grammar', 'Emily Thompson', 'Language Learning', 2022, 'Education', 280000, 25),
('978-9-012-34567-8', 'Digital Marketing Strategies', 'Daniel Lee', 'Marketing Press', 2023, 'Business', 360000, 6),
('978-0-123-45678-9', 'Vietnamese Literature Anthology', 'Le Thi Z', 'Literature House', 2021, 'Fiction', 270000, 18),
('978-0-765-43210-9', 'Advanced Programming Techniques', 'Robert Martin', 'Clean Code Publishers', 2022, 'Education', 425000, 12),
('978-0-654-32109-8', 'Đắc Nhân Tâm', 'Dale Carnegie', 'First News', 2019, 'Self-Help', 120000, 30),
('978-0-543-21098-7', 'Mathematics for Computer Science', 'Eric Lehman', 'MIT Press', 2021, 'Education', 390000, 15),
('978-0-432-10987-6', 'Introduction to Algorithms', 'Thomas H. Cormen', 'MIT Press', 2020, 'Education', 450000, 10),
('978-0-321-09876-5', 'Clean Code', 'Robert C. Martin', 'Prentice Hall', 2019, 'Technology', 395000, 12),
('978-0-210-98765-4', 'The Art of Computer Programming', 'Donald E. Knuth', 'Addison-Wesley', 2018, 'Education', 520000, 8),
('978-0-109-87654-3', 'Design Patterns', 'Erich Gamma', 'Addison-Wesley', 2021, 'Technology', 380000, 10),
('978-1-098-76543-2', 'Nhà Giả Kim', 'Paulo Coelho', 'NXB Hội Nhà Văn', 2020, 'Fiction', 85000, 25),
('978-2-987-65432-1', 'Dế Mèn Phiêu Lưu Ký', 'Tô Hoài', 'NXB Kim Đồng', 2019, 'Fiction', 75000, 28);

-- Sample loans
INSERT INTO LoanRecord (readerID, loanDate, expectedReturnDate, actualReturnDate)
VALUES
('R001', '2023-06-10', '2023-06-17', '2023-06-16'),
('R002', '2023-06-12', '2023-06-19', '2023-06-20'),
('R003', '2023-06-15', '2023-06-22', NULL),
('R001', '2023-06-20', '2023-06-27', '2023-06-25'),
('R004', '2023-06-22', '2023-06-29', NULL);

-- Sample loan details
INSERT INTO LoanDetail (loanID, ISBN, status, fine)
VALUES
(1, '978-1-234-56789-0', 'Returned', 0),
(1, '978-3-456-78901-2', 'Returned', 0),
(2, '978-6-789-01234-5', 'Returned', 5000),
(3, '978-5-678-90123-4', 'Borrowed', 0),
(3, '978-7-890-12345-6', 'Borrowed', 0),
(4, '978-9-012-34567-8', 'Returned', 0),
(5, '978-2-345-67890-1', 'Borrowed', 0),
(5, '978-8-901-23456-7', 'Borrowed', 0),
(5, '978-0-123-45678-9', 'Borrowed', 0);