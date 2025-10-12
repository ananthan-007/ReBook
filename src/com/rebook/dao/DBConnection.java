package com.rebook.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBConnection {

    private static final String CONFIG_PATH = "config/db.properties";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Always open a new connection each time getConnection() is called
    public static Connection getConnection() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
            props.load(fis);

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String pass = props.getProperty("db.password");

            return DriverManager.getConnection(url, user, pass);

        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
