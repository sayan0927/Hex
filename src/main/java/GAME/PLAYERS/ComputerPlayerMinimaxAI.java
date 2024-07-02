package GAME.PLAYERS;

import GAME.BOARD.BoardEvaluator;
import GAME.BOARD.GameBoard;
import GAME.BOARD.BoardState;
import GAME.MoveDetails;

import java.util.*;

public class ComputerPlayerMinimaxAI extends Player {

    protected final Character computerPlayer ;
    protected final Character humanPlayer ;
    protected GameBoard gameBoardForMoves; // this board is used to make moves by minimax, so that original board doesnt change

    private int depthLimit=15;

    protected final int gamesize;

    Map<String,Integer> transpositionTable;
    Map<String,Integer> stateCount;

    BoardEvaluator evaluator;


    public ComputerPlayerMinimaxAI(Character computerPlayer, Character humanPlayer, boolean isHuman, int gamesize, GameBoard gameBoard, BoardEvaluator evaluator) {
        super(computerPlayer, isHuman);
        this.gameBoardForMoves= gameBoard;
        this.gamesize=gamesize;
        this.computerPlayer=computerPlayer;
        this.humanPlayer=humanPlayer;
        this.transpositionTable=new LinkedHashMap<>();
        this.stateCount=new LinkedHashMap<>();
        this.evaluator=evaluator;
        depthLimit=3;

    }

    @Override
    public MoveDetails newMove(GameBoard gameBoard) {



      //  if(gameBoard.getCountOfMovesMade()==1)
        //    return randomMoveOnColumn(0);

        //else if(gameBoard.getCountOfMovesMade()==3)
          //  return randomMoveOnColumn(gamesize-1);

        MoveDetails latestMove = findBestMove(gameBoardForMoves);
        return latestMove;
    }

    private MoveDetails randomMoveOnColumn(int column)
    {
        int col = column,row=-1;
        MoveDetails temp = new MoveDetails(row,col,computerPlayer);
        while (true)
        {
            row = new Random().nextInt(0,gamesize);
            temp.setRow(row);
            temp.setCol(col);

            if(gameBoardForMoves.isValidInput(temp))
                break;
        }
        return temp;
    }

    protected int minimax(GameBoard boardForMoves, int depth, boolean isMax, int alpha, int beta)  {


        int score =     evaluator.evaluate(boardForMoves);

        // If Maximizer has won the game return his/her evaluated score
        // score reduces if Maximizer won at larger depths ( Higher score is better since Maximizer)
        if (score == gameBoardForMoves.getComputerBestScore())
            return score-depth*10;

        // If Minimizer has won the game return his/her evaluated score
        // score increases if Minimizer won at larger depths ( Lower score is better since Minimizer)
        if (score == gameBoardForMoves.getHumanBestScore()) // If Minimizer has won the game return his/her evaluated score
            return score+depth*10;

        if (!boardForMoves.movesLeft())   // If there are no more moves and no winner then it is a tie
            return 0;

        if(depth==depthLimit)
            return score;

        Set<Integer> remainingMoves = new HashSet<>(boardForMoves.getRemainingMoves());
        // If this maximizer's move
        int best;
        if (isMax)
        {
            best = -10000;

            //first entry holds move and second holds score of move
            ArrayList<int[]> topMaxMoves = new ArrayList<>();

            for(int move:remainingMoves)
            {
                boardForMoves.makeMove(move,computerPlayer);
                int movescore =  evaluator.evaluate(boardForMoves);//  gameBoardForMoves.evaluate();
                topMaxMoves.add(new int[]{move,movescore});
                boardForMoves.undoMove(move);

            }

            topMaxMoves.sort((o1, o2) -> o2[1]-o1[1]);
            for(int[] movedetail:topMaxMoves)
            {
                int move = movedetail[0];
                boardForMoves.makeMove(move,computerPlayer);
                best = Math.max(best,minimax(boardForMoves,depth+1,false,alpha,beta));
                boardForMoves.undoMove(move);

                if(best== gameBoardForMoves.getComputerBestScore())
                    break;

                alpha = Math.max(alpha,best);
                if(beta<=alpha)
                    break;
            }
        }
        // If this minimizer's move
        else
        {
            best = 10000;


            ArrayList<int[]> topMinMoves = new ArrayList<>();
            for(int move:remainingMoves)
            {
                boardForMoves.makeMove(move,computerPlayer);
                int movescore =  evaluator.evaluate(boardForMoves);//  gameBoardForMoves.evaluate();
                topMinMoves.add(new int[]{move,movescore});
                boardForMoves.undoMove(move);

            }

            topMinMoves.sort((o1, o2) -> o1[1]-o2[1]);

            for(int[] movedetail:topMinMoves)
            {
                int move = movedetail[0];
                boardForMoves.makeMove(move,humanPlayer);
                best = Math.min(best,minimax(boardForMoves,depth+1,true,alpha,beta));
                boardForMoves.undoMove(move);

                if(best== gameBoardForMoves.getHumanBestScore())
                   break;

                beta = Math.min(beta,best);
                if(beta<=alpha)
                    break;
            }
        }

        return best;
    }

    protected MoveDetails findBestMove(GameBoard boardForMoves)  {
        int bestVal = -10000;


        Map<Integer, List<MoveDetails>> moveScores = new HashMap<>();
        Set<Integer> remainingMoves = new HashSet<>(boardForMoves.getRemainingMoves());

        for(int move:remainingMoves)
        {
            boardForMoves.makeMove(move,computerPlayer);
            int moveVal = minimax(boardForMoves,0,false,Integer.MIN_VALUE,Integer.MAX_VALUE);
            boardForMoves.undoMove(move);
            bestVal = Math.max(bestVal,moveVal);

            int i = move / gamesize;
            int j = move - gamesize * i;

            if(moveScores.containsKey(moveVal))
                moveScores.get(moveVal).add(new MoveDetails(i,j,computerPlayer));
            else
            {
                List<MoveDetails> list = new ArrayList<>(150);
                list.add(new MoveDetails(i,j,computerPlayer));
                moveScores.put(moveVal,list);
            }
        }

        List<MoveDetails> bestMoves = moveScores.get(bestVal);
        int randIdx = new Random().nextInt(bestMoves.size());
        return bestMoves.get(randIdx);

    }


}
