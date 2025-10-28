package com.rebook.view;
import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    public Dashboard() {
        // Frame setup
        setTitle("Dashboard - Reusable Books & Stationary Exchange");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ensure program closes completely when window closes
        setLocationRelativeTo(null); // Centers the window on screen
        setLayout(new BorderLayout());

        // Title Bar
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0, 128, 128));
        titlePanel.setPreferredSize(new Dimension(700, 70));

        JLabel title = new JLabel("Reusable Books and Stationary Exchange");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(title);

        add(titlePanel, BorderLayout.NORTH);

        // Center Panel (Buttons)
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 2, 30, 30));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        centerPanel.setBackground(Color.WHITE);

        // Buttons
        JButton addItemBtn = new JButton("Add Item");
        JButton viewItemsBtn = new JButton("View Items");
        JButton myListingsBtn = new JButton("My Listings");
        JButton logoutBtn = new JButton("Log Out");

        // Button styling
        JButton[] buttons = { addItemBtn, viewItemsBtn, myListingsBtn, logoutBtn };
        for (JButton b : buttons) {
            b.setFont(new Font("Segoe UI", Font.BOLD, 16));
            b.setFocusPainted(false);
            b.setBackground(new Color(230, 240, 255));
            b.setBorder(BorderFactory.createLineBorder(new Color(0, 128, 128), 8));
            b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        logoutBtn.setBackground(new Color(255, 127, 127));
        // Add buttons to center panel
        centerPanel.add(addItemBtn);
        centerPanel.add(viewItemsBtn);
        centerPanel.add(myListingsBtn);
        centerPanel.add(logoutBtn);

        add(centerPanel, BorderLayout.CENTER);

        // Footer
        JPanel footer = new JPanel();
        footer.setBackground(new Color(240, 240, 240));
        JLabel footText = new JLabel("2025 | St. Joseph's College of Engineering and Technology, Palai");
        footText.setFont(new Font("Arial", Font.PLAIN, 12));
        footer.add(footText);
        add(footer, BorderLayout.SOUTH);

        setVisible(true);

        // Button actions
        addItemBtn.addActionListener(e -> new AddItemPanel());
        viewItemsBtn.addActionListener(e -> new ViewItemsPanel());
        myListingsBtn.addActionListener(e -> new MyListingsPanel());
        logoutBtn.addActionListener(e -> {
            com.rebook.util.Session.clear();
            JOptionPane.showMessageDialog(this, "You have been logged out.");
            dispose(); // closes dashboard
            new LoginPanel();
        });
    }
}
