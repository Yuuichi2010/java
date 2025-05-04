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

-- Insert default librarian
INSERT INTO Librarian (username, password, fullName, email) 
VALUES ('admin', 'admin123', 'System Administrator', 'admin@library.com');

-- Insert sample data for demonstration

-- Sample readers
INSERT INTO Reader (readerID, fullName, identityCard, dateOfBirth, gender, email, address, registrationDate, expirationDate)
VALUES
('R001', 'Nguyen Van A', '001234567890', '1990-05-15', 'Male', 'nguyenvana@email.com', '123 ABC Street, District 1, HCMC', '2023-01-10', '2027-01-10'),
('R002', 'Tran Thi B', '002345678901', '1995-08-22', 'Female', 'tranthib@email.com', '456 XYZ Street, District 2, HCMC', '2023-02-15', '2027-02-15'),
('R003', 'Le Van C', '003456789012', '1988-12-03', 'Male', 'levanc@email.com', '789 DEF Street, District 3, HCMC', '2023-03-20', '2027-03-20'),
('R004', 'Pham Thi D', '004567890123', '1992-04-18', 'Female', 'phamthid@email.com', '101 GHI Street, District 4, HCMC', '2023-04-25', '2027-04-25'),
('R005', 'Hoang Van E', '005678901234', '1985-11-30', 'Male', 'hoangvane@email.com', '202 JKL Street, District 5, HCMC', '2023-05-30', '2027-05-30'),
('R006', 'Nguyen Thi F', '006789012345', '1996-07-10', 'Female', 'nguyenthif@email.com', '303 MNO Street, District 6, HCMC', '2023-06-05', '2027-06-05'),
('R007', 'Le Thi G', '007890123456', '1993-09-25', 'Female', 'lethig@email.com', '404 PQR Street, District 7, HCMC', '2023-07-01', '2027-07-01'),
('R008', 'Tran Thi H', '008901234567', '1994-02-18', 'Female', 'tranthih@email.com', '8A Le Lai, District 8, HCMC', '2023-07-10', '2027-07-10'),
('R009', 'Hoang Thi I', '009012345678', '1997-01-05', 'Female', 'hoangthii@email.com', '9B Tran Hung Dao, District 9, HCMC', '2023-07-12', '2027-07-12'),
('R010', 'Pham Thi J', '010123456789', '1998-04-20', 'Female', 'phamthij@email.com', '10 Nguyen Dinh Chieu, District 10, HCMC', '2023-07-15', '2027-07-15'),
('R011', 'Nguyen Thi K', '011234567890', '1991-05-30', 'Female', 'nguyenthik@email.com', '11 Pham Ngu Lao, District 11, HCMC', '2023-07-20', '2027-07-20'),
('R012', 'Le Thi L', '012345678901', '1989-08-15', 'Female', 'lethil@email.com', '12 Le Van Sy, District 12, HCMC', '2023-07-25', '2027-07-25'),
('R013', 'Tran Thi M', '013456789012', '1990-11-02', 'Female', 'tranthim@email.com', '13 Truong Chinh, Tan Binh District, HCMC', '2023-08-01', '2027-08-01'),
('R014', 'Hoang Thi N', '014567890123', '1987-03-09', 'Female', 'hoangthin@email.com', '14A Nguyen Van Troi, Phu Nhuan District, HCMC', '2023-08-05', '2027-08-05'),
('R015', 'Pham Thi O', '015678901234', '1992-12-28', 'Female', 'phamthio@email.com', '15B Cach Mang Thang 8, District 1, HCMC', '2023-08-10', '2027-08-10'),
('R016', 'Nguyen Thi P', '016789012345', '1986-07-30', 'Female', 'nguyenthip@email.com', '16C Hoang Van Thu, Tan Binh District, HCMC', '2023-08-15', '2027-08-15'),
('R017', 'Le Thi Q', '017890123456', '1993-06-12', 'Female', 'lethiq@email.com', '17A Dinh Tien Hoang, Binh Thanh District, HCMC', '2023-08-18', '2027-08-18'),
('R018', 'Tran Thi R', '018901234567', '1990-09-25', 'Female', 'tranthir@email.com', '18 Nguyen Thai Hoc, District 1, HCMC', '2023-08-20', '2027-08-20'),
('R019', 'Hoang Thi S', '019012345678', '1994-02-05', 'Female', 'hoangthis@email.com', '19 Ton Duc Thang, District 1, HCMC', '2023-08-25', '2027-08-25'),
('R020', 'Pham Thi T', '020123456789', '1992-10-17', 'Female', 'phamthit@email.com', '20 Nguyen Huu Canh, Binh Thanh District, HCMC', '2023-09-01', '2027-09-01'),
('R021', 'Nguyen Thi U', '021234567890', '1995-05-30', 'Female', 'nguyenthiu@email.com', '21 Truong Sa, District 3, HCMC', '2023-09-05', '2027-09-05'),
('R022', 'Le Thi V', '022345678901', '1991-11-11', 'Female', 'lethiv@email.com', '22 Hoang Hoa Tham, Tan Binh District, HCMC', '2023-09-10', '2027-09-10'),
('R023', 'Tran Thi W', '023456789012', '1996-08-20', 'Female', 'tranthiw@email.com', '23A Ba Thang Hai, District 10, HCMC', '2023-09-12', '2027-09-12'),
('R024', 'Hoang Thi X', '024567890123', '1993-04-14', 'Female', 'hoangthix@email.com', '24 Le Thanh Ton, District 1, HCMC', '2023-09-15', '2027-09-15'),
('R025', 'Pham Thi Y', '025678901234', '1989-03-27', 'Female', 'phamthiy@email.com', '25 Nguyen Thi Minh Khai, District 3, HCMC', '2023-09-18', '2027-09-18'),
('R026', 'Nguyen Thi Z', '026789012345', '1992-01-10', 'Female', 'nguyenthiz@email.com', '26 Tran Quang Khai, District 1, HCMC', '2023-09-20', '2027-09-20'),
('R027', 'Le Thi AA', '027890123456', '1997-07-25', 'Female', 'lethiaa@email.com', '27D Nguyen Duy Trinh, Thu Duc City, HCMC', '2023-09-22', '2027-09-22'),
('R028', 'Tran Thi BB', '028901234567', '1995-10-15', 'Female', 'tranthibb@email.com', '28E Vo Van Kiet, District 5, HCMC', '2023-09-25', '2027-09-25'),
('R029', 'Hoang Thi CC', '029012345678', '1993-02-05', 'Female', 'hoangthicc@email.com', '29F An Duong Vuong, District 6, HCMC', '2023-09-30', '2027-09-30'),
('R030', 'Pham Thi DD', '030123456789', '1988-11-09', 'Female', 'phamthidd@email.com', '30G Ly Thuong Kiet, District 10, HCMC', '2023-10-05', '2027-10-05'),
('R031', 'Nguyen Thi EE', '031234567890', '1990-06-18', 'Female', 'nguyenthee@email.com', '31H Truong Dinh, District 3, HCMC', '2023-10-10', '2027-10-10'),
('R032', 'Le Thi FF', '032345678901', '1996-03-22', 'Female', 'lethiff@email.com', '32I Nguyen Van Cu, District 5, HCMC', '2023-10-12', '2027-10-12'),
('R033', 'Tran Thi GG', '033456789012', '1994-04-05', 'Female', 'tranthigg@email.com', '33J Pham Van Dong, Thu Duc City, HCMC', '2023-10-15', '2027-10-15'),
('R034', 'Hoang Thi HH', '034567890123', '1991-05-17', 'Female', 'hoangthihh@email.com', '34K Tan Son Nhi, Tan Phu District, HCMC', '2023-10-18', '2027-10-18'),
('R035', 'Pham Thi II', '035678901234', '1992-12-03', 'Female', 'phamthiii@email.com', '35L Nguyen Thai Binh, District 1, HCMC', '2023-10-20', '2027-10-20'),
('R036', 'Nguyen Van JJ', '036789012345', '1990-01-12', 'Male', 'nguyenvanjj@email.com', '36A Nguyen Trai, District 1, HCMC', '2023-10-22', '2027-10-22'),
('R037', 'Le Van KK', '037890123456', '1985-02-18', 'Male', 'levankk@email.com', '37B Cach Mang Thang 8, District 3, HCMC', '2023-10-24', '2027-10-24'),
('R038', 'Tran Van LL', '038901234567', '1993-09-09', 'Male', 'tranvanll@email.com', '38C Nguyen Thi Minh Khai, District 3, HCMC', '2023-10-26', '2027-10-26'),
('R039', 'Hoang Van MM', '039012345678', '1987-07-07', 'Male', 'hoangvanmm@email.com', '39D Vo Van Tan, District 3, HCMC', '2023-10-28', '2027-10-28'),
('R040', 'Pham Van NN', '040123456789', '1994-04-04', 'Male', 'phamvannn@email.com', '40E Le Duan, District 1, HCMC', '2023-10-30', '2027-10-30'),
('R041', 'Nguyen Thi OO', '041234567890', '1991-08-08', 'Female', 'nguyenthioo@email.com', '41F Phan Xich Long, Phu Nhuan District, HCMC', '2023-11-01', '2027-11-01'),
('R042', 'Le Thi PP', '042345678901', '1996-12-12', 'Female', 'lethipp@email.com', '42G Dinh Tien Hoang, Binh Thanh District, HCMC', '2023-11-03', '2027-11-03'),
('R043', 'Tran Thi QQ', '043456789012', '1995-05-05', 'Female', 'tranthipp@email.com', '43H Tran Nao, Thu Duc City, HCMC', '2023-11-05', '2027-11-05'),
('R044', 'Hoang Thi RR', '044567890123', '1992-10-10', 'Female', 'hoangthirr@email.com', '44I Nguyen Van Dau, Binh Thanh District, HCMC', '2023-11-07', '2027-11-07'),
('R045', 'Pham Thi SS', '045678901234', '1990-03-03', 'Female', 'phamthiss@email.com', '45J Luy Ban Bich, Tan Phu District, HCMC', '2023-11-09', '2027-11-09'),
('R046', 'Doan Minh Tien', '046789012345', '1989-06-15', 'Male', 'doanminhtien@email.com', '12 Nguyen Trai, Thanh Xuan, Hanoi', '2023-11-11', '2027-11-11'),
('R047', 'Vo Thi Bich Thao', '047890123456', '1993-03-08', 'Female', 'bichthao.vo@email.com', '43 Le Loi, Hue City', '2023-11-13', '2027-11-13'),
('R048', 'Trinh Cong Son', '048901234567', '1985-09-15', 'Male', 'trinhson@email.com', '58 Tran Phu, Nha Trang, Khanh Hoa', '2023-11-15', '2027-11-15'),
('R049', 'Y Moan Eban', '049012345678', '1990-01-20', 'Male', 'ymoan.eban@email.com', 'Buon Ma Thuot, Dak Lak', '2023-11-17', '2027-11-17'),
('R050', 'Nguyen Tuan Anh', '050123456789', '1992-12-12', 'Male', 'tuananhnguyen@email.com', '77 Phan Dang Luu, Da Nang', '2023-11-19', '2027-11-19'),
('R051', 'Phan Thi Kim Cuc', '051234567890', '1991-04-22', 'Female', 'kimcuc.phan@email.com', '135 Cach Mang Thang 8, Can Tho', '2023-11-21', '2027-11-21'),
('R052', 'Miyuki Tanaka', '052345678901', '1994-07-10', 'Female', 'miyuki.tanaka@email.com', '3-12-5 Shibuya, Tokyo, Japan', '2023-11-23', '2027-11-23'),
('R053', 'Johnathan Smith', '053456789012', '1987-11-30', 'Male', 'john.smith@email.com', '221B Baker Street, London, UK', '2023-11-25', '2027-11-25'),
('R054', 'Nguyen Bao Chau', '054567890123', '1998-02-14', 'Female', 'chau.nguyen@email.com', '91 Nguyen Van Linh, District 7, HCMC', '2023-11-27', '2027-11-27'),
('R055', 'Lam Gia Huy', '055678901234', '1996-08-01', 'Male', 'giahuy.lam@email.com', '17 Vo Van Tan, District 3, HCMC', '2023-11-29', '2027-11-29'),
('R056', 'Tran Van Phuc', '056789012345', '1990-05-20', 'Male', 'tranphuc@email.com', 'Ap 5, Xa Tan Lap, Huyen Tan Thanh, Long An', '2023-12-01', '2027-12-01'),
('R057', 'Le Thi Xuan Mai', '057890123456', '1993-11-05', 'Female', 'xuanmai.le@email.com', '2 Le Duan, Dong Ha, Quang Tri', '2023-12-03', '2027-12-03'),
('R058', 'Ali bin Musa', '058901234567', '1988-08-08', 'Male', 'ali.musa@email.com', 'Kuala Lumpur, Malaysia', '2023-12-05', '2027-12-05'),
('R059', 'Sok Dara', '059012345678', '1992-04-14', 'Other', 'dara.sok@email.com', 'Phnom Penh, Cambodia', '2023-12-07', '2027-12-07'),
('R060', 'Nguyen Thi Be', '060123456789', '1996-06-06', 'Female', 'be.nguyen@email.com', 'Xom 3, Xa Nghi Trung, Huyen Nghi Loc, Nghe An', '2023-12-09', '2027-12-09'),
('R061', 'Zhang Wei', '061234567890', '1991-01-30', 'Male', 'zhang.wei@email.com', 'Shanghai, China', '2023-12-11', '2027-12-11'),
('R062', 'Phan Thanh Son', '062345678901', '1989-10-10', 'Male', 'thanjson@email.com', 'Khu Pho 1, Phuong Linh Trung, Thu Duc City', '2023-12-13', '2027-12-13'),
('R063', 'Thach Thi Na', '063456789012', '1995-03-03', 'Female', 'thachna@email.com', 'Ap 7, Xa My Thanh, Huyen Tra Cu, Tra Vinh', '2023-12-15', '2027-12-15'),
('R064', 'Jessica Nguyen', '064567890123', '1994-09-09', 'Female', 'jessica.n@email.com', 'San Jose, California, USA', '2023-12-17', '2027-12-17'),
('R065', 'Huynh Cong Tinh', '065678901234', '1987-12-25', 'Male', 'congtinh.huynh@email.com', '5A Nguyen Thai Hoc, Quy Nhon City', '2023-12-19', '2027-12-19'),
('R066', 'Dang Thi Lan Huong', '066789012345', '1990-03-11', 'Female', 'huong.dang@email.com', '56 Ngo Quyen, Vinh City, Nghe An', '2023-12-21', '2027-12-21'),
('R067', 'Luong Van Hien', '067890123456', '1986-07-27', 'Male', 'hien.luong@email.com', 'Ban Xa Phin, Dong Van, Ha Giang', '2023-12-23', '2027-12-23'),
('R068', 'Nguyen Hoang Minh Chau', '068901234567', '1998-05-16', 'Female', 'chau.minh@email.com', '99B Hai Ba Trung, Hoi An, Quang Nam', '2023-12-25', '2027-12-25'),
('R069', 'David Tran', '069012345678', '1993-02-22', 'Male', 'david.tran@email.com', 'Paris, France', '2023-12-27', '2027-12-27'),
('R070', 'Mai Thi Bich Duyen', '070123456789', '1991-01-01', 'Female', 'duyen.mai@email.com', 'Cai Be Town, Tien Giang', '2023-12-29', '2027-12-29'),
('R071', 'Vo Van Dung', '071234567890', '1985-09-09', 'Male', 'dung.vo@email.com', 'Rach Gia City, Kien Giang', '2024-01-02', '2028-01-02'),
('R072', 'Truong Thi Cam Nhung', '072345678901', '1992-10-03', 'Female', 'nhung.truong@email.com', 'Plei Ku, Gia Lai', '2024-01-04', '2028-01-04'),
('R073', 'Phung Minh Quan', '073456789012', '1997-06-30', 'Male', 'quan.phung@email.com', 'Tam Ky City, Quang Nam', '2024-01-06', '2028-01-06'),
('R074', 'Sandra Le', '074567890123', '1989-04-17', 'Female', 'sandra.le@email.com', 'Toronto, Canada', '2024-01-08', '2028-01-08'),
('R075', 'Hoang Gia Bao', '075678901234', '1995-11-11', 'Male', 'giabao.hoang@email.com', '9 Tran Hung Dao, Ninh Binh', '2024-01-10', '2028-01-10'),
('R076', 'Lang Van Cuong', '076789012345', '1984-12-12', 'Male', 'cuong.lang@email.com', 'Thon Suoi Da, Xa Phuoc Tan, Huyen Bac Ai, Ninh Thuan', '2024-01-12', '2028-01-12'),
('R077', 'Dieu Thi Muoi', '077890123456', '1990-08-18', 'Female', 'muoi.dieu@email.com', 'Thon Pa Hy, Xa A Roang, A Luoi, Thua Thien Hue', '2024-01-14', '2028-01-14'),
('R078', 'Bui Van Hai', '078901234567', '1993-03-03', 'Male', 'hai.bui@email.com', 'Xa Tan Hoi, Huyen Tan Chau, Tay Ninh', '2024-01-16', '2028-01-16'),
('R079', 'Pham Thi Hong Ngoc', '079012345678', '1992-07-19', 'Female', 'ngoc.pham@email.com', '10 Nguyen Hue, Quy Nhon City, Binh Dinh', '2024-01-18', '2028-01-18'),
('R080', 'Michael Johnson', '080123456789', '1985-06-06', 'Male', 'michael.j@email.com', 'New York City, USA', '2024-01-20', '2028-01-20'),
('R081', 'Nguyen Kieu Anh', '081234567890', '1996-01-15', 'Female', 'kieuanh.nguyen@email.com', 'Phu Quoc Island, Kien Giang', '2024-01-22', '2028-01-22'),
('R082', 'Tang Gia Huy', '082345678901', '1994-04-04', 'Male', 'gia.huy@email.com', '2/45 Le Van Sy, District 3, HCMC', '2024-01-24', '2028-01-24'),
('R083', 'Jessica Vu', '083456789012', '1990-09-09', 'Female', 'jessica.vu@email.com', 'Melbourne, Australia', '2024-01-26', '2028-01-26'),
('R084', 'Y Brah Ail', '084567890123', '1988-10-10', 'Male', 'ybrah.ail@email.com', 'Buon Tri A, Huyen Cu Mgar, Dak Lak', '2024-01-28', '2028-01-28'),
('R085', 'Lai Minh Tuan', '085678901234', '1997-05-05', 'Male', 'tuan.lai@email.com', 'Son La City, Son La', '2024-01-30', '2028-01-30'),
('R086', 'Hoang Kha Nhi', '086789012345', '1998-08-18', 'Female', 'khanhi.hoang@email.com', 'Cao Lanh City, Dong Thap', '2024-02-01', '2028-02-01'),
('R087', 'Nguyen Phi Long', '087890123456', '1995-12-25', 'Male', 'philong.nguyen@email.com', 'Dien Bien Phu City, Dien Bien', '2024-02-03', '2028-02-03'),
('R088', 'Luu Thanh Truc', '088901234567', '1991-03-17', 'Female', 'truc.luu@email.com', 'Soc Trang City, Soc Trang', '2024-02-05', '2028-02-05'),
('R089', 'Tran Bao Han', '089012345678', '1999-07-07', 'Other', 'baohan.tran@email.com', 'Lao Cai City, Lao Cai', '2024-02-07', '2028-02-07'),
('R090', 'Pham Anh Vu', '090123456789', '1986-06-06', 'Male', 'anhvu.pham@email.com', 'Bac Lieu City, Bac Lieu', '2024-02-09', '2028-02-09'),
('R091', 'Dinh Mai Quyen', '091234567890', '1992-02-29', 'Female', 'maiquyen.dinh@email.com', 'Cang Long, Tra Vinh', '2024-02-11', '2028-02-11'),
('R092', 'Trieu Minh Quan', '092345678901', '1993-10-10', 'Male', 'minhquan.trieu@email.com', 'Lang Son City, Lang Son', '2024-02-13', '2028-02-13'),
('R093', 'Alex Do', '093456789012', '1990-01-20', 'Male', 'alex.do@email.com', 'Los Angeles, California, USA', '2024-02-15', '2028-02-15'),
('R094', 'Vo Kim Ngan', '094567890123', '1997-11-11', 'Female', 'kimngan.vo@email.com', 'Vinh Chau Town, Soc Trang', '2024-02-17', '2028-02-17'),
('R095', 'Nguyen Khanh Duy', '095678901234', '1996-09-19', 'Male', 'khanhduy.nguyen@email.com', 'Ha Tinh City, Ha Tinh', '2024-02-19', '2028-02-19'),
('R096', 'Dang Le Hai Yen', '096789012345', '1994-04-24', 'Female', 'haiyen.dangle@email.com', 'Bao Loc City, Lam Dong', '2024-02-21', '2028-02-21'),
('R097', 'Nguyen Huy Khang', '097890123456', '1990-12-12', 'Male', 'huykhang.nguyen@email.com', 'Nam Dinh City, Nam Dinh', '2024-02-23', '2028-02-23'),
('R098', 'Ly Thi Hoa', '098901234567', '1993-01-02', 'Female', 'hoa.ly@email.com', 'Meo Vac, Ha Giang', '2024-02-25', '2028-02-25'),
('R099', 'Tan Khoa Nguyen', '099012345678', '1995-06-06', 'Male', 'tankhoa.nguyen@email.com', 'My Tho City, Tien Giang', '2024-02-27', '2028-02-27'),
('R100', 'Emily Tran', '100123456789', '1992-08-08', 'Female', 'emily.tran@email.com', 'Sydney, Australia', '2024-02-29', '2028-02-29');

