package com.rebook.view;

import com.rebook.dao.ItemDAO;
import com.rebook.model.Item;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewItemsPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JButton refreshBtn;
    private ItemDAO dao;

    public ViewItemsPanel() {
        setLayout(new BorderLayout());
        dao = new ItemDAO();

        // Table model
        model = new DefaultTableModel(new String[]{"ID", "Title", "Description", "Price", "Quantity", "Status"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Refresh button
        JPanel btnPanel = new JPanel();
        refreshBtn = new JButton("Refresh");
        btnPanel.add(refreshBtn);
        add(btnPanel, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> loadItems());

        // Load items initially
        loadItems();
    }

    private void loadItems() {
        try {
            model.setRowCount(0); // Clear table
            List<Item> items = dao.getAllItems();
            for (Item item : items) {
                model.addRow(new Object[]{
                        item.getId(),
                        item.getTitle(),
                        item.getDescription(),
                        item.getPrice(),
                        item.getQuantity(),
                        item.getQuantity() > 0 ? "Available" : "Donated"
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading items: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
