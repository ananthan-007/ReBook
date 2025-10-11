package com.rebook.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Hash the password with BCrypt
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12)); // 12 = work factor
    }

    // Verify password during login
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) return false;
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
