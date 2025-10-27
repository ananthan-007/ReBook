package com.rebook.controller;

import com.rebook.dao.ItemDAO;
import com.rebook.model.Item;

public class ItemController {
    public static boolean addItem(Item item) {
        try {
            ItemDAO.addItem(item);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
