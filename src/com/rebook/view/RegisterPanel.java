package com.rebook.view;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import com.rebook.controller.UserController;

public class RegisterPanel extends JFrame implements ActionListener {

    private JTextField usernameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JPasswordField pswdField;
    private JPasswordField confirmPswdField;
    private JButton registerButton;
    private JButton loginButton;

    public RegisterPanel() {
        setTitle("Register Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
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

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setForeground(Color.WHITE);
        phoneField = new JTextField(20);

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

        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(emailLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        mainPanel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(phoneLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        mainPanel.add(phoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(pswdLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        mainPanel.add(pswdField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        mainPanel.add(confirmPswdLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        mainPanel.add(confirmPswdField, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(registerButton, gbc);

        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        loginPanel.setOpaque(false);
        loginPanel.add(loginLabel);
        loginPanel.add(loginButton);

        gbc.gridx = 0; gbc.gridy = 6;
        mainPanel.add(loginPanel, gbc);

        setContentPane(mainPanel);
        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            String username = usernameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String password = new String(pswdField.getPassword());
            String confirmPassword = new String(confirmPswdField.getPassword());

            if (username.isEmpty() || email.isEmpty() || phone.isEmpty() ||
                    password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String message = UserController.registerUser(username, email, password, phone);

            if (message.equals("SUCCESS")) {
                JOptionPane.showMessageDialog(this, "Registration successful! Please login.");
                dispose();
                new LoginPanel();
            } else {
                JOptionPane.showMessageDialog(this, message, "Registration Failed", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == loginButton) {
            dispose();
            new LoginPanel();
        }
    }
}
