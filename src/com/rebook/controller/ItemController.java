package com.rebook.controller;

import com.rebook.dao.ItemDAO;
import com.rebook.model.Item;
import java.sql.SQLException;

public class ItemController {
    private static final ItemDAO itemDAO = new ItemDAO();

    public static boolean addItem(Item item) {
        try {
            itemDAO.addItem(item);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
