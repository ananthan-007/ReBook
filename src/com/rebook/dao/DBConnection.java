package com.rebook.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Example MySQL:
    // private static final String URL = "jdbc:mysql://localhost:3306/bookexchange?useSSL=false&serverTimezone=UTC";
    // private static final String USER = "app_user";
    // private static final String PASS = "app_password";

    // Example SQLite:
    // private static final String URL = "jdbc:sqlite:database/rebook.db";

    private static final String URL = "jdbc:sqlite:database/reusehub.db";
    private static final String USER = "";
    private static final String PASS = "";

    static {
        try {
            // For MySQL you might need: Class.forName("com.mysql.cj.jdbc.Driver");
            // For SQLite: Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            // ignore; drivers usually auto-register in modern setups
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
