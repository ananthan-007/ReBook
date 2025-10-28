package com.rebook.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class Validator {
    //String validation
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    //number validation
    public static boolean isPositiveNumber(String value) {
        try {
            return Integer.parseInt(value) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //Email validation
    public static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    //Phone-number validation
    public static boolean isValidPhone(String phone) {
        return phone.matches("\\d{10}");
    }

    //password validation
    public static boolean isValidPassword(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$");
    }

    //Date validation
    public static boolean isValidDate(String date) {
        try {
            LocalDate.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    //General utility
    public static String validateItem(String name, String quantity) {
        if (!isNotEmpty(name)) return "Item name is required.";
        if (!isPositiveNumber(quantity)) return "Quantity must be a positive number.";
        return null; // valid
    }

    //sql injection safety
    private static final Pattern SAFE_TITLE = Pattern.compile("^[A-Za-z0-9 .,'\\-()]{1,200}$");
    private static final Pattern POSITIVE_INT = Pattern.compile("^\\d+$");

    public static boolean isValidTitle(String s) {
        return s != null && SAFE_TITLE.matcher(s).matches();
    }

    public static boolean isPositiveInt(String s) {
        return s != null && POSITIVE_INT.matcher(s).matches() && Integer.parseInt(s) > 0;
    }

    public static String safeTrim(String s) {
        return s == null ? null : s.trim();
    }
}
