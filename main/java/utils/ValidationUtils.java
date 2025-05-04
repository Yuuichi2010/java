package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

/**
 * Lớp tiện ích để kiểm tra và xác thực dữ liệu đầu vào
 */
public class ValidationUtils {

    // Các mẫu biểu thức chính quy
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^(\\+84|0)[0-9]{9,10}$");

    private static final Pattern ISBN_PATTERN =
            Pattern.compile("^[0-9]{3}-[0-9]-[0-9]{3}-[0-9]{5}-[0-9]$");

    private static final Pattern ID_CARD_PATTERN =
            Pattern.compile("^[0-9]{9,12}$");

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Kiểm tra xem một chuỗi có rỗng hoặc null không
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Kiểm tra định dạng email
     */
    public static boolean isValidEmail(String email) {
        if (isNullOrEmpty(email)) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Kiểm tra định dạng số điện thoại Việt Nam
     */
    public static boolean isValidPhone(String phone) {
        if (isNullOrEmpty(phone)) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone).matches();
    }

    /**
     * Kiểm tra định dạng ISBN
     */
    public static boolean isValidISBN(String isbn) {
        if (isNullOrEmpty(isbn)) {
            return false;
        }
        return ISBN_PATTERN.matcher(isbn).matches();
    }

    /**
     * Kiểm tra định dạng CMND/CCCD
     */
    public static boolean isValidIDCard(String idCard) {
        if (isNullOrEmpty(idCard)) {
            return false;
        }
        return ID_CARD_PATTERN.matcher(idCard).matches();
    }

    /**
     * Kiểm tra ngày hợp lệ theo định dạng dd/MM/yyyy
     */
    public static boolean isValidDate(String dateStr) {
        if (isNullOrEmpty(dateStr)) {
            return false;
        }
        try {
            LocalDate.parse(dateStr, DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Kiểm tra ngày có trong tương lai không
     */
    public static boolean isFutureDate(String dateStr) {
        if (!isValidDate(dateStr)) {
            return false;
        }
        LocalDate date = LocalDate.parse(dateStr, DATE_FORMATTER);
        return date.isAfter(LocalDate.now());
    }

    /**
     * Kiểm tra ngày có trong quá khứ không
     */
    public static boolean isPastDate(String dateStr) {
        if (!isValidDate(dateStr)) {
            return false;
        }
        LocalDate date = LocalDate.parse(dateStr, DATE_FORMATTER);
        return date.isBefore(LocalDate.now());
    }

    /**
     * Kiểm tra số nguyên hợp lệ
     */
    public static boolean isValidInteger(String value) {
        if (isNullOrEmpty(value)) {
            return false;
        }
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Kiểm tra số thực hợp lệ
     */
    public static boolean isValidDouble(String value) {
        if (isNullOrEmpty(value)) {
            return false;
        }
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Kiểm tra số nguyên dương
     */
    public static boolean isPositiveInteger(String value) {
        if (!isValidInteger(value)) {
            return false;
        }
        return Integer.parseInt(value) > 0;
    }

    /**
     * Kiểm tra số thực dương
     */
    public static boolean isPositiveDouble(String value) {
        if (!isValidDouble(value)) {
            return false;
        }
        return Double.parseDouble(value) > 0;
    }
}