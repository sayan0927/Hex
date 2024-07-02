package GAME.BOARD;

/**
 *  Provides a method for evaluating the score of a board which is then used by AI Players ( Minimax / Alpha Beta Pruning)
 */
public interface BoardEvaluator {

    int evaluate(GameBoard gameBoard);

    /**
     * Returns the last Computed score , computed by evaluate(),
     * @return Last Score , or null if evaluate() was not invoked earlier
     */
    Integer getLastComputedScore();
}
