package GUI;

import GAME.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.Semaphore;

public class WelcomePage extends BaseFrame{

    JLabel gameSizeLabel;
    JTextArea gameSizeTextField;
    JButton playAgainstHuman,playAgainstHardAI, playAgainstEasyAi,getPlayAgainstMediumAi;
    int playingAgainst=-1;

    Semaphore semaphore;

    JPanel rightPanel,leftPanel,lowerPanel,innerLeftPanel;

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }



    public WelcomePage()
    {

        this.setTitle("Welcome");


        playAgainstHuman=new JButton("Play against another Human");
        playAgainstHardAI=new JButton("Play against AI (Hard)");
        playAgainstEasyAi =new JButton("Play against AI (Easy)");
        getPlayAgainstMediumAi =new JButton("Play against AI (Medium)");



        playAgainstHuman.addActionListener(this);
        playAgainstHardAI.addActionListener(this);
        playAgainstEasyAi.addActionListener(this);
        getPlayAgainstMediumAi.addActionListener(this);

        lowerPanel=new ImagePanel(BaseFrame.yellowBackGroundImage,BaseFrame.windowSize);
        lowerPanel.setLayout(new BorderLayout());

        rightPanel = new ImagePanel(BaseFrame.yellowBackGroundImage,BaseFrame.windowSize);
        rightPanel.setLayout(new FlowLayout());

        leftPanel = new ImagePanel(BaseFrame.blackBackGroundImage,new Dimension(rightPanel.getWidth()/3,rightPanel.getHeight()));
        leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.Y_AXIS));



        lowerPanel.add(rightPanel,BorderLayout.CENTER);
        lowerPanel.add(leftPanel,BorderLayout.WEST);

        basePanel.add(lowerPanel);



       leftPanel.add(playAgainstHuman,BorderLayout.NORTH);
       leftPanel.add(playAgainstEasyAi,BorderLayout.CENTER);
       leftPanel.add(playAgainstHardAI,BorderLayout.SOUTH);
       leftPanel.add(getPlayAgainstMediumAi,BorderLayout.AFTER_LAST_LINE);


       gameSizeLabel = new JLabel("Game Size");
       gameSizeTextField = new JTextArea("5");
       rightPanel.add(gameSizeLabel,BorderLayout.CENTER);
       rightPanel.add(gameSizeTextField,BorderLayout.AFTER_LAST_LINE);

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {

        if(e.getSource().equals(playAgainstHuman))
            playingAgainst=3;

        if(e.getSource().equals(playAgainstHardAI))
            playingAgainst=2;



       // semaphore.release();
        this.setVisible(false);

        Thread gameThread = new Thread(new Runnable() { public void run()
        {

            // current thread is AWT Thread
            //running the controller in a main thread
            GameController gameController=new GameController(playingAgainst,Integer.parseInt(gameSizeTextField.getText()));

        }});

        gameThread.start();


    }

   public int getPlayingAgainst()
    {
        return playingAgainst;
    }




}
