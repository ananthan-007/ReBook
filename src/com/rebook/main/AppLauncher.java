package com.rebook.main;
import  com.rebook.view.LoginPanel;
import com.rebook.view.Dashboard;
public class AppLauncher {
    public static void main(String[] args) {
         SwingUtilities.invokeLater(() -> new Dashboard().setVisible(true));
         new LoginPanel();
    }
}
