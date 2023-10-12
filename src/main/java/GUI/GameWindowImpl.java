package GUI;

import GAME.GameBoard;
import GAME.MoveDetails;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class GameWindowImpl extends BaseFrame implements GameWindow, MouseListener {

    JButton back ;
    JPanel midPanel,leftPanel,lowerPanel;
    JLabel[][] buttons;

    MoveDetails lastMove;

    GameBoard gameBoard;

    Semaphore semaphore;

    Character currentSymbol;


    public void setCurrentSymbol(Character currentSymbol) {
        this.currentSymbol = currentSymbol;


    }

    public void disposeWindow()
    {
        this.dispose();
    }

    public void showInvalidMoveError()
    {
        JOptionPane.showMessageDialog(this,"Error this move is Invalid");
    }

    public void showWinner(String text)
    {
        JOptionPane.showMessageDialog(this,text);
    }
    public void showDraw()
    {
        JOptionPane.showMessageDialog(this,"Draw");
    }



    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }
    Map<Character,String> iconMap;

    JLabel label;

    JTextArea l;

    int xStart =20,width=50,height=50;
    int yStart =50;

    public GameWindowImpl(GameBoard gameBoard)
    {
        iconMap=new HashMap<>();
        iconMap.put('R',"assets\\red\\");
        iconMap.put('B',"assets\\blue\\");
        iconMap.put('-',"assets\\blank\\");

        this.gameBoard=gameBoard;


        lowerPanel=new ImagePanel(BaseFrame.yellowBackGroundImage,BaseFrame.windowSize);
        lowerPanel.setLayout(new BorderLayout());

        midPanel = new ImagePanel(BaseFrame.yellowBackGroundImage,BaseFrame.windowSize);
        midPanel.setLayout(null);

        JPanel redPanel1 = new JPanel();
        redPanel1.setBackground(new Color(255,0,0));
        redPanel1.setBounds(0,0,midPanel.getWidth(),20);

        JPanel redPanel2 = new JPanel();
        redPanel2.setBackground(new Color(255,0,0));
        redPanel2.setBounds(0,midPanel.getHeight()-20,midPanel.getWidth(),20);


        JPanel bluePanel1 = new JPanel();
        bluePanel1.setBackground(new Color(0,0,255));
        bluePanel1.setBounds(0,0,20,midPanel.getHeight());

        JPanel bluePanel2 = new JPanel();
        bluePanel2.setBackground(new Color(0,0,255));
        bluePanel2.setBounds(midPanel.getWidth()-195,0,20,midPanel.getHeight());
        System.out.println(midPanel.getWidth()-500);


      //  midPanel.add(redPanel1);
       // midPanel.add(redPanel2);

       // midPanel.add(bluePanel1);
       // midPanel.add(bluePanel2);

        leftPanel = new ImagePanel(BaseFrame.blackBackGroundImage,new Dimension(midPanel.getWidth()/4,midPanel.getHeight()));
        leftPanel.setLayout(null);



        lowerPanel.add(midPanel,BorderLayout.CENTER);
        lowerPanel.add(leftPanel,BorderLayout.WEST);


        basePanel.add(lowerPanel);
        buttons=new JLabel[gameBoard.gamesize][gameBoard.gamesize];


        currentSymbol='R';
        label =new JLabel("Turn of "+currentSymbol);
        l =new JTextArea("Turn of "+currentSymbol);
        label.setOpaque(true);
        l.setEditable(false);
        l.setBounds(20,50,80,30);
        leftPanel.add(l);

        back = new JButton("Back");
        back.addActionListener(this);
        back.setBounds(20,100,80,30);
        leftPanel.add(back);





        placeButtons();
        refresh();


    }

    void placeButtons()
    {
        for(int i=0;i<gameBoard.gamesize;i++)
        {
            int lastXStart= xStart;
            for(int j=0;j<gameBoard.gamesize;j++)
            {
                buttons[i][j]=new JLabel("");
                String folderPath = iconMap.get(gameBoard.getBoardState()[i][j]);
                String imageName = null;
                if(i==0 && j==0)
                    imageName="topleft.png";
                else if(i==0 && j== gameBoard.gamesize-1)
                    imageName="topright.png";
                else if(i==gameBoard.gamesize-1 && j== 0)
                    imageName="leftbottom.png";
                else if(i==gameBoard.gamesize-1 && j== gameBoard.gamesize-1)
                    imageName="rightbottom.png";
                else if(i==0)
                    imageName="topred.png";
                else if(i==gameBoard.gamesize-1)
                    imageName="bottomred.png";
                else if(j==0)
                    imageName="leftblue.png";
                else if(j==gameBoard.gamesize-1)
                    imageName="rightblue.png";
                else
                    imageName="middle.png";

                ImageIcon buttonImage = new ImageIcon(folderPath+imageName);
                buttons[i][j].setIcon(buttonImage);
                buttons[i][j].setBounds(xStart, yStart,width,height);
                buttons[i][j].addMouseListener(this);
                midPanel.add(buttons[i][j]);
                xStart = xStart +width-10;

            }

            yStart = yStart + height - 17;
            xStart = lastXStart + 21;
        }




    }

    public void updateWindow()
    {

        for(int i=0;i<gameBoard.gamesize;i++)
        {
            for(int j=0;j<gameBoard.gamesize;j++)
            {
                JLabel currentButton=buttons[i][j];
                Character currentState=gameBoard.getBoardState()[i][j];
                try {

                    String folderPath = iconMap.get(gameBoard.getBoardState()[i][j]);
                    String imageName = null;
                    if(i==0 && j==0)
                        imageName="topleft.png";
                    else if(i==0 && j== gameBoard.gamesize-1)
                        imageName="topright.png";
                    else if(i==gameBoard.gamesize-1 && j== 0)
                        imageName="leftbottom.png";
                    else if(i==gameBoard.gamesize-1 && j== gameBoard.gamesize-1)
                        imageName="rightbottom.png";
                    else if(i==0)
                        imageName="topred.png";
                    else if(i==gameBoard.gamesize-1)
                        imageName="bottomred.png";
                    else if(j==0)
                        imageName="leftblue.png";
                    else if(j==gameBoard.gamesize-1)
                        imageName="rightblue.png";
                    else
                        imageName="middle.png";

                    Image image= ImageIO.read(new File(folderPath+imageName)).getScaledInstance(currentButton.getWidth(),currentButton.getHeight(),Image.SCALE_DEFAULT);
                    currentButton.setIcon(new ImageIcon(image));
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        }

        this.l.setText("Turn of "+String.valueOf(this.currentSymbol));
        refresh();
    }

    void refresh()
    {
        this.setVisible(false);
        this.repaint();
        this.setVisible(true);
    }

    public MoveDetails getLastMove()  {
        return lastMove;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource().equals(back))
        {
            this.dispose();
            new WelcomePage();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        for(int i=0;i<gameBoard.gamesize;i++)
        {
            for(int j=0;j<gameBoard.gamesize;j++)
            {
                if(e.getSource().equals(buttons[i][j])) {
                    {
                        lastMove = new MoveDetails(i, j, currentSymbol);

                        semaphore.release();
                        break;
                    }

                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
