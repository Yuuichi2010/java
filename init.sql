-- Xóa database nếu tồn tại
DROP DATABASE IF EXISTS library_db;
CREATE DATABASE library_db;
USE library_db;

-- Bảng quản lý người dùng chung
CREATE TABLE user (
    id BINARY(16) DEFAULT (UUID_TO_BIN(UUID())) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL,
    email NVARCHAR(255) NOT NULL UNIQUE,
    password NVARCHAR(255) NOT NULL,
    dob DATE NOT NULL,
    gender ENUM('Male', 'Female', 'Other') NOT NULL,
    address NVARCHAR(255) NOT NULL,
    isEnabled BOOLEAN DEFAULT TRUE,
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP(),
    lastLogin DATETIME DEFAULT CURRENT_TIMESTAMP()
);

-- Bảng thủ thư (librarian)
CREATE TABLE librarian (
    id BINARY(16) DEFAULT (UUID_TO_BIN(UUID())) PRIMARY KEY,
    userID BINARY(16) NOT NULL,
    FOREIGN KEY (userID) REFERENCES user(id) ON DELETE CASCADE
);

-- Bảng độc giả (reader)
CREATE TABLE reader (
    id BINARY(16) DEFAULT (UUID_TO_BIN(UUID())) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL,
    cmnd_cccd NVARCHAR(20) NOT NULL UNIQUE,
    dob DATE NOT NULL,
    gender ENUM('Male', 'Female', 'Other') NOT NULL,
    email NVARCHAR(255) NOT NULL UNIQUE,
    address NVARCHAR(255) NOT NULL,
    cardIssuedDate DATE NOT NULL DEFAULT (CURRENT_DATE),
    cardExpiryDate DATE NOT NULL DEFAULT (DATE_ADD(CURRENT_DATE, INTERVAL 48 MONTH)),
    isEnabled BOOLEAN DEFAULT TRUE
);

-- Bảng tác giả (author)
CREATE TABLE author (
    id BINARY(16) DEFAULT (UUID_TO_BIN(UUID())) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL,
    isEnabled BOOLEAN DEFAULT TRUE
);

-- Bảng nhà xuất bản (publisher)
CREATE TABLE publisher (
    id BINARY(16) DEFAULT (UUID_TO_BIN(UUID())) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL,
    isEnabled BOOLEAN DEFAULT TRUE
);

-- Bảng thể loại sách (category)
CREATE TABLE category (
    id BINARY(16) DEFAULT (UUID_TO_BIN(UUID())) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL,
    isEnabled BOOLEAN DEFAULT TRUE
);

-- Bảng sách (book)
CREATE TABLE book (
    isbn VARCHAR(13) NOT NULL PRIMARY KEY,
    title NVARCHAR(255) NOT NULL,
    publisherID BINARY(16) NOT NULL,
    authorID BINARY(16) NOT NULL,
    publishingDate DATE NOT NULL,
    language NVARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL DEFAULT 0.0,
    quantity INT NOT NULL DEFAULT 0,
    isEnabled BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (publisherID) REFERENCES publisher(id),
    FOREIGN KEY (authorID) REFERENCES author(id)
);

-- Liên kết sách với thể loại (bookCategory)
CREATE TABLE bookCategory (
    bookID VARCHAR(13) NOT NULL,
    categoryID BINARY(16) NOT NULL,
    PRIMARY KEY (bookID, categoryID),
    FOREIGN KEY (bookID) REFERENCES book(isbn),
    FOREIGN KEY (categoryID) REFERENCES category(id)
);

-- Bảng quản lý phiếu mượn sách (borrowCard)
CREATE TABLE borrowCard (
    id BINARY(16) DEFAULT (UUID_TO_BIN(UUID())) PRIMARY KEY,
    readerID BINARY(16) NOT NULL,
    librarianID BINARY(16) NOT NULL,
    borrowDate DATE NOT NULL DEFAULT (CURRENT_DATE),
    expectedReturnDate DATE NOT NULL,
    actualReturnDate DATE,
    FOREIGN KEY (readerID) REFERENCES reader(id),
    FOREIGN KEY (librarianID) REFERENCES librarian(id)
);

-- Bảng chi tiết phiếu mượn (borrowDetails)
CREATE TABLE borrowDetails (
    borrowID BINARY(16) NOT NULL,
    bookID VARCHAR(13) NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    fine DECIMAL(10, 2) NOT NULL DEFAULT 0.0,
    PRIMARY KEY (borrowID, bookID),
    FOREIGN KEY (borrowID) REFERENCES borrowCard(id),
    FOREIGN KEY (bookID) REFERENCES book(isbn)
);
-- Bảng quản lý ảnh sách (bookImage)
CREATE TABLE bookImage (
    id BINARY(16) DEFAULT (UUID_TO_BIN(UUID())) PRIMARY KEY,
    bookID VARCHAR(13) NOT NULL,
    url NVARCHAR(255) NOT NULL,
    FOREIGN KEY (bookID) REFERENCES book(isbn)
);

-- Bảng quản lý phiếu trả sách (returnCard)
CREATE TABLE returnCard (
    id BINARY(16) DEFAULT (UUID_TO_BIN(UUID())) PRIMARY KEY,
    borrowID BINARY(16) NOT NULL,
    readerID BINARY(16) NOT NULL,
    librarianID BINARY(16) NOT NULL,
    returnDate DATE NOT NULL DEFAULT (CURRENT_DATE),
    FOREIGN KEY (borrowID) REFERENCES borrowCard(id),
    FOREIGN KEY (readerID) REFERENCES reader(id),
    FOREIGN KEY (librarianID) REFERENCES librarian(id)
);