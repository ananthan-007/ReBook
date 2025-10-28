package com.rebook.view;

import com.rebook.controller.ItemController;
import com.rebook.model.Item;
import com.rebook.util.Validator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class AddItemPanel extends JFrame implements ActionListener {

    private JTextField titleField;
    private JTextArea descriptionArea;
    private JComboBox<String> conditionBox;
    private JRadioButton donateBtn, exchangeBtn;
    private JLabel imageLabel;
    private JButton chooseImageBtn, addItemBtn, cancelBtn;
    private String imagePath = null;

    public AddItemPanel() {
        setTitle("Add New Item");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(230, 240, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Item Title:"), gbc);
        gbc.gridx = 1;
        titleField = new JTextField(20);
        panel.add(titleField, gbc);

        // Description
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        descriptionArea = new JTextArea(4, 20);
        panel.add(new JScrollPane(descriptionArea), gbc);

        // Condition
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Condition:"), gbc);
        gbc.gridx = 1;
        String[] conditions = {"New", "Good", "Used"};
        conditionBox = new JComboBox<>(conditions);
        panel.add(conditionBox, gbc);

        // Type (Donation / Exchange)
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Type:"), gbc);
        gbc.gridx = 1;
        donateBtn = new JRadioButton("Donation");
        exchangeBtn = new JRadioButton("Exchange");
        ButtonGroup group = new ButtonGroup();
        group.add(donateBtn);
        group.add(exchangeBtn);
        donateBtn.setSelected(true);
        JPanel typePanel = new JPanel();
        typePanel.add(donateBtn);
        typePanel.add(exchangeBtn);
        panel.add(typePanel, gbc);

        // Image chooser
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Image:"), gbc);
        gbc.gridx = 1;
        chooseImageBtn = new JButton("Choose Image");
        chooseImageBtn.addActionListener(this);
        panel.add(chooseImageBtn, gbc);

        // Show selected image
        gbc.gridx = 1; gbc.gridy = 5;
        imageLabel = new JLabel("No image selected");
        panel.add(imageLabel, gbc);

        // Buttons
        JPanel btnPanel = new JPanel();
        addItemBtn = new JButton("Add Item");
        cancelBtn = new JButton("Cancel");
        addItemBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
        btnPanel.add(addItemBtn);
        btnPanel.add(cancelBtn);

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        panel.add(btnPanel, gbc);

        setContentPane(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == chooseImageBtn) {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Select Item Image");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            int result = chooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selected = chooser.getSelectedFile();
                imagePath = copyImageToLocalFolder(selected);
                imageLabel.setText(selected.getName());
            }

        } else if (e.getSource() == addItemBtn) {
            String title = titleField.getText().trim();
            String desc = descriptionArea.getText().trim();
            String condition = (String) conditionBox.getSelectedItem();
            String type = donateBtn.isSelected() ? "Donation" : "Exchange";

            if (!Validator.isNotEmpty(title)) {
                JOptionPane.showMessageDialog(this, "Title is required!");
                return;
            }

            Item item = new Item();
            item.setTitle(title);
            item.setDescription(desc);
            item.setConditionStatus(condition);
            item.setType(type);
            item.setImagePath(imagePath);
            item.setUserId(com.rebook.util.Session.getUser().getId());

            boolean success = ItemController.addItem(item);
            if (success) {
                JOptionPane.showMessageDialog(this, "Item added successfully!");
                dispose(); // ✅ closes only AddItemPanel
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add item!");
            }

        } else if (e.getSource() == cancelBtn) {
            dispose(); // ✅ just closes AddItemPanel
        }
    }

    private String copyImageToLocalFolder(File source) {
        try {
            File imagesDir = new File("images");
            if (!imagesDir.exists()) imagesDir.mkdir();
            File dest = new File(imagesDir, source.getName());
            Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return dest.getAbsolutePath();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
