package utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtils {

    // Generate a salt for password hashing
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // Hash a password with SHA-256 and salt
    public static String hashPassword(String password) {
        try {
            String salt = generateSalt();
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

            // Store salt and hashed password together
            return salt + ":" + Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    // Verify a password against a stored hash
    public static boolean verifyPassword(String password, String storedHash) {
        try {
            // Split stored hash to get salt and hash
            String[] parts = storedHash.split(":");
            if (parts.length != 2) {
                return false;
            }

            String salt = parts[0];
            String storedHashedPassword = parts[1];

            // Hash the input password with the same salt
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
            String hashedPasswordString = Base64.getEncoder().encodeToString(hashedPassword);

            // Compare the generated hash with the stored hash
            return storedHashedPassword.equals(hashedPasswordString);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error verifying password", e);
        }
    }
}