package com.rebook.controller;

import com.rebook.view.MyListingsPanel;
import com.rebook.dao.ItemDAO;
import com.rebook.model.Item;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class MyListingsController {
    private MyListingsPanel view;
    private ItemDAO dao;

    public MyListingsController(MyListingsPanel view) {
        this.view = view;
        this.dao = new ItemDAO();
    }

    public void loadItems() {
        List<Item> items = dao.getAllItems();
        populateTable(items);
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
        dao.deleteItem(id);
        loadItems();
    }

    public void editSelectedItem() {
        JTable table = view.getTable();
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Please select an item to edit.");
            return;
        }
        int id = (int) table.getValueAt(row, 0);
        String title = (String) table.getValueAt(row, 1);
        String category = (String) table.getValueAt(row, 2);
        String newTitle = JOptionPane.showInputDialog("Edit title:", title);
        if (newTitle != null && !newTitle.trim().isEmpty()) {
            dao.updateItem(id, newTitle, category);
            loadItems();
        }
    }

    private void populateTable(List<Item> items) {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);
        for (Item item : items) {
            model.addRow(new Object[]{item.getId(), item.getTitle(), item.getCategory(), item.isAvailable() ? "Available" : "Donated"});
        }
    }
}
