package com.rebook.controller;

import com.rebook.dao.ItemDAO;
import com.rebook.dao.RequestDAO;
import com.rebook.model.Item;
import com.rebook.view.MyListingsPanel;
import com.rebook.util.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MyListingsController {
    private final MyListingsPanel view;
    private final ItemDAO dao;

    public MyListingsController(MyListingsPanel view) {
        this.view = view;
        this.dao = new ItemDAO();
    }

    public void loadItems() {
        try {
            List<Item> allItems = dao.getAllItems();
            // Filter only current user's items
            List<Item> userItems = allItems.stream()
                    .filter(item -> item.getUserId() == Session.getUser().getId())
                    .collect(Collectors.toList());
            populateTable(userItems);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error loading items: " + e.getMessage());
        }
    }

    public void loadRequests() {
        try {
            List<Map<String, Object>> requests = RequestDAO.getRequestsForUser(Session.getUser().getId());
            DefaultTableModel model = view.getRequestsModel();
            model.setRowCount(0);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            for (Map<String, Object> request : requests) {
                Timestamp date = (Timestamp) request.get("created_at");
                model.addRow(new Object[]{
                        request.get("request_id"),
                        request.get("title"),
                        request.get("requester_name"),
                        request.get("requester_email"),
                        request.get("requester_phone"),
                        request.get("status"),
                        date != null ? sdf.format(date) : ""
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error loading requests: " + e.getMessage());
        }
    }

    public void searchItems(String keyword) {
        List<Item> allItems = dao.searchItems(keyword);
        // Filter only current user's items
        List<Item> userItems = allItems.stream()
                .filter(item -> item.getUserId() == Session.getUser().getId())
                .collect(Collectors.toList());
        populateTable(userItems);
    }

    public void deleteSelectedItem() {
        JTable table = view.getTable();
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Please select an item to delete.");
            return;
        }

        // Check if item belongs to current user
        int id = (int) table.getValueAt(row, 0);
        try {
            Item item = dao.getItemById(id);
            if (item.getUserId() != Session.getUser().getId()) {
                JOptionPane.showMessageDialog(view, "You can only delete your own items!");
                return;
            }

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
        try {
            Item item = dao.getItemById(id);

            // Check if item belongs to current user
            if (item.getUserId() != Session.getUser().getId()) {
                JOptionPane.showMessageDialog(view, "You can only edit your own items!");
                return;
            }

            String oldTitle = (String) table.getValueAt(row, 1);
            String newTitle = JOptionPane.showInputDialog(view, "Edit Title:", oldTitle);

            if (newTitle != null && !newTitle.trim().isEmpty()) {
                item.setTitle(newTitle);
                dao.updateItem(item);
                loadItems();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error updating item: " + e.getMessage());
        }
    }

    public void approveRequest() {
        JTable table = view.getRequestsTable();
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Please select a request.");
            return;
        }

        int requestId = (int) table.getValueAt(row, 0);
        try {
            if (RequestDAO.updateRequestStatus(requestId, "Approved")) {
                JOptionPane.showMessageDialog(view, "Request approved!");
                loadRequests();
                loadItems();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to approve request!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error approving request: " + e.getMessage());
        }
    }

    public void rejectRequest() {
        JTable table = view.getRequestsTable();
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Please select a request.");
            return;
        }

        int requestId = (int) table.getValueAt(row, 0);
        try {
            if (RequestDAO.updateRequestStatus(requestId, "Rejected")) {
                JOptionPane.showMessageDialog(view, "Request rejected!");
                loadRequests();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to reject request!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error rejecting request: " + e.getMessage());
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