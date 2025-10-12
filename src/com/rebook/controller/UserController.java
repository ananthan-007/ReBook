package com.rebook.controller;

import com.rebook.dao.UserDAO;
import com.rebook.model.User;
import com.rebook.util.PasswordUtil;
import com.rebook.util.Validator;

public class UserController {
    private static final UserDAO userDAO = new UserDAO();

    // Register a new user
    public static String registerUser(String name, String email, String password, String phone) {
        // Validation
        if (!Validator.isNotEmpty(name)) return "Name is required.";
        if (!Validator.isValidEmail(email)) return "Invalid email format.";
        if (!Validator.isValidPassword(password))
            return "Password must contain upper, lower, digit, special char, and min 8 chars.";
        if (!Validator.isValidPhone(phone)) return "Phone number must be 10 digits.";

        // Check if user already exists
        if (userDAO.userExists(email)) {
            return "Email already exists!";
        }

        // Hash password securely
        String hashedPassword = PasswordUtil.hashPassword(password);

        // Create User model
        User user = new User(name, email, phone, hashedPassword);

        // Register user
        boolean success = userDAO.registerUser(user);
        return success ? "SUCCESS" : "Error while registering. Please try again.";
    }

    // Login logic
    public static User login(String email, String password) {
        if (!Validator.isValidEmail(email) || !Validator.isNotEmpty(password))
            return null;
        return userDAO.loginUser(email, password);
    }
}
