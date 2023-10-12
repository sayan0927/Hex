

package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public abstract class BaseFrame extends JFrame implements ActionListener {
    JPanel basePanel;
    protected static String yellowBackGroundImage ="assets\\background.jpg";
    protected static String blackBackGroundImage ="assets\\panel.jpg";
    protected static Dimension windowSize=new Dimension(700,700);
    BaseFrame()
    {
        basePanel = new ImagePanel(yellowBackGroundImage,windowSize);
        basePanel.setLayout(null);
        this.add(basePanel);
        this.setBounds(600,200,600,600);
        this.setVisible(true);
        this.pack();

    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
