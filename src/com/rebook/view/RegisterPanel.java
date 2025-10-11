package com.rebook.view;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;

public class RegisterPanel extends JFrame implements ActionListener {

    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField pswdField;
    private JPasswordField confirmPswdField;
    private JButton registerButton;
    private JButton loginButton;

    public RegisterPanel() {
        setTitle("Register Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(70, 130, 180));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.WHITE);
        usernameField = new JTextField(20);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE);
        emailField = new JTextField(20);

        JLabel pswdLabel = new JLabel("Password:");
        pswdLabel.setForeground(Color.WHITE);
        pswdField = new JPasswordField(20);

        JLabel confirmPswdLabel = new JLabel("Confirm Password:");
        confirmPswdLabel.setForeground(Color.WHITE);
        confirmPswdField = new JPasswordField(20);
        
        registerButton = new JButton("Register");
        registerButton.addActionListener(this);

        JLabel loginLabel = new JLabel("Already have an account?");
        loginLabel.setForeground(Color.WHITE);
        
        loginButton = new JButton("Login");
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setBorderPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setForeground(new Color(173, 216, 230));
        loginButton.addActionListener(this);

        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(userLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(emailLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(pswdLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(pswdField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(confirmPswdLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(confirmPswdField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(registerButton, gbc);

        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        loginPanel.setOpaque(false);
        loginPanel.add(loginLabel);
        loginPanel.add(loginButton);
        
        gbc.gridx = 0; gbc.gridy = 5;
        mainPanel.add(loginPanel, gbc);
        
        setContentPane(mainPanel);
        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            char[] password = pswdField.getPassword();
            char[] confirmPassword = confirmPswdField.getPassword();

            if (!Arrays.equals(password, confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Registration Successful!");
                dispose();
                new LoginPanel();
            }
        } else if (e.getSource() == loginButton) {
            dispose();
            new LoginPanel();
        }
    }
}