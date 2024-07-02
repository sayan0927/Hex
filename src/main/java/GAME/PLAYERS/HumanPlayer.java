package GAME.PLAYERS;
import GAME.BOARD.GameBoard;
import GAME.MoveDetails;
import GUI.GameWindow;



/**
 * This class represents a human player in the HEX game.
 */
public class HumanPlayer extends Player {
    MoveDetails last;
    private final MoveDetails move;
    private int game_size;
    GameWindow gameWindow;



    /**
     * Constructor to initialize a human player.
     *
     * @param symbol     the character representing the player's symbol
     * @param isHuman    true if the player is human
     * @param game_size  the size of the game
     * @param gameWindow the game window
     */
    public HumanPlayer(Character symbol, boolean isHuman, int game_size, GameWindow gameWindow) {
        super(symbol, isHuman);
        move = new MoveDetails();
        this.game_size = game_size;
        this.gameWindow = gameWindow;
    }

    /**
     * Get the new move details from the human player.
     * Blocks Controller Thread until a move is made on the GUI by the expected Player
     * @param gameBoard the game board
     * @return the move details
     */
    @Override
    public MoveDetails newMove(GameBoard gameBoard)  {
        MoveDetails move;
        while (true) {

            try {
                Thread.sleep(10);
            } catch (IllegalArgumentException illegalArgumentException) {
                System.err.println(illegalArgumentException.getMessage());
            }
            catch (InterruptedException interruptedException)
            {
                System.err.println(interruptedException.getMessage());
                System.exit(0);
            }

            move = gameWindow.getLastMove();
            if (move != null &&  move.getSymbol().equals(getSymbol())  )
                break;
        }
        return move;
    }
}