-- Sample books
INSERT INTO Book (ISBN, title, author, publisher, publishYear, genre, price, quantity)
VALUES
('B001', 'Introduction to Java Programming', 'John Smith', 'Tech Publishing', 2022, 'Education', 350000, 10),
('B002', 'Data Structures and Algorithms', 'Jane Johnson', 'Academic Press', 2021, 'Education', 400000, 8),
('B003', 'The History of Vietnam', 'Nguyen Van X', 'Vietnam Publishing House', 2020, 'History', 250000, 15),
('B004', 'Modern Web Development', 'Michael Brown', 'Web Tech Books', 2023, 'Technology', 380000, 12),
('B005', 'Artificial Intelligence Basics', 'Sarah Davis', 'Future Press', 2022, 'Technology', 420000, 7),
('B006', 'Vietnamese Cuisine', 'Tran Thi Y', 'Food Publishers', 2021, 'Cooking', 300000, 20),
('B007', 'Business Management', 'Robert Wilson', 'Business Books', 2020, 'Business', 320000, 9),
('B008', 'English Grammar', 'Emily Thompson', 'Language Learning', 2022, 'Education', 280000, 25),
('B009', 'Digital Marketing Strategies', 'Daniel Lee', 'Marketing Press', 2023, 'Business', 360000, 6),
('B010', 'Vietnamese Literature Anthology', 'Le Thi Z', 'Literature House', 2021, 'Fiction', 270000, 18),
('B011', 'Advanced Programming Techniques', 'Robert Martin', 'Clean Code Publishers', 2022, 'Education', 425000, 12),
('B012', 'Đắc Nhân Tâm', 'Dale Carnegie', 'First News', 2019, 'Self-Help', 120000, 30),
('B013', 'Mathematics for Computer Science', 'Eric Lehman', 'MIT Press', 2021, 'Education', 390000, 15),
('B014', 'Introduction to Algorithms', 'Thomas H. Cormen', 'MIT Press', 2020, 'Education', 450000, 10),
('B015', 'Clean Code', 'Robert C. Martin', 'Prentice Hall', 2019, 'Technology', 395000, 12),
('B016', 'The Art of Computer Programming', 'Donald E. Knuth', 'Addison-Wesley', 2018, 'Education', 520000, 8),
('B017', 'Design Patterns', 'Erich Gamma', 'Addison-Wesley', 2021, 'Technology', 380000, 10),
('B018', 'Nhà Giả Kim', 'Paulo Coelho', 'NXB Hội Nhà Văn', 2020, 'Fiction', 85000, 25),
('B019', 'Dế Mèn Phiêu Lưu Ký', 'Tô Hoài', 'NXB Kim Đồng', 2019, 'Fiction', 75000, 28),
('B020', 'The Pragmatic Programmer', 'Andrew Hunt', 'Addison-Wesley', 2020, 'Technology', 475000, 14),
('B021', 'Introduction to Machine Learning', 'Ethem Alpaydin', 'MIT Press', 2021, 'Technology', 450000, 9),
('B022', 'The Catcher in the Rye', 'J.D. Salinger', 'Little, Brown and Company', 1951, 'Fiction', 200000, 22),
('B023', 'The Lean Startup', 'Eric Ries', 'Crown Publishing', 2011, 'Business', 380000, 16),
('B024', 'Python Crash Course', 'Eric Matthes', 'No Starch Press', 2019, 'Technology', 350000, 11),
('B025', 'Deep Learning', 'Ian Goodfellow', 'MIT Press', 2016, 'Technology', 550000, 6),
('B026', 'Web Design with HTML, CSS, and JavaScript', 'Jon Duckett', 'Wiley', 2019, 'Technology', 420000, 18),
('B027', 'The Power of Habit', 'Charles Duhigg', 'Random House', 2012, 'Self-Help', 280000, 20),
('B028', '1984', 'George Orwell', 'Secker & Warburg', 1949, 'Fiction', 150000, 10),
('B029', 'Sapiens: A Brief History of Humankind', 'Yuval Noah Harari', 'Harvill Secker', 2015, 'History', 400000, 13),
('B030', 'The Code Book', 'Simon Singh', 'Doubleday', 1999, 'Technology', 450000, 8),
('B031', 'Learning React', 'Alex Banks', 'O’Reilly Media', 2021, 'Technology', 350000, 10),
('B032', 'Modern JavaScript for Beginners', 'Brad Traversy', 'Udemy Press', 2020, 'Education', 380000, 12),
('B033', 'The Subtle Art of Not Giving a F*ck', 'Mark Manson', 'HarperOne', 2016, 'Self-Help', 320000, 24),
('B034', 'The Art of War', 'Sun Tzu', 'Various', 500, 'Business', 150000, 19),
('B035', 'The Psychology of Money', 'Morgan Housel', 'Harriman House', 2020, 'Business', 330000, 17),
('B036', 'Clean Architecture', 'Robert C. Martin', 'Prentice Hall', 2017, 'Technology', 410000, 9),
('B037', 'Thinking, Fast and Slow', 'Daniel Kahneman', 'Farrar, Straus and Giroux', 2011, 'Self-Help', 420000, 15),
('B038', 'React Up & Running', 'Stoyan Stefanov', 'O’Reilly Media', 2016, 'Technology', 400000, 11),
('B039', 'JavaScript: The Good Parts', 'Douglas Crockford', 'O’Reilly Media', 2008, 'Technology', 375000, 14),
('B040', 'Learning TypeScript', 'Remo H. Jansen', 'Packt Publishing', 2020, 'Technology', 450000, 13),
('B041', 'Design Patterns: Elements of Reusable Object-Oriented Software', 'Erich Gamma', 'Addison-Wesley', 1994, 'Technology', 500000, 8),
('B042', 'Eloquent JavaScript', 'Marijn Haverbeke', 'No Starch Press', 2018, 'Technology', 370000, 22),
('B043', 'Atomic Habits', 'James Clear', 'Avery', 2018, 'Self-Help', 350000, 25),
('B044', 'The Complete Software Developer’s Career Guide', 'John Sonmez', 'Simple Programmer', 2017, 'Technology', 380000, 10),
('B045', 'Web Development with Node and Express', 'Ethan Brown', 'O’Reilly Media', 2019, 'Technology', 420000, 9),
('B046', 'Principles: Life and Work', 'Ray Dalio', 'Simon & Schuster', 2017, 'Business', 480000, 12),
('B047', 'React Design Patterns and Best Practices', 'Michele Bertoli', 'Packt Publishing', 2018, 'Technology', 390000, 13),
('B048', 'The Intelligent Investor', 'Benjamin Graham', 'HarperBusiness', 1949, 'Business', 450000, 7),
('B049', 'The Mythical Man-Month', 'Frederick P. Brooks', 'Addison-Wesley', 1975, 'Technology', 460000, 5),
('B050', 'Introduction to Data Science', 'Laura Igual', 'Springer', 2017, 'Technology', 500000, 6),
('B051', 'Deep Work', 'Cal Newport', 'Grand Central Publishing', 2016, 'Self-Help', 380000, 18),
('B052', 'The 7 Habits of Highly Effective People', 'Stephen R. Covey', 'Free Press', 1989, 'Self-Help', 320000, 23),
('B053', 'Digital Minimalism', 'Cal Newport', 'Penguin Press', 2019, 'Self-Help', 330000, 17),
('B054', 'Refactoring: Improving the Design of Existing Code', 'Martin Fowler', 'Addison-Wesley', 1999, 'Technology', 500000, 10),
('B055', 'The Design of Everyday Things', 'Don Norman', 'Basic Books', 2013, 'Business', 390000, 20),
('B056', 'UX Design for Beginners', 'Joel Marsh', 'Smashing Magazine', 2016, 'Technology', 370000, 15),
('B057', 'Kubernetes Up & Running', 'Kelsey Hightower', 'O’Reilly Media', 2017, 'Technology', 430000, 9),
('B058', 'JavaScript Allongé', 'Reginald Braithwaite', 'Independently Published', 2013, 'Technology', 400000, 14),
('B059', 'Full Stack React', 'Azat Mardan', 'Independently Published', 2020, 'Technology', 450000, 11),
('B060', 'Cloud Native Java Microservices', 'Rajesh RV', 'Packt Publishing', 2021, 'Technology', 420000, 8),
('B061', 'AI Superpowers', 'Kai-Fu Lee', 'Houghton Mifflin Harcourt', 2018, 'Technology', 470000, 12),
('B062', 'Learning JavaScript Design Patterns', 'Addy Osmani', 'O’Reilly Media', 2012, 'Technology', 360000, 14),
('B063', 'Hacking: The Art of Exploitation', 'Jon Erickson', 'No Starch Press', 2008, 'Technology', 400000, 7),
('B064', 'The DevOps Handbook', 'Gene Kim', 'IT Revolution Press', 2016, 'Technology', 480000, 10),
('B065', 'The Phoenix Project', 'Gene Kim', 'IT Revolution Press', 2013, 'Technology', 500000, 8),
('B066', 'React Native in Action', 'Nader Dabit', 'Manning Publications', 2017, 'Technology', 390000, 11),
('B067', 'Code Complete', 'Steve McConnell', 'Microsoft Press', 2004, 'Technology', 460000, 12),
('B068', 'The Pragmatic Programmer: Your Journey to Mastery', 'David Thomas', 'Addison-Wesley', 1999, 'Technology', 475000, 9),
('B069', 'Introduction to the Theory of Computation', 'Michael Sipser', 'Cengage Learning', 2012, 'Education', 500000, 6),
('B070', 'Algorithms', 'Robert Sedgewick', 'Addison-Wesley', 2011, 'Education', 420000, 12),
('B071', 'Introduction to Machine Learning with Python', 'Andreas C. Müller', 'O’Reilly Media', 2016, 'Technology', 460000, 10),
('B072', 'The Hard Thing About Hard Things', 'Ben Horowitz', 'Harper Business', 2014, 'Business', 420000, 15),
('B073', 'The Lean Analytics', 'Alistair Croll', 'O’Reilly Media', 2013, 'Business', 400000, 13),
('B074', 'Designing Data-Intensive Applications', 'Martin Kleppmann', 'O’Reilly Media', 2017, 'Technology', 490000, 9),
('B075', 'The Myth of Multitasking', 'Dave Crenshaw', 'McGraw-Hill Education', 2010, 'Self-Help', 320000, 22),
('B076', 'Learning SQL', 'Alan Beaulieu', 'O’Reilly Media', 2021, 'Technology', 370000, 18),
('B077', 'Learning Angular', 'Aristeidis Bampakos', 'Packt Publishing', 2020, 'Technology', 400000, 10),
('B078', 'You Don’t Know JS', 'Kyle Simpson', 'O’Reilly Media', 2014, 'Technology', 350000, 16),
('B079', 'The Everything Store', 'Brad Stone', 'Little, Brown and Company', 2013, 'Business', 450000, 12),
('B080', 'React: Up & Running', 'Stoyan Stefanov', 'O’Reilly Media', 2016, 'Technology', 420000, 14),
('B081', 'The 4-Hour Work Week', 'Tim Ferriss', 'Crown Publishing Group', 2007, 'Business', 350000, 20),
('B082', 'Cracking the Coding Interview', 'Gayle Laakmann McDowell', 'CareerCup', 2015, 'Education', 470000, 8),
('B083', 'Fundamentals of Computer Algorithms', 'Ellis Horowitz', 'Schaum’s Outline', 1998, 'Education', 400000, 9),
('B084', 'Principles of Computer System Design', 'Jerome Saltzer', 'Morgan Kaufmann', 2009, 'Technology', 480000, 7),
('B085', 'The Art of SEO', 'Eric Enge', 'O’Reilly Media', 2015, 'Technology', 450000, 10),
('B086', 'Mastering Bitcoin', 'Andreas M. Antonopoulos', 'O’Reilly Media', 2014, 'Technology', 510000, 5),
('B087', 'CSS Secrets', 'Lea Verou', 'O’Reilly Media', 2015, 'Technology', 380000, 13),
('B088', 'The Software Craftsman', 'Sandro Mancuso', 'Pearson', 2014, 'Technology', 430000, 11),
('B089', 'The Algorithm Design Manual', 'Steven S. Skiena', 'Springer', 2008, 'Education', 490000, 6),
('B090', 'Building Microservices', 'Sam Newman', 'O’Reilly Media', 2015, 'Technology', 470000, 12),
('B091', 'Clean Architecture: A Craftsman’s Guide to Software Structure and Design', 'Robert C. Martin', 'Prentice Hall', 2017, 'Technology', 450000, 9),
('B092', 'The Big Book of Small Python Projects', 'Leo Vasilev', 'Independently Published', 2020, 'Technology', 420000, 14),
('B093', 'Blockchain Basics', 'Daniel Drescher', 'Apress', 2017, 'Technology', 450000, 8),
('B094', 'The Bitcoin Standard', 'Saifedean Ammous', 'Hodl Publications', 2018, 'Technology', 480000, 10),
('B095', 'Python Data Science Handbook', 'Jake VanderPlas', 'O’Reilly Media', 2016, 'Technology', 440000, 12),
('B096', 'Algorithms in Java', 'Robert Sedgewick', 'Addison-Wesley', 2002, 'Education', 460000, 8),
('B097', 'The Pragmatic Programmer: 20th Anniversary Edition', 'David Thomas', 'Addison-Wesley', 2019, 'Technology', 490000, 7),
('B098', 'Advanced React', 'Nader Dabit', 'Manning Publications', 2020, 'Technology', 430000, 10),
('B099', 'AI for Everyone', 'Andrew Ng', 'Coursera', 2019, 'Technology', 350000, 11),
('B100', 'The Design Thinking Toolbox', 'Michael Lewrick', 'Wiley', 2018, 'Business', 470000, 15);

