package GAME.BOARD;

import GAME.MoveDetails;

import java.util.Set;

/**
 * This interface represents the game board for the HEX game.
 */
public interface GameBoard {

    /**
     * Increment the move count for the second player.
     */
    void incrementFirstPlayerMoves();


    /**
     * Increment the move count for the second player.
     */
    void incrementSecondPlayerMoves();

    /**
     * Get the state of the cell at the specified coordinates.
     *
     * @param i the row index
     * @param j the column index
     * @return the character representing the state of the cell
     */
    Character getCellState(int i, int j);

    /**
     * Check if the game has ended.
     *
     * @return true if the game has ended, false otherwise
     */
    boolean checkIfGameEnded();


    /**
     * Make a move on the board with the given move details.
     *
     * @param move the move details
     */
    void makeMove(MoveDetails move);


    /**
     * Overloaded Method
     * Make a move on the board at the specified position with the given character.
     *
     * @param n         the position on the board, n = move.i * gamesize + move.j
     * @param character the character to place on the board
     */
    void makeMove(int n, Character character);


    /**
     * Check if there are any moves left on the board.
     *
     * @return true if there are moves left, false otherwise
     */
    boolean movesLeft();


    /**
     * Undo a move on the board with the given move details.
     *
     * @param move the move details
     */
    void undoMove(MoveDetails move);


    /**
     * Undo a move on the board at the specified position.
     *
     * @param n the position on the board,   n = move.i * gamesize + move.j
     */
    void undoMove(int n);


    /**
     * Validate the given move details.
     *
     * @param move the move details
     * @return true if the move is valid, false otherwise
     */
    boolean isValidInput(MoveDetails move);

    /**
     * Get the current state of the board.
     *
     * @return a 2D array representing the board state
     */
    Character[][] getBoardState();

    /**
     * Evaluate the current state of the board.
     *
     * @return an integer score representing the board state
     */
    int evaluate();

    /**
     * Get the count of moves made so far.
     *
     * @return the count of moves made
     */
    int getCountOfMovesMade();

    /**
     * Get the remaining moves on the board.
     *
     * @return a set of integers representing the remaining moves
     */
    Set<Integer> getRemainingMoves();

    /**
     * Get the best score for the computer player.
     *
     * @return the best score for the computer player
     */
    int getComputerBestScore();

    /**
     * Get the best score for the human player.
     *
     * @return the best score for the human player
     */
    int getHumanBestScore();

    /**
     * Get the winner of the game.
     *
     * @return a string representing the winner
     */
    String getWinner();

    /**
     * Get the size of the game board.
     *
     * @return the size of the game board
     */
    int getGameSize();

    /**
     * Get the board evaluator.
     *
     * @return the board evaluator
     */
    BoardEvaluator getEvaluator();

    /**
     * Check if the specified coordinates are valid on the 2D game board.
     *
     * @param i the row index
     * @param j the column index
     * @return true if the coordinates are valid, false otherwise
     */
    boolean isValidCellCoordinates(int i, int j);

    /**
     * Get the character representing an empty cell.
     *
     * @return the character representing an empty cell
     */
    Character getEmptyCharecter();
}
