package com.rebook.main;
import  com.rebook.view.*:
import javax.swing.*;
public class AppLauncher {
    public static void main(String[] args) {
         SwingUtilities.invokeLater(() -> new Dashboard().setVisible(true));
         new LoginPanel();
    }
}