-- Sample loans
INSERT INTO LoanRecord (readerID, loanDate, expectedReturnDate, actualReturnDate)
VALUES
('R001', '2023-06-10', '2023-06-17', '2023-06-16'),
('R002', '2023-06-12', '2023-06-19', '2023-06-20'),
('R003', '2023-06-15', '2023-06-22', NULL),
('R001', '2023-06-20', '2023-06-27', '2023-06-25'),
('R004', '2023-06-22', '2023-06-29', NULL),
('R005', '2023-06-25', '2023-07-02', '2023-06-30'),
('R006', '2023-06-28', '2023-07-05', NULL),
('R007', '2023-07-01', '2023-07-08', '2023-07-07'),
('R008', '2023-07-02', '2023-07-09', NULL),
('R009', '2023-07-03', '2023-07-10', '2023-07-10'),
('R010', '2023-07-04', '2023-07-11', '2023-07-12'),
('R011', '2023-07-05', '2023-07-12', NULL),
('R012', '2023-07-06', '2023-07-13', '2023-07-14'),
('R013', '2023-07-07', '2023-07-14', NULL),
('R014', '2023-07-08', '2023-07-15', '2023-07-15'),
('R015', '2023-07-09', '2023-07-16', NULL),
('R016', '2023-07-10', '2023-07-17', '2023-07-18'),  -- Trả trễ 1 ngày
('R017', '2023-07-12', '2023-07-19', NULL),           -- Đang mượn
('R018', '2023-07-14', '2023-07-21', '2023-07-21'),  -- Trả đúng hạn
('R019', '2023-07-15', '2023-07-22', NULL),           -- Đang mượn
('R020', '2023-07-17', '2023-07-24', '2023-07-30'),  -- Trả trễ 6 ngày
('R021', '2023-07-18', '2023-07-25', '2023-07-25'),  -- Trả đúng hạn
('R022', '2023-07-20', '2023-07-27', NULL),           -- Đang mượn
('R023', '2023-07-22', '2023-07-29', '2023-07-29'),  -- Trả đúng hạn
('R024', '2023-07-24', '2023-07-31', '2023-08-01'),  -- Trả trễ 1 ngày
('R025', '2023-07-25', '2023-08-01', NULL),           -- Đang mượn
('R026', '2023-07-27', '2023-08-03', '2023-08-04'),  -- Trễ 1 ngày
('R027', '2023-07-28', '2023-08-04', NULL),           -- Chưa trả
('R028', '2023-07-30', '2023-08-06', '2023-08-05'),  -- Trả sớm
('R029', '2023-08-01', '2023-08-08', NULL),           -- Đang mượn
('R030', '2023-08-02', '2023-08-09', '2023-08-15'),  -- Trễ 6 ngày
('R031', '2023-08-03', '2023-08-10', '2023-08-10'),  -- Trả đúng hạn
('R032', '2023-08-05', '2023-08-12', NULL),           -- Đang mượn
('R033', '2023-08-06', '2023-08-13', '2023-08-14'),  -- Trễ 1 ngày
('R034', '2023-08-07', '2023-08-14', NULL),           -- Đang mượn
('R035', '2023-08-08', '2023-08-15', '2023-08-15'),  -- Trả đúng hạn
('R036', '2023-08-10', '2023-08-17', '2023-08-16'),  -- Trả đúng
('R037', '2023-08-12', '2023-08-19', '2023-08-21'),  -- Trễ 2 ngày
('R038', '2023-08-13', '2023-08-20', NULL),           -- Đang mượn
('R039', '2023-08-14', '2023-08-21', '2023-08-18'),  -- Trả sớm
('R040', '2023-08-15', '2023-08-22', '2023-08-25'),  -- Trễ 3 ngày
('R041', '2023-08-16', '2023-08-23', NULL),           -- Chưa trả
('R042', '2023-08-17', '2023-08-24', '2023-08-24'),  -- Đúng hạn
('R043', '2023-08-18', '2023-08-25', NULL),           -- Đang mượn
('R044', '2023-08-19', '2023-08-26', '2023-08-28'),  -- Trễ 2 ngày
('R045', '2023-08-20', '2023-08-27', NULL),           -- Chưa trả
('R046', '2023-08-21', '2023-08-28', '2023-08-29'),  -- Trễ 1 ngày
('R047', '2023-08-22', '2023-08-29', NULL),           -- Đang mượn
('R048', '2023-08-23', '2023-08-30', '2023-08-30'),  -- Đúng hạn
('R049', '2023-08-24', '2023-08-31', '2023-08-30'),  -- Trả sớm
('R050', '2023-08-25', '2023-09-01', NULL),           -- Đang mượn
('R051', '2023-08-26', '2023-09-02', '2023-09-05'),  -- Trễ 3 ngày
('R052', '2023-08-27', '2023-09-03', '2023-09-03'),  -- Đúng hạn
('R053', '2023-08-28', '2023-09-04', NULL),           -- Chưa trả
('R054', '2023-08-29', '2023-09-05', '2023-09-06'),  -- Trễ 1 ngày
('R055', '2023-08-30', '2023-09-06', NULL);           -- Đang mượn

