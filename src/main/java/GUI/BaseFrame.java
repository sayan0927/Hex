

package GUI;

import org.example.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public abstract class BaseFrame extends JFrame implements ActionListener {
    JPanel basePanel;
    protected static String bgImgPath = Config.bgImgPath;
    protected static String panelImgPath = Config.panelImgPath;
    protected static Dimension windowSize=new Dimension(700,700);
    BaseFrame()
    {
        basePanel = new ImagePanel(bgImgPath,windowSize);


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
