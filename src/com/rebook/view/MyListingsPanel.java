package com.rebook.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import com.rebook.controller.MyListingsController;

public class MyListingsPanel extends JFrame {
    private JTable itemsTable;
    private JTable requestsTable;
    private JTextField searchField;
    private JButton btnSearch, btnEdit, btnDelete, btnRefresh;
    private DefaultTableModel itemsModel, requestsModel;
    private MyListingsController controller;

    public MyListingsPanel() {
        setTitle("My Listings");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        controller = new MyListingsController(this);

        setLayout(new BorderLayout());

        // Top Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        searchPanel.setBackground(new Color(70, 130, 180));
        searchPanel.setPreferredSize(new Dimension(getWidth(), 60));

        searchField = new JTextField(30);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        btnSearch = new JButton("Search");
        btnSearch.setFont(new Font("Arial", Font.BOLD, 14));
        btnRefresh = new JButton("Refresh");
        btnRefresh.setFont(new Font("Arial", Font.BOLD, 14));

        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(btnSearch);
        searchPanel.add(btnRefresh);

        add(searchPanel, BorderLayout.NORTH);

        // Center panel with tabs
        JTabbedPane tabbedPane = new JTabbedPane();

        // My Items Tab
        JPanel itemsPanel = new JPanel(new BorderLayout());
        itemsModel = new DefaultTableModel(new String[]{"ID", "Title", "Type", "Condition"}, 0);
        itemsTable = new JTable(itemsModel);
        itemsTable.setRowHeight(30);
        itemsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        itemsTable.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel itemsBtnPanel = new JPanel();
        btnEdit = new JButton("Edit");
        btnDelete = new JButton("Delete");
        btnEdit.setFont(new Font("Arial", Font.BOLD, 14));
        btnDelete.setFont(new Font("Arial", Font.BOLD, 14));
        itemsBtnPanel.add(btnEdit);
        itemsBtnPanel.add(btnDelete);

        itemsPanel.add(new JScrollPane(itemsTable), BorderLayout.CENTER);
        itemsPanel.add(itemsBtnPanel, BorderLayout.SOUTH);

        // Requests Tab
        JPanel requestsPanel = new JPanel(new BorderLayout());
        requestsModel = new DefaultTableModel(
                new String[]{"Request ID", "Item", "Requester Name", "Email", "Phone", "Status", "Date"}, 0);
        requestsTable = new JTable(requestsModel);
        requestsTable.setRowHeight(30);
        requestsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        requestsTable.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel requestsBtnPanel = new JPanel();
        JButton btnApprove = new JButton("Approve");
        JButton btnReject = new JButton("Reject");
        btnApprove.setFont(new Font("Arial", Font.BOLD, 14));
        btnReject.setFont(new Font("Arial", Font.BOLD, 14));
        requestsBtnPanel.add(btnApprove);
        requestsBtnPanel.add(btnReject);

        requestsPanel.add(new JScrollPane(requestsTable), BorderLayout.CENTER);
        requestsPanel.add(requestsBtnPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("My Items", itemsPanel);
        tabbedPane.addTab("Requests", requestsPanel);

        add(tabbedPane, BorderLayout.CENTER);

        // Footer
        JPanel footer = new JPanel();
        footer.setBackground(new Color(240, 240, 240));
        JLabel footText = new JLabel("2025 | Reusable Books & Stationary Exchange");
        footText.setFont(new Font("Arial", Font.PLAIN, 12));
        footer.add(footText);
        add(footer, BorderLayout.SOUTH);

        // Button actions
        btnSearch.addActionListener(e -> controller.searchItems(searchField.getText()));
        btnRefresh.addActionListener(e -> controller.loadItems());
        btnDelete.addActionListener(e -> controller.deleteSelectedItem());
        btnEdit.addActionListener(e -> controller.editSelectedItem());
        btnApprove.addActionListener(e -> controller.approveRequest());
        btnReject.addActionListener(e -> controller.rejectRequest());

        setVisible(true);
        controller.loadItems();
        controller.loadRequests();
    }

    public DefaultTableModel getTableModel() { return itemsModel; }
    public JTable getTable() { return itemsTable; }
    public DefaultTableModel getRequestsModel() { return requestsModel; }
    public JTable getRequestsTable() { return requestsTable; }
}