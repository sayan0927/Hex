package org.example;

import GUI.WelcomePage;

import javax.swing.*;

import static javax.swing.SwingUtilities.updateComponentTreeUI;

public class Main {
    public static void main(String[] args) {


        WelcomePage welcome = new WelcomePage();
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
            System.out.println(info);


        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

            updateComponentTreeUI(welcome);

            updateComponentTreeUI(welcome);

        } catch (Exception exception )
        {
            exception.printStackTrace();
        }
    }
}