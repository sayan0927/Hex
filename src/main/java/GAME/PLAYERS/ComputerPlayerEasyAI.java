package GAME.PLAYERS;

import GAME.BOARD.GameBoard;
import GAME.MoveDetails;

/**
 * This class represents a computer player with an easy AI in the HEX game.
 */
public class ComputerPlayerEasyAI extends Player {
    private int gameSize;

    /**
     * Constructor to initialize a computer player with easy AI.
     *
     * @param symbol   the character representing the player's symbol
     * @param isHuman  true if the player is human (should be false for computer player)
     * @param gamesize the size of the game
     */
    public ComputerPlayerEasyAI(Character symbol, boolean isHuman, int gamesize) {
        super(symbol, isHuman);
        this.gameSize = gamesize;
    }

    /**
     * Get the new move details from the computer player with easy AI.
     *
     * @param gameBoard the game board
     * @return the move details
     */
    @Override
    public MoveDetails newMove(GameBoard gameBoard) {
        while (true) {
            MoveDetails latest_move = new MoveDetails();
            int index_row = (int) (3 * Math.random());     // Get coordinates of cell where computer will enter their symbol
            int index_column = (int) (3 * Math.random());
            latest_move.setRow(index_row);
            latest_move.setCol(index_column);
            latest_move.setSymbol(super.getSymbol());
            if (gameBoard.isValidInput(latest_move)) {
                return latest_move;
            }
        }
    }
}
