package GAME;

/**
 * Represents a move on a 2D Grid
 */
public class MoveDetails {

    private int row;
    private int  col;
    Character symbol;

    public MoveDetails()
    {

    }



    public MoveDetails(int i, int j, Character symbol) {
        this.row = i;
        this.col = j;
        this.symbol = symbol;
    }
    public void setRow(int x) {
        this.row = x;
    }

    public void setCol(int x) {
        this.col = x;
    }
    public void setSymbol(Character c) {
        this.symbol = c;
    }
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
    public Character getSymbol() {
        return symbol;
    }

    @Override
    public String toString()
    {
        return (this.row+" "+this.col);
    }

}
