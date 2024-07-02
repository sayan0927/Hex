package GUI;

import GAME.GameController;
import org.example.Config;
import org.example.MediaUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class WelcomePage extends BaseFrame {

    JLabel gameSizeLabel, welcomeLabel, goalImageLabel, goalTextLabel;
    JButton playAgainstHuman, playAgainstAI;
    int playingAgainst = -1;
    JPanel rightPanel, leftPanel, lowerPanel;

    JComboBox<String> gameSizeSelectBox;

    public WelcomePage() {

        this.setTitle("Welcome to Hex");


        playAgainstHuman = new JButton("Play against another Human");
        playAgainstAI = new JButton("Play against AI ");



        playAgainstHuman.addActionListener(this);
        playAgainstAI.addActionListener(this);



        lowerPanel = new ImagePanel(BaseFrame.bgImgPath, BaseFrame.windowSize);
        lowerPanel.setLayout(new BorderLayout());

        rightPanel = new ImagePanel(BaseFrame.bgImgPath, BaseFrame.windowSize);
        rightPanel.setLayout(null);

        leftPanel = new ImagePanel(BaseFrame.panelImgPath, new Dimension(rightPanel.getWidth() / 3, rightPanel.getHeight()));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));


        lowerPanel.add(rightPanel, BorderLayout.CENTER);
        lowerPanel.add(leftPanel, BorderLayout.WEST);

        basePanel.add(lowerPanel);


        leftPanel.add(playAgainstHuman, BorderLayout.NORTH);

        leftPanel.add(playAgainstAI, BorderLayout.SOUTH);


        welcomeLabel = new JLabel("Welcome to Hex");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 20));
        welcomeLabel.setBounds(rightPanel.getWidth() / 4 - 20, 5, 400, 50);


        gameSizeLabel = new JLabel("Game Size");
        gameSizeLabel.setBounds(rightPanel.getWidth() / 4, 50, 50, 30);

        String[] sizes = new String[]{"3", "4", "5", "6", "7", "8"};
        gameSizeSelectBox = new JComboBox<>(sizes);
        gameSizeSelectBox.setBounds(gameSizeLabel.getBounds().x + gameSizeLabel.getWidth() + 30, 55, 50, 30);

        //Default gamesize
        gameSizeSelectBox.setSelectedItem("6");



        rightPanel.add(welcomeLabel);
        rightPanel.add(gameSizeLabel);
        rightPanel.add(gameSizeSelectBox);


        ImageIcon goalImageIcon = MediaUtil.getInstance().loadImageIcon(Config.goalImagePath);
        goalImageLabel = new JLabel(goalImageIcon);
        goalImageLabel.setBounds(50, 150, 400, 350);
        rightPanel.add(goalImageLabel);

        ImageIcon goalTextImageIcon = MediaUtil.getInstance().loadImageIcon(Config.goalTextImagePath);
        goalTextLabel = new JLabel(goalTextImageIcon);

        goalTextLabel.setBounds(10, 300, 450, 400);
        rightPanel.add(goalTextLabel);


    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(playAgainstHuman))
            playingAgainst = 2;

        if (e.getSource().equals(playAgainstAI))
            playingAgainst = 1;



        this.setVisible(false);

        // current thread is AWT Thread
        //running the controller in a main thread

        Thread gameThread = new Thread(() -> {

            GameController gameController = new GameController(playingAgainst, Integer.parseInt(gameSizeSelectBox.getSelectedItem().toString()));
            gameController.init_Game();

        });

        gameThread.start();


    }
}
