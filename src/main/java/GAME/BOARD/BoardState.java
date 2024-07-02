package GAME.BOARD;


/**
 * Represents the state of the Board as a String
 * Used by AI algorithms in Transposition Tables
 * One object is a Cell in a transposition table
 */
public class BoardState {


    Character[][] board;
    int depth;

    boolean isMax;

    String stringRepresentation;

    public BoardState(Character[][] board, int depth, boolean isMax) {
        this.board = board;
        this.depth = depth;
        this.isMax = isMax;
        stringRepresentation = getStringRepresentation();

    }

    @Override
    public int hashCode()
    {
       return stringRepresentation.hashCode();
    }

    private String getStringRepresentation()
    {
        StringBuilder stringBuilder = new StringBuilder();

        for(Character[] row:board)
        {
            for(Character character:row)
                stringBuilder.append(character);
            stringBuilder.append('\n');
        }


        stringBuilder.append('\n');
        stringBuilder.append(isMax);
        return stringBuilder.toString();
    }

    @Override
    public String toString()
    {
        return stringRepresentation;
    }






}
