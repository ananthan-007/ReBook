package com.rebook.controller;

import com.rebook.dao.ItemDAO;
import com.rebook.dao.RequestDAO;
import com.rebook.model.Item;
import com.rebook.view.ViewItemsPanel;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class ViewItemsController {
    private final ViewItemsPanel view;
    private final ItemDAO dao;
    
    public ViewItemsController(ViewItemsPanel view) {
        this.view = view;
        this.dao = new ItemDAO();
    }
    
    public void loadItems() {
        try {
            List<Item> items = dao.getAllItems();
            view.displayItems(items);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error loading items: " + e.getMessage());
        }
    }
    
    public void searchItems(String keyword) {
        try {
            List<Item> items = keyword.isEmpty() ? dao.getAllItems() : dao.searchItems(keyword);
            view.displayItems(items);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error searching items: " + e.getMessage());
        }
    }
    
    public void showDetails(Item item) {
        try {
            Item fullItem = dao.getItemById(item.getId());
            if (fullItem == null) {
                JOptionPane.showMessageDialog(view, "Item not found!");
                return;
            }
            
            StringBuilder details = new StringBuilder();
            details.append("Title: ").append(fullItem.getTitle()).append("\n");
            details.append("Type: ").append(fullItem.getType()).append("\n");
            details.append("Condition: ").append(fullItem.getConditionStatus()).append("\n");
            details.append("Description: ").append(fullItem.getDescription()).append("\n");
            
            JOptionPane.showMessageDialog(view, details.toString(), "Item Details", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error loading details: " + e.getMessage());
        }
    }
    
    public void requestItem(Item item) {
        try {
            // Get current user
            com.rebook.model.User user = com.rebook.util.Session.getUser();
            if (user == null) {
                JOptionPane.showMessageDialog(view, "Please login first!");
                return;
            }
            
            // Check if user is requesting their own item
            if (item.getUserId() == user.getId()) {
                JOptionPane.showMessageDialog(view, "You cannot request your own item!");
                return;
            }
            
            // Create request
            if (RequestDAO.createRequest(item.getId(), user.getId())) {
                JOptionPane.showMessageDialog(view, "Request sent successfully!");
            } else {
                JOptionPane.showMessageDialog(view, "Failed to send request!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error sending request: " + e.getMessage());
        }
    }
}
