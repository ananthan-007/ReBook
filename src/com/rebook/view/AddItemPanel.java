package com.rebook.view;

import com.rebook.dao.ItemDAO;
import com.rebook.model.Item;
import com.rebook.util.Validator;

import javax.swing.*;
import java.awt.*;

public class AddItemPanel extends JFrame {

    private JTextField titleField;
    private JTextField descriptionField;
    private JTextField priceField;
    private JTextField quantityField;
    private JButton addButton;

    private ItemDAO dao;

    public AddItemPanel() {
        setTitle("Add Item");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        dao = new ItemDAO();

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Title:");
        titleField = new JTextField(20);
        JLabel descLabel = new JLabel("Description:");
        descriptionField = new JTextField(20);
        JLabel priceLabel = new JLabel("Price:");
        priceField = new JTextField(20);
        JLabel quantityLabel = new JLabel("Quantity:");
        quantityField = new JTextField(20);

        addButton = new JButton("Add Item");

        // Layout
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(titleLabel, gbc);
        gbc.gridx = 1;
        panel.add(titleField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(descLabel, gbc);
        gbc.gridx = 1;
        panel.add(descriptionField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(priceLabel, gbc);
        gbc.gridx = 1;
        panel.add(priceField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(quantityLabel, gbc);
        gbc.gridx = 1;
        panel.add(quantityField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        panel.add(addButton, gbc);

        add(panel);
        setVisible(true);

        addButton.addActionListener(e -> addItem());
    }

    private void addItem() {
        String title = titleField.getText().trim();
        String desc = descriptionField.getText().trim();
        String priceStr = priceField.getText().trim();
        String quantityStr = quantityField.getText().trim();

        // Validation
        if (!Validator.isNotEmpty(title)) {
            JOptionPane.showMessageDialog(this, "Title is required.");
            return;
        }

        if (!Validator.isValidPrice(priceStr)) {
            JOptionPane.showMessageDialog(this, "Enter valid price.");
            return;
        }

        if (!Validator.isPositiveInt(quantityStr)) {
            JOptionPane.showMessageDialog(this, "Enter valid quantity.");
            return;
        }

        try {
            Item item = new Item();
            item.setTitle(title);
            item.setDescription(desc);
            item.setPrice(Double.parseDouble(priceStr));
            item.setQuantity(Integer.parseInt(quantityStr));

            dao.addItem(item);
            JOptionPane.showMessageDialog(this, "Item added successfully!");
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding item: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
