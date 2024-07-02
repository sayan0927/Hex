package GAME.PLAYERS;

import GAME.BOARD.BoardEvaluator;
import GAME.BOARD.GameBoard;
import GAME.BOARD.ShortestPathBoardEvaluator;
import GUI.GameWindow;





public class PlayerFactory {


    public static BoardEvaluator defaultEvaluator =  ShortestPathBoardEvaluator.getInstance(); ;
    public static final Character humanPlayerCharacter = 'R';
    public static final Character computerPlayerCharacter = 'B';

    public static Player createPlayer(int playingAgainst, int gameSize, GameWindow gameWindow, GameBoard gameBoard) {
        switch (playingAgainst) {
            case 2:
                return new HumanPlayer(computerPlayerCharacter, false, gameSize,gameWindow);
            case 1:
                return new ComputerPlayerMinimaxAI(computerPlayerCharacter,humanPlayerCharacter,false,gameSize,gameBoard,defaultEvaluator);
            default:
                throw new IllegalArgumentException("Invalid playing against value: " + playingAgainst);
        }
    }
}
