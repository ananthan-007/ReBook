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
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(0, 128, 128));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE);
        emailField = new JTextField(20);
        
        JLabel pswdLabel = new JLabel("Password:");
        pswdLabel.setForeground(Color.WHITE);
        pswdField = new JPasswordField(20);
        
        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        
        JLabel signUpLabel = new JLabel("Don't have an account?");
        signUpLabel.setForeground(Color.WHITE);
        
        signUpButton = new JButton("Sign Up");
        signUpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signUpButton.setBorderPainted(false);
        signUpButton.setContentAreaFilled(false);
        signUpButton.setForeground(new Color(173, 216, 230));
        signUpButton.addActionListener(this);

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(pswdLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(pswdField, gbc);

        gbc.gridx = 0; 
        gbc.gridy = 2;
        gbc.gridwidth = 2; 
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER; 
        mainPanel.add(loginButton, gbc);
        
        JPanel signUpPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        signUpPanel.setOpaque(false);
        signUpPanel.add(signUpLabel);
        signUpPanel.add(signUpButton);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(signUpPanel, gbc);
        
        setContentPane(mainPanel);

        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {

            //dashboard loading option
            
        } else if (e.getSource() == signUpButton) {
            dispose();
            new RegisterPanel();
        }
    }
}