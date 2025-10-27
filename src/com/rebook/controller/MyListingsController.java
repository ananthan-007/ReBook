package com.rebook.controller;

import com.rebook.dao.ItemDAO;
import com.rebook.model.Item;
import com.rebook.view.MyListingsPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class MyListingsController {
    private final MyListingsPanel view;
    private final ItemDAO dao;

    public MyListingsController(MyListingsPanel view) {
        this.view = view;
        this.dao = new ItemDAO();
    }

    public void loadItems() {
        try {
            List<Item> items = dao.getAllItems();
            populateTable(items);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error loading items: " + e.getMessage());
        }
    }

    public void searchItems(String keyword) {
        List<Item> items = dao.searchItems(keyword);
        populateTable(items);
    }

    public void deleteSelectedItem() {
        JTable table = view.getTable();
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Please select an item to delete.");
            return;
        }
        int id = (int) table.getValueAt(row, 0);
        try {
            if (dao.deleteItem(id)) {
                JOptionPane.showMessageDialog(view, "Item deleted successfully.");
                loadItems();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error deleting item: " + e.getMessage());
        }
    }

    public void editSelectedItem() {
        JTable table = view.getTable();
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Please select an item to edit.");
            return;
        }

        int id = (int) table.getValueAt(row, 0);
        String oldTitle = (String) table.getValueAt(row, 1);
        String newTitle = JOptionPane.showInputDialog(view, "Edit Title:", oldTitle);

        if (newTitle != null && !newTitle.trim().isEmpty()) {
            try {
                Item item = dao.getItemById(id);
                item.setTitle(newTitle);
                dao.updateItem(item);
                loadItems();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(view, "Error updating item: " + e.getMessage());
            }
        }
    }

    private void populateTable(List<Item> items) {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);
        for (Item it : items) {
            model.addRow(new Object[]{
                    it.getId(),
                    it.getTitle(),
                    it.getType(),
                    it.getConditionStatus()
            });
        }
    }
}
