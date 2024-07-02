package GAME.BOARD;


import GAME.MoveDetails;

import java.util.*;

public class GameBoardImpl implements GameBoard {


    public int computerBestScore = 1000;
    public int humanBestScore = -1000;
    protected Character[][] board;
    public final int gamesize;
    public final Character emptyCharecter = '-';
    private String winner = "None";
    private final Character humanPlayerCharecter;
    private int totalMoves;
    private int firstPlayerMoves;
    private int secondPlayerMoves;
    private Set<Integer> remainingMoves;
    BoardEvaluator evaluator;



    public int getCountOfMovesMade()
    {
        return gamesize*gamesize-remainingMoves.size();
    }

    public GameBoardImpl(GameBoardImpl g)
    {
      //  this.board=g.getBoardState();
        this.board=g.board;
        this.gamesize=g.gamesize;
        this.humanPlayerCharecter = g.humanPlayerCharecter;
        this.totalMoves =g.totalMoves;
        this.firstPlayerMoves=g.firstPlayerMoves;
        this.secondPlayerMoves=g.secondPlayerMoves;
        this.remainingMoves = new HashSet<>(g.getRemainingMoves());

    }

    public GameBoardImpl(int gamesize, Character humanPlayerCharecter,BoardEvaluator evaluator) {
        board = new Character[gamesize][gamesize];
        this.humanPlayerCharecter = humanPlayerCharecter;
        this.gamesize = gamesize;
        this.totalMoves=0;
        this.firstPlayerMoves=0;
        this.secondPlayerMoves=0;
        this.evaluator = evaluator;
        setBoardReady();
        setRemainingMoves();
    }

    public int getGameSize()
    {
        return gamesize;
    }

    @Override
    public BoardEvaluator getEvaluator() {
        return this.evaluator;
    }

    public void incrementFirstPlayerMoves()
    {
        this.firstPlayerMoves++;
        this.totalMoves++;
    }
    public void incrementSecondPlayerMoves()
    {
        this.secondPlayerMoves++;
        this.totalMoves++;
    }

    public Character getCellState(int i,int j)
    {
        return board[i][j];
    }

    public Set<Integer> getRemainingMoves()
    {
        return this.remainingMoves;
    }

    @Override
    public int getComputerBestScore() {
        return computerBestScore;
    }

    @Override
    public int getHumanBestScore() {
        return humanBestScore;
    }

    private void setRemainingMoves()
    {
        this.remainingMoves=new HashSet<>();

        for(int i=0;i<gamesize*gamesize;i++)
            this.remainingMoves.add(i);
    }
    public boolean checkIfGameEnded() {

       int score = evaluate();

        if(score==computerBestScore)
        {
            winner="B";
            return true;
        }
        else if(score==humanBestScore)
        {
            winner="R";
            return true;
        }

        return false;
    }

    public int evaluate()
    {
       return evaluator.evaluate(this);

    }

    public boolean isValidCellCoordinates(int i, int j)
    {
        return i>=0 && i<board.length && j>=0 && j<board[0].length;
    }

    @Override
    public Character getEmptyCharecter() {
        return emptyCharecter;
    }


    public boolean movesLeft()
    {
        return this.remainingMoves.size()>0;
    }
    public String getWinner() {

        return winner;
    }
    public void setBoardReady() {
        for (int row = 0; row < gamesize; row++) {
            for (int col = 0; col < gamesize; col++) {
                this.board[row][col] = emptyCharecter;
            }
        }
    }

    private void makeMove(int i,int j,Character playerCharacter)
    {
        board[i][j]=playerCharacter;
    }

    public void  makeMove(int n,Character playerCharecter)
    {
        int i = n / gamesize;
        int j = n - gamesize * i;

        makeMove(i,j,playerCharecter);
        remainingMoves.remove(n);
    }

    public void makeMove(MoveDetails move)
    {
        int n = move.getRow() * gamesize + move.getCol();
        makeMove(move.getRow(),move.getCol(),move.getSymbol());
        remainingMoves.remove(n);
    }



    private void undoMove(int i,int j)
    {
        board[i][j]=this.emptyCharecter;
    }

    public void undoMove(MoveDetails move)
    {
        undoMove(move.getRow(),move.getCol());
    }

    public void undoMove(int n)
    {
        int i = n / gamesize;
        int j = n - gamesize * i;

        undoMove(i,j);
        remainingMoves.add(n);
    }

    public boolean isValidInput(MoveDetails move) {

        return this.remainingMoves.contains(move.getRow() * gamesize + move.getCol());
    }

    public Character[][] getBoardState() {

        return make_copy(board);
    }

    public Character human_playing_as() {
        return humanPlayerCharecter;
    }

    private Character[][] make_copy(Character[][] board) {
        Character[][] copy = new Character[gamesize][gamesize];

        for (int row = 0; row < gamesize; row++) {
            System.arraycopy(board[row], 0, copy[row], 0, gamesize);
        }

        return copy;

    }

}





