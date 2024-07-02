package GUI;

import GAME.BOARD.GameBoard;
import GAME.MoveDetails;
import org.example.Config;
import org.example.MediaUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class GameWindowImpl extends BaseFrame implements GameWindow, MouseListener {

    JButton back;
    JPanel midPanel, leftPanel, lowerPanel;
    JLabel[][] buttons;
    MoveDetails lastMove;
    GameBoard gameBoard;

    Character nextPlayerSymbol;
    JLabel label;
    JTextArea l;
    int xStart = 20, width = 50, height = 50;
    int yStart = 50;


    public GameWindowImpl(GameBoard gameBoard) {
        this.setTitle("Play");



        this.gameBoard = gameBoard;


        lowerPanel = new ImagePanel(BaseFrame.bgImgPath, BaseFrame.windowSize);
        lowerPanel.setLayout(new BorderLayout());

        midPanel = new ImagePanel(BaseFrame.bgImgPath, BaseFrame.windowSize);
        midPanel.setLayout(null);

        JPanel redPanel1 = new JPanel();
        redPanel1.setBackground(new Color(255, 0, 0));
        redPanel1.setBounds(0, 0, midPanel.getWidth(), 20);

        JPanel redPanel2 = new JPanel();
        redPanel2.setBackground(new Color(255, 0, 0));
        redPanel2.setBounds(0, midPanel.getHeight() - 20, midPanel.getWidth(), 20);


        JPanel bluePanel1 = new JPanel();
        bluePanel1.setBackground(new Color(0, 0, 255));
        bluePanel1.setBounds(0, 0, 20, midPanel.getHeight());

        JPanel bluePanel2 = new JPanel();
        bluePanel2.setBackground(new Color(0, 0, 255));
        bluePanel2.setBounds(midPanel.getWidth() - 195, 0, 20, midPanel.getHeight());
        System.out.println(midPanel.getWidth() - 500);


        leftPanel = new ImagePanel(BaseFrame.panelImgPath, new Dimension(midPanel.getWidth() / 4, midPanel.getHeight()));
        leftPanel.setLayout(null);


        lowerPanel.add(midPanel, BorderLayout.CENTER);
        lowerPanel.add(leftPanel, BorderLayout.WEST);


        basePanel.add(lowerPanel);
        buttons = new JLabel[gameBoard.getGameSize()][gameBoard.getGameSize()];


        nextPlayerSymbol = 'R';
        label = new JLabel("Turn of " + nextPlayerSymbol);
        l = new JTextArea("Turn of " + nextPlayerSymbol);
        label.setOpaque(true);
        l.setEditable(false);
        l.setBounds(20, 50, 80, 30);
        leftPanel.add(l);

        back = new JButton("Back");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameWindowImpl.this.disposeWindow();
                new WelcomePage();
            }
        });
        back.setBounds(20, 100, 80, 30);
        leftPanel.add(back);


        placeButtons();
        refresh();


    }

    public void setNextPlayerSymbol(Character nextPlayerSymbol) {
        this.nextPlayerSymbol = nextPlayerSymbol;
        updateWindow();

    }

    public void disposeWindow() {
        this.dispose();
    }

    public void showInvalidMoveError() {
        JOptionPane.showMessageDialog(this, "Error this move is Invalid");
    }

    public void showWinner(String text) {
        JOptionPane.showMessageDialog(this, text);
    }

    public void showDraw() {
        JOptionPane.showMessageDialog(this, "Draw");
    }

    void placeButtons() {
        int gameSize = gameBoard.getGameSize();

        for (int i = 0; i < gameSize; i++) {
            int lastXStart = xStart;
            for (int j = 0; j < gameSize; j++) {
                buttons[i][j] = new JLabel("");
                String folderPath = Config.iconMap.get(gameBoard.getCellState(i, j));
                String imageName = null;
                if (i == 0 && j == 0)
                    imageName = "topleft.png";
                else if (i == 0 && j == gameSize - 1)
                    imageName = "topright.png";
                else if (i == gameSize - 1 && j == 0)
                    imageName = "leftbottom.png";
                else if (i == gameSize - 1 && j == gameSize - 1)
                    imageName = "rightbottom.png";
                else if (i == 0)
                    imageName = "topred.png";
                else if (i == gameSize - 1)
                    imageName = "bottomred.png";
                else if (j == 0)
                    imageName = "leftblue.png";
                else if (j == gameSize - 1)
                    imageName = "rightblue.png";
                else
                    imageName = "middle.png";


                String imagePath = Config.concatFilenameToPath(folderPath,imageName);
                ImageIcon buttonImage = MediaUtil.getInstance().loadImageIcon(imagePath);
                buttons[i][j].setIcon(buttonImage);
                buttons[i][j].setBounds(xStart, yStart, width, height);
                buttons[i][j].addMouseListener(this);
                midPanel.add(buttons[i][j]);
                xStart = xStart + width - 10;

            }

            yStart = yStart + height - 17;
            xStart = lastXStart + 21;
        }


    }

    public void updateWindow() {

        int gamesize = gameBoard.getGameSize();
        for (int i = 0; i < gamesize; i++) {
            for (int j = 0; j < gamesize; j++) {
                JLabel currentButton = buttons[i][j];
                try {

                    String folderPath = Config.iconMap.get(gameBoard.getCellState(i, j));
                    String imageName = null;
                    if (i == 0 && j == 0)
                        imageName = "topleft.png";
                    else if (i == 0 && j == gamesize - 1)
                        imageName = "topright.png";
                    else if (i == gamesize - 1 && j == 0)
                        imageName = "leftbottom.png";
                    else if (i == gamesize - 1 && j == gamesize - 1)
                        imageName = "rightbottom.png";
                    else if (i == 0)
                        imageName = "topred.png";
                    else if (i == gamesize - 1)
                        imageName = "bottomred.png";
                    else if (j == 0)
                        imageName = "leftblue.png";
                    else if (j == gamesize - 1)
                        imageName = "rightblue.png";
                    else
                        imageName = "middle.png";

                    String imagePath = Config.concatFilenameToPath(folderPath,imageName);
                    ImageIcon buttonImage = MediaUtil.getInstance().loadImageIcon(imagePath);


                    currentButton.setIcon(buttonImage);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

        this.l.setText("Turn of " + String.valueOf(this.nextPlayerSymbol));
        refresh();
    }

    void refresh() {
        this.setVisible(false);
        this.repaint();
        this.setVisible(true);
    }

    public MoveDetails getLastMove() {

        MoveDetails toReturn = lastMove;
        lastMove = null;
        return toReturn;
    }


    @Override
    public void mouseClicked(MouseEvent e) {

        int gameSize = gameBoard.getGameSize();

        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++) {
                if (e.getSource().equals(buttons[i][j])) {
                    {
                        lastMove = new MoveDetails(i, j, nextPlayerSymbol);
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
