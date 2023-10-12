package GAME;

import GUI.*;

import javax.swing.*;
import java.util.concurrent.Semaphore;

import static javax.swing.SwingUtilities.updateComponentTreeUI;

public class GameController {

    boolean against_human;
    int gameSize;

    Player firstPlayer;
    Player secondPlayer;

    private final GameBoard gameBoard;
    MoveDetails latest_move;
    int moves_made;
    boolean game_ended;
    GameWindow gameWindow;
    public GameController(int playing_against,int gameSize) {

        this.gameSize = gameSize;
        this.against_human = (playing_against > 0);

        gameBoard = new GameBoard(this.gameSize);
        gameWindow=new GameWindowImpl(gameBoard);
        firstPlayer=new HumanPlayer('R', true, this.gameSize,gameWindow);

        if(playing_against==3)
            secondPlayer=new HumanPlayer('B', true, this.gameSize,gameWindow);
        if(playing_against==2)
            secondPlayer=new ComputerPlayerHardAI('B','R',false,this.gameSize,this.gameBoard);

        moves_made = 0;
        game_ended = false;

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

            updateComponentTreeUI((BaseFrame)gameWindow);

            updateComponentTreeUI((BaseFrame)gameWindow);

        } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException |
                 ClassNotFoundException | NullPointerException ignored) {

        }

        init_Game();

    }

    void init_Game()  {

        gameWindow.setCurrentSymbol(firstPlayer.getSymbol());
        Semaphore lock=new Semaphore(0);
        while (true)
        {

            //human making move
            while (true)
            {
                gameWindow.setSemaphore(lock);
                getLock(lock);

                latest_move = firstPlayer.newMove(gameBoard);
                if(gameBoard.isValidInput(latest_move)) {

                    int n = latest_move.getRow() * gameSize + latest_move.getCol();
                    gameBoard.makeMove(n,firstPlayer.getSymbol());
                    break;
                }
                else
                    gameWindow.showInvalidMoveError();
            }

            moves_made++;
            gameBoard.incrementFirstPlayerMoves();
            gameWindow.setCurrentSymbol(secondPlayer.getSymbol());
            gameWindow.updateWindow();

            if(winCheck())
                break;
            //2nd player make move
            while (true)
            {
               // Semaphore lock=new Semaphore(0);
                gameWindow.setSemaphore(lock);

                //only if 2nd player is human take the lock
                if(secondPlayer instanceof HumanPlayer)
                    getLock(lock);

                latest_move = secondPlayer.newMove(gameBoard);
                if(gameBoard.isValidInput(latest_move)) {

                    int n = latest_move.getRow() * gameSize + latest_move.getCol();
                    gameBoard.makeMove(n,secondPlayer.getSymbol());
                    break;
                }
                else
                    gameWindow.showInvalidMoveError();

            }

            moves_made++;
            gameBoard.incrementSecondPlayerMoves();
            gameWindow.setCurrentSymbol(firstPlayer.getSymbol());
            gameWindow.updateWindow();

            if(winCheck())
                break;
        }

        restart();
    }

    private void getLock(Semaphore lock)
    {
        try {
            lock.acquire();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    boolean winCheck()
    {
        if (gameBoard.check_if_game_ended())
        {
            gameWindow.showWinner("Winner is  " + gameBoard.getWinner());
            return true;
        }
        return false;
    }

    void restart()
    {
        gameWindow.disposeWindow();
        new WelcomePage();
    }

}
