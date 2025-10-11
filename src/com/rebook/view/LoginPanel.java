package com.rebook.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LoginPanel extends JFrame implements ActionListener {

    private JTextField emailField;
    private JPasswordField pswdField;
    private JButton loginButton;
    private JButton signUpButton;

    public LoginPanel() {
        setTitle("Login Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(400, 220));
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(0, 128, 128));
        setContentPane(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(emailLabel, gbc);

        emailField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(emailField, gbc);

        JLabel pswdLabel = new JLabel("Password:");
        pswdLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(pswdLabel, gbc);

        pswdField = new JPasswordField(20);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(pswdField, gbc);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

        JPanel signUpPanel = new JPanel();
        signUpPanel.setOpaque(false);
        JLabel signUpLabel = new JLabel("Don't have an account?");
        signUpLabel.setForeground(Color.WHITE);
        signUpButton = new JButton("Sign Up");
        signUpButton.setBorderPainted(false);
        signUpButton.setContentAreaFilled(false);
        signUpButton.setForeground(new Color(173, 216, 230));
        signUpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signUpButton.addActionListener(this);
        signUpPanel.add(signUpLabel);
        signUpPanel.add(signUpButton);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(signUpPanel, gbc);

        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String email = emailField.getText();
            String password = new String(pswdField.getPassword());
            System.out.println("Login attempt with Email: " + email);        }
    }
}