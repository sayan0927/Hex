package GUI;


import org.example.MediaUtil;

import javax.swing.*;
import java.awt.*;

class ImagePanel extends JPanel {

    private Image img;


    public ImagePanel(String imgPath,Dimension size) {

        ImageIcon buttonImage = MediaUtil.getInstance().loadImageIcon(imgPath);
        this.img = buttonImage.getImage();

        if(size!=null)
        {
            setPreferredSize(size);
            setMinimumSize(size);
            // setMaximumSize(size);
            setSize(size);
        }
        this.setVisible(true);
    }



    public ImagePanel(Image img,Dimension size) {
        this.img = img;

        if(size!=null)
        {
            setPreferredSize(size);
            setMinimumSize(size);
           // setMaximumSize(size);
            setSize(size);
        }
        this.setVisible(true);


    }



    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

}
