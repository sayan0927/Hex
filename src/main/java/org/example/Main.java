package org.example;


import GUI.WelcomePage;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.TargetDataLine;
import javax.swing.*;

import static javax.swing.SwingUtilities.updateComponentTreeUI;

public class Main {
    public static void main(String[] args) {




            SwingUtilities.invokeLater(new Runnable() {
                public void run() {

                    MediaUtil.getInstance().playSound(Config.startupSoundPath);
                    WelcomePage welcome = new WelcomePage();

                    try {
                        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                        updateComponentTreeUI(welcome);

                    } catch (Exception exception )
                    {
                        exception.printStackTrace();
                    }

                }
            });











    }
}