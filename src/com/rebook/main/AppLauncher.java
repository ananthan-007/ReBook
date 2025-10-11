package com.rebook.main;

import com.rebook.dao.DBConnection;

public class AppLauncher {
    public static void main(String[] args) {
        DBConnection dbConnection = new DBConnection();
        DBConnection.getConnection();
    }
}
