package GAME;


import GAME.BOARD.GameBoard;
import GAME.BOARD.GameBoardImpl;

import GAME.PLAYERS.*;
import GUI.*;
import org.example.Config;
import org.example.MediaUtil;


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

    public static final Character humanPlayerCharacter = PlayerFactory.humanPlayerCharacter;
    public static final Character computerPlayerCharacter = PlayerFactory.computerPlayerCharacter;

    public GameController(int playing_against,int gameSize) {

        this.gameSize = gameSize;
        this.against_human = (playing_against > 0);


        gameBoard = new GameBoardImpl(gameSize,humanPlayerCharacter, PlayerFactory.defaultEvaluator);
        gameWindow=new GameWindowImpl(gameBoard);
        firstPlayer=new HumanPlayer(humanPlayerCharacter, true, gameSize,gameWindow);

        secondPlayer = PlayerFactory.createPlayer(playing_against,gameSize,gameWindow,gameBoard);

        moves_made = 0;
        game_ended = false;

    }

    /**
     * Loop for making new Move
     * If player is AI, the loop will only iterate once since there will be no invalid inputs.
     * If player is Human, check if input is valid then make the move, else show Error on GameWindow
     * @param player Can be Instance of ComputerPlayerEasy/Medium/HardAI or HumanPlayer
     */
    void makePlayerMove(Player player) {
        while (true)
        {
            latest_move = player.newMove(gameBoard);

            if(gameBoard.isValidInput(latest_move)) {
                MediaUtil.getInstance().playSound(Config.moveMadeSoundPath);
                gameBoard.makeMove(latest_move);
                break;
            }
            else {
                MediaUtil.getInstance().playSound(Config.invalidMoveSoundPath);
                gameWindow.showInvalidMoveError();
            }

        }
    }



    /**
     * Main Game Loop
     * Infinite loop which delegates moves to makePlayerMove()
     * counts moves , checks if game has ended and show draw/winner as needed
     */
    public void init_Game()  {


        while (true)
        {

            //First player make move

            gameWindow.setNextPlayerSymbol(firstPlayer.getSymbol());
            makePlayerMove(firstPlayer);
            moves_made++;
            gameBoard.incrementFirstPlayerMoves();
            gameWindow.updateWindow();

            if(winCheck())
                break;



            //second player make move
            {
                System.out.println(secondPlayer.getSymbol());
                gameWindow.setNextPlayerSymbol(secondPlayer.getSymbol());
                makePlayerMove(secondPlayer);
                moves_made++;
                gameBoard.incrementSecondPlayerMoves();
                gameWindow.setNextPlayerSymbol(firstPlayer.getSymbol());

                gameWindow.updateWindow();
                if(winCheck())
                    break;
            }

         //   System.out.println("Board score is + "+gameBoard.getEvaluator().getLastComputedScore());


        }

        restart();
    }



    boolean winCheck()
    {
        if (gameBoard.checkIfGameEnded())
        {
            playGameEndAudio(gameBoard.getWinner());
            gameWindow.showWinner("Winner is  " + gameBoard.getWinner());
            return true;
        }
        return false;
    }

    /**
     * Plays win sound if winner is human
     * Plays lose sound if winner is computer
     * @param winner The winner
     */
    private void playGameEndAudio(String winner)
    {
        if(winner.equals(humanPlayerCharacter.toString()))
            MediaUtil.getInstance().playSound(Config.winSoundPath);
        else
            MediaUtil.getInstance().playSound(Config.lossSoundPath);
    }

    void restart()
    {
        gameWindow.disposeWindow();
        new WelcomePage();
    }

}
