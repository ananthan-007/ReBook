package com.rebook.controller;

import com.rebook.dao.Item;
import com.rebook.model.Item;

public class ItemController {
    private static final Item itemDAO = new Item();

    public static boolean addItem(Item item) {
        try {
            itemDAO.addItem(item);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