-- Sample loan details
INSERT INTO LoanDetail (loanID, ISBN, status, fine)
VALUES
(1, 'B014', 'Returned', 0),
(1, 'B008', 'Returned', 0),
(2, 'B009', 'Returned', 5000),
(3, 'B010', 'Borrowed', 0),
(3, 'B002', 'Borrowed', 0),
(4, 'B003', 'Returned', 0),
(5, 'B006', 'Borrowed', 0),
(5, 'B007', 'Borrowed', 0),
(5, 'B009', 'Borrowed', 0),
(6, 'B001', 'Returned', 0),
(6, 'B003', 'Returned', 0),
(7, 'B005', 'Returned', 10000),
(8, 'B002', 'Borrowed', 0),
(8, 'B004', 'Borrowed', 0),
(9, 'B007', 'Returned', 0),
(10, 'B006', 'Borrowed', 0),
(10, 'B008', 'Borrowed', 0),
(11, 'B010', 'Returned', 0),
(12, 'B011', 'Returned', 0),
(13, 'B013', 'Returned', 0),
(14, 'B015', 'Borrowed', 0),
(15, 'B016', 'Borrowed', 0),
(16, 'B005', 'Returned', 0),
(16, 'B006', 'Returned', 5000),
(17, 'B002', 'Borrowed', 0),
(17, 'B008', 'Borrowed', 0),
(18, 'B009', 'Returned', 0),
(18, 'B010', 'Returned', 0),
(19, 'B011', 'Borrowed', 0),
(20, 'B012', 'Borrowed', 0),
(20, 'B013', 'Borrowed', 0),
(21, 'B001', 'Returned', 10000),
(21, 'B003', 'Returned', 0),
(22, 'B004', 'Borrowed', 0),
(23, 'B014', 'Returned', 0),
(24, 'B015', 'Returned', 0),
(24, 'B016', 'Returned', 0),
(25, 'B017', 'Borrowed', 0),
(25, 'B018', 'Borrowed', 0),
(26, 'B019', 'Borrowed', 0),
(27, 'B005', 'Returned', 0),
(28, 'B006', 'Borrowed', 0),
(28, 'B007', 'Borrowed', 0),
(29, 'B008', 'Returned', 2000),
(30, 'B009', 'Returned', 0),
(31, 'B001', 'Returned', 0),
(31, 'B002', 'Returned', 0),
(32, 'B003', 'Borrowed', 0),
(32, 'B004', 'Borrowed', 0),
(33, 'B005', 'Returned', 0),
(33, 'B006', 'Returned', 5000),
(34, 'B007', 'Borrowed', 0),
(35, 'B008', 'Borrowed', 0),
(35, 'B009', 'Borrowed', 0),
(36, 'B010', 'Returned', 0),
(36, 'B011', 'Returned', 10000),
(37, 'B012', 'Returned', 0),
(37, 'B013', 'Returned', 0),
(38, 'B014', 'Borrowed', 0),
(39, 'B015', 'Borrowed', 0),
(39, 'B016', 'Borrowed', 0),
(40, 'B017', 'Returned', 0),
(40, 'B018', 'Returned', 0),
(41, 'B019', 'Returned', 2000),
(42, 'B020', 'Borrowed', 0),
(43, 'B001', 'Borrowed', 0),
(43, 'B002', 'Borrowed', 0),
(44, 'B003', 'Returned', 0),
(44, 'B004', 'Returned', 0),
(45, 'B005', 'Returned', 0),
(45, 'B006', 'Returned', 5000),
(46, 'B007', 'Borrowed', 0),
(47, 'B008', 'Borrowed', 0),
(48, 'B009', 'Returned', 0),
(48, 'B010', 'Returned', 0),
(49, 'B011', 'Returned', 0),
(50, 'B012', 'Borrowed', 0);