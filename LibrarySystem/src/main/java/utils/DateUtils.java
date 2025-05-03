package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {

    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter SQL_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Parse a date string in the format dd/MM/yyyy
    public static LocalDate parseDisplayDate(String dateString) {
        try {
            return LocalDate.parse(dateString, DISPLAY_FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    // Format a LocalDate as a string in the format dd/MM/yyyy
    public static String formatDisplayDate(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.format(DISPLAY_FORMATTER);
    }

    // Parse a date string in the SQL format yyyy-MM-dd
    public static LocalDate parseSqlDate(String dateString) {
        try {
            return LocalDate.parse(dateString, SQL_FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    // Format a LocalDate as a string in the SQL format yyyy-MM-dd
    public static String formatSqlDate(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.format(SQL_FORMATTER);
    }

    // Calculate age from a birth date
    public static int calculateAge(LocalDate birthDate) {
        if (birthDate == null) {
            return 0;
        }
        LocalDate now = LocalDate.now();
        int age = now.getYear() - birthDate.getYear();
        if (now.getMonthValue() < birthDate.getMonthValue() ||
                (now.getMonthValue() == birthDate.getMonthValue() && now.getDayOfMonth() < birthDate.getDayOfMonth())) {
            age--;
        }
        return age;
    }

    // Check if a date is in the past
    public static boolean isPast(LocalDate date) {
        if (date == null) {
            return false;
        }
        return date.isBefore(LocalDate.now());
    }

    // Check if a date is in the future
    public static boolean isFuture(LocalDate date) {
        if (date == null) {
            return false;
        }
        return date.isAfter(LocalDate.now());
    }

    // Calculate days difference between two dates
    public static long daysBetween(LocalDate date1, LocalDate date2) {
        if (date1 == null || date2 == null) {
            return 0;
        }
        return Math.abs(date1.toEpochDay() - date2.toEpochDay());
    }
}