package com.rebook.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import com.rebook.controller.MyListingsController;

public class MyListingsPanel extends JPanel {
    private JTable table;
    private JTextField searchField;
    private JButton btnSearch, btnEdit, btnDelete, btnRefresh;
    private DefaultTableModel model;
    private MyListingsController controller;

    public MyListingsPanel() {
        setLayout(new BorderLayout());
        controller = new MyListingsController(this);

        // Top Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        btnSearch = new JButton("Search");
        btnRefresh = new JButton("Refresh");
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(btnSearch);
        searchPanel.add(btnRefresh);

        // Table Model
        model = new DefaultTableModel(new String[]{"ID", "Title", "Type", "Condition"}, 0);
        table = new JTable(model);

        // Bottom Buttons
        JPanel btnPanel = new JPanel();
        btnEdit = new JButton("Edit");
        btnDelete = new JButton("Delete");
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);

        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        // Button actions
        btnSearch.addActionListener(e -> controller.searchItems(searchField.getText()));
        btnRefresh.addActionListener(e -> controller.loadItems());
        btnDelete.addActionListener(e -> controller.deleteSelectedItem());
        btnEdit.addActionListener(e -> controller.editSelectedItem());

        controller.loadItems();
    }

    public DefaultTableModel getTableModel() { return model; }
    public JTable getTable() { return table; }
}
