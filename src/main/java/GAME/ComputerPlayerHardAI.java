package GAME;

import java.util.*;

public class ComputerPlayerHardAI extends Player {

    protected final Character computerPlayer ;
    protected final Character humanPlayer ;
    protected final GameBoard mainGameBoard;
    protected GameBoard gameBoardForMoves; // this board is used to make moves by minimax, so that original board doesnt change

    private int depthLimit=15;

    protected final int gamesize;


    ComputerPlayerHardAI(Character computerPlayer, Character humanPlayer, boolean b, int gamesize, GameBoard gameBoard) {
        super(computerPlayer, b);
        this.mainGameBoard = gameBoard;
        this.gamesize=gamesize;
        this.computerPlayer=computerPlayer;
        this.humanPlayer=humanPlayer;

        if(gamesize==5)
            depthLimit=5;

        if(gamesize==7)
            depthLimit=3;

        if(gamesize==9)
            depthLimit=3;

      //  depthLimit=1;

    }

    @Override
    public MoveDetails newMove(GameBoard gameBoard) {

     //   this.gameBoardForMoves=new GameBoard(gameBoard);
        this.gameBoardForMoves=gameBoard;

  //      if(gameBoardForMoves.getTotalMoves()<=1)
     //       return new ComputerPlayerEasyAI(this.computerPlayer,false,this.gamesize).newMove(gameBoardForMoves);
        MoveDetails latestMove= findBestMove(gameBoardForMoves);
        return latestMove;
    }

    protected int minimax(GameBoard  boardForMoves, int depth, boolean isMax,int alpha,int beta)  {
        //System.out.println("at depth "+depth);
        
        int score = boardForMoves.evaluate(false);

        if(depth==depthLimit)
            return score;

        if (score == gameBoardForMoves.computerBestScore)  // If Maximizer has won the game return his/her evaluated score
            return score-depth*10;

        if (score == gameBoardForMoves.humanBestScore) // If Minimizer has won the game return his/her evaluated score
            return score+depth*10;

        if (!boardForMoves.movesLeft())   // If there are no more moves and no winner then it is a tie
            return 0;

        Set<Integer> remainingMoves = new HashSet<>(boardForMoves.getRemainingMoves());
        // If this maximizer's move
        int best;
        if (isMax)
        {
            best = -10000;
            for(int move:remainingMoves)
            {
                boardForMoves.makeMove(move,computerPlayer);
                best = Math.max(best,minimax(boardForMoves,depth+1,false,alpha,beta));
                boardForMoves.undoMove(move);

                if(best==gameBoardForMoves.computerBestScore)
                    return best;

                alpha = Math.max(alpha,best);
                if(beta<=alpha)
                    break;
            }
        }
        // If this minimizer's move
        else
        {
            best = 10000;
            for(int move:remainingMoves)
            {
                boardForMoves.makeMove(move,humanPlayer);
                best = Math.min(best,minimax(boardForMoves,depth+1,true,alpha,beta));
                boardForMoves.undoMove(move);

                if(best==gameBoardForMoves.humanBestScore)
                    return best;

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

        //    if(moveVal==gameBoardForMoves.computerBestScore)
          //      return new MoveDetails(i,j,computerPlayer);

            if(moveScores.containsKey(moveVal))
                moveScores.get(moveVal).add(new MoveDetails(i,j,computerPlayer));
            else
            {
                List<MoveDetails> set = new ArrayList<>(150);
                set.add(new MoveDetails(i,j,computerPlayer));
                moveScores.put(moveVal,set);
            }
        }


        System.out.println(moveScores);
        List<MoveDetails> bestMoves = moveScores.get(bestVal);
        int randIdx = new Random().nextInt(bestMoves.size());
        return bestMoves.get(randIdx);

    }


}
