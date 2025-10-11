package com.rebook.controller;

import com.rebook.dao.UserDAO;
import com.rebook.model.User;
import com.rebook.util.Validator;

public class UserController {
    private UserDAO userDAO;

    public UserController() {
        userDAO = new UserDAO();
    }

    // Register a new user with validation
    public String registerUser(String name, String email, String password, String phone) {
        if (!Validator.isNotEmpty(name)) return "Name is required.";
        if (!Validator.isValidEmail(email)) return "Invalid email format.";
        if (!Validator.isValidPassword(password))
            return "Password must contain upper, lower, digit, special char, and min 8 chars.";
        if (!Validator.isValidPhone(phone)) return "Phone number must be 10 digits.";

        boolean success = userDAO.registerUser(name, email, password, phone);
        return success ? "Registration successful!" : "Email already exists!";
    }

    // Login logic
    public User login(String email, String password) {
        if (!Validator.isNotEmpty(email) || !Validator.isNotEmpty(password)) return null;
        if (!Validator.isValidEmail(email)) return null;
        return userDAO.loginUser(email, password);
    }
}
