package com.rebook.dao;

import com.rebook.model.User;
import com.rebook.util.PasswordUtil;
import java.sql.*;

public class UserDAO {

    // Register a new user
    public boolean registerUser(String name, String email, String password, String phone) {
        String checkSql = "SELECT COUNT(*) FROM users WHERE email = ?";
        String insertSql = "INSERT INTO users (name, email, password, phone) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql);
             PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {

            // Check for duplicate email
            checkStmt.setString(1, email);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return false; // email already exists
                }
            }

            // Hash the password before storing
            String hashedPassword = PasswordUtil.hashPassword(password);

            insertStmt.setString(1, name);
            insertStmt.setString(2, email);
            insertStmt.setString(3, hashedPassword);
            insertStmt.setString(4, phone);

            int rows = insertStmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Login user by verifying bcrypt hash
    public static User loginUser(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password");

                    // Check password with bcrypt
                    if (PasswordUtil.checkPassword(password, storedHash)) {
                        User user = new User();
                        user.setId(rs.getInt("user_id"));
                        user.setName(rs.getString("name"));
                        user.setEmail(rs.getString("email"));
                        user.setPhone(rs.getString("phone"));
                        return user;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
