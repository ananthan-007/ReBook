package com.rebook.dao;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;


public class DBConnection {
    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn != null) return conn;

        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("config/db.properties")) {
            props.load(fis);

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String pass = props.getProperty("db.password");

            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(url, user, pass);
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }
}