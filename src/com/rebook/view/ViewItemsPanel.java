package com.rebook.view;

import com.rebook.controller.ViewItemsController;
import com.rebook.model.Item;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewItemsPanel extends JFrame {
    private JPanel contentPanel;
    private JTextField searchField;
    private JButton btnSearch, btnRefresh;
    private ViewItemsController controller;
    
    public ViewItemsPanel() {
        setTitle("View Available Items");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        
        controller = new ViewItemsController(this);
        
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
        
        JLabel filterLabel = new JLabel("Filter by Type:");
        filterLabel.setForeground(Color.WHITE);
        filterLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        searchPanel.add(new JLabel("Search: "));
        searchPanel.add(searchField);
        searchPanel.add(btnSearch);
        searchPanel.add(btnRefresh);
        searchPanel.add(Box.createHorizontalStrut(30));
        searchPanel.add(filterLabel);
        
        JCheckBox donationsBox = new JCheckBox("Donations");
        donationsBox.setForeground(Color.WHITE);
        JCheckBox exchangesBox = new JCheckBox("Exchanges");
        exchangesBox.setForeground(Color.WHITE);
        
        searchPanel.add(donationsBox);
        searchPanel.add(exchangesBox);
        
        add(searchPanel, BorderLayout.NORTH);
        
        // Content Panel with scroll
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        add(scrollPane, BorderLayout.CENTER);
        
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
        
        setVisible(true);
        controller.loadItems();
    }
    
    public void displayItems(List<Item> items) {
        contentPanel.removeAll();
        
        if (items.isEmpty()) {
            JLabel noItemsLabel = new JLabel("No items available");
            noItemsLabel.setFont(new Font("Arial", Font.BOLD, 20));
            noItemsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPanel.add(noItemsLabel);
        } else {
            // Create a grid of cards
            int cols = 3;
            for (int i = 0; i < items.size(); i += cols) {
                JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
                rowPanel.setBackground(Color.WHITE);
                
                for (int j = i; j < Math.min(i + cols, items.size()); j++) {
                    Item item = items.get(j);
                    rowPanel.add(createItemCard(item));
                }
                
                contentPanel.add(rowPanel);
            }
        }
        
        revalidate();
        repaint();
    }
    
    private JPanel createItemCard(Item item) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setPreferredSize(new Dimension(300, 400));
        card.setBackground(Color.WHITE);
        
        // Image Section
        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(280, 200));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        if (item.getImagePath() != null && !item.getImagePath().isEmpty()) {
            try {
                ImageIcon icon = new ImageIcon(item.getImagePath());
                Image scaled = icon.getImage().getScaledInstance(280, 200, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaled));
            } catch (Exception e) {
                imageLabel.setText("No Image");
            }
        } else {
            imageLabel.setText("No Image");
        }
        
        // Title
        JLabel titleLabel = new JLabel(item.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        
        // Type and Condition
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        
        JLabel typeLabel = new JLabel("Type: " + item.getType());
        typeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel conditionLabel = new JLabel("Condition: " + item.getConditionStatus());
        conditionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        infoPanel.add(typeLabel);
        infoPanel.add(conditionLabel);
        
        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton detailsBtn = new JButton("Details");
        JButton requestBtn = new JButton("Request");
        
        detailsBtn.addActionListener(e -> controller.showDetails(item));
        requestBtn.addActionListener(e -> controller.requestItem(item));
        
        btnPanel.add(detailsBtn);
        btnPanel.add(requestBtn);
        
        card.add(imageLabel, BorderLayout.NORTH);
        
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.add(titleLabel);
        textPanel.add(infoPanel);
        textPanel.add(btnPanel);
        
        card.add(textPanel, BorderLayout.CENTER);
        
        return card;
    }
}
