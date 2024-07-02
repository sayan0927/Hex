package org.example;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.FileNotFoundException;
import java.net.URL;


public class MediaUtil {

    private static MediaUtil instance=null;

    private MediaUtil()
    {

    }



    public static MediaUtil getInstance()
    {
        if(instance==null)
            instance=new MediaUtil();

        return instance;
    }

    public ImageIcon loadImageIcon(String imagePath) throws RuntimeException
    {
        URL imgURL = getClass().getClassLoader().getResource(imagePath);

        if(imgURL!=null)
            return new ImageIcon(imgURL);
        else
            throw new RuntimeException( "Invalid Image resource at+"+imagePath);

    }

    public void playSound(String soundPath) throws RuntimeException
    {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource(soundPath));

            Clip clip = AudioSystem.getClip();

            clip.open(audioInputStream);
            clip.start();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
