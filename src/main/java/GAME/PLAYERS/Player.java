package GAME.PLAYERS;

import GAME.BOARD.GameBoard;
import GAME.MoveDetails;

/**
 * This abstract class represents a player in the HEX game.
 * All Concrete Player classes should implement the newMove() method
 */
public abstract class Player {

    private final Character symbol;
    private final boolean isHuman;

    /**
     * Constructor to initialize a player with the given symbol and type.
     * @param character the character representing the player's symbol (R/B)
     * @param isHuman   true if the player is human, false if the player is a computer
     */
    Player(Character character, boolean isHuman) {
        this.symbol = character;
        this.isHuman = isHuman;
    }


    /**
     * Get the symbol of the player.
     * @return the character representing the player's symbol
     */
    public Character getSymbol() {
        return symbol;
    }

    /**
     * Check if the player is human.
     * @return true if the player is human, false otherwise
     */
    boolean isHuman() {
        return isHuman;
    }

    /**
     * Abstract method to get the new move details from the player.
     * @param gameBoard the game board
     * @return the move details
     */
    public abstract MoveDetails newMove(GameBoard gameBoard);
}
