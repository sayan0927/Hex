package GAME.BOARD;

import java.util.LinkedList;
import java.util.Queue;


/**
 * Singleton Object is used to maintain consistent lastScore
 * Pass any GameBoard Instance to compute board
 */
public class ShortestPathBoardEvaluator implements BoardEvaluator {

    Integer lastScore = null;

    public static ShortestPathBoardEvaluator instance = null;

    public static ShortestPathBoardEvaluator getInstance()
    {
        if(instance == null)
            instance = new ShortestPathBoardEvaluator();

        return instance;
    }

    private ShortestPathBoardEvaluator() {}


    public Integer getLastComputedScore()
    {
        return lastScore;
    }

    /**
     * Calculates score of a particular Board State
     * @param gameBoard Board
     * @return score
     */
    public int evaluate(GameBoard gameBoard)
    {
        // rdist = minimum cells R needs to capture to reach end
        int rdist = redShortest(gameBoard);

        // bluedist = minimum cells R needs to capture to reach end
        int bdist = blueShortest(gameBoard);

        // no cells needed to capture for 'B'
        if(bdist==0)
        {
            lastScore = gameBoard.getComputerBestScore();
        }

        // no cells needed to capture for 'B'
        else if(rdist==0)
        {
            lastScore = gameBoard.getHumanBestScore();
        }


        // impossible for either player to reach end at current state;
        else if(rdist==-1 && bdist==-1)
            lastScore=0;

        // 'R' cannot reach end but 'B' has not won yet , scale down score of 'B'
        else if(rdist==-1)
            lastScore= gameBoard.getComputerBestScore()/2;

        // 'B' cannot reach end but 'R' has not won yet , scale down score of 'R'
        else if(bdist==-1)
            lastScore= gameBoard.getHumanBestScore()/2;
        else {
            // Maximizer(AI) will want to increase score,score is better for AI if 'R' human needs many captures and 'B' needs less captures
            // Maximizer will want to increase (rdist - bdist)
            // Minimizer will want to decrease score , score is better for human if 'R' needs less captures and 'B' needs many captures
            // Minimizer will want to decrease ( rdist - dist)
            lastScore = (rdist - bdist);
        }

        return lastScore;
    }



    int blueShortest(GameBoard gameBoard)
    {
        int gamesize = gameBoard.getGameSize();
        Character[][] board = gameBoard.getBoardState();

        int[] dx = new int[]{0,0,1,1,-1,-1};
        int[] dy = new int[]{1,-1,0,-1,0,1};

        boolean[][] leftVisited = new boolean[gamesize][gamesize];
        Queue<int[]> leftQueue = new LinkedList<>();

        boolean[][] rightVisited = new boolean[gamesize][gamesize];
        Queue<int[]> rightQueue = new LinkedList<>();

        for(int l=0;l<gamesize;l++)
        {
            if(board[l][0]=='B')
            {
                leftQueue.add(new int[]{l,0});
                leftVisited[l][0]=true;
            }

            if(board[l][gamesize-1]=='B')
            {
                rightQueue.add(new int[]{l,gamesize-1});
                rightVisited[l][gamesize-1]=true;
            }
        }

        while (!rightQueue.isEmpty())
        {
            int[] top = rightQueue.poll();

            int x = top[0];
            int y = top[1];

            if(leftVisited[x][y])
                return 0;

            for(int i=0;i<dx.length;i++)
            {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if(gameBoard.isValidCellCoordinates(nx,ny) && !rightVisited[nx][ny] && board[nx][ny]=='B')
                {
                    rightVisited[nx][ny]=true;
                    rightQueue.add(new int[]{nx,ny});
                }
            }
        }

        Queue<int[]> traverse = new LinkedList<>();

        while (!leftQueue.isEmpty())
        {
            int[] top = leftQueue.poll();
            traverse.add(top);

            int x = top[0];
            int y = top[1];

            for(int i=0;i<dx.length;i++)
            {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if(gameBoard.isValidCellCoordinates(nx,ny) && !leftVisited[nx][ny] && board[nx][ny]=='B')
                {
                    leftVisited[nx][ny]=true;
                    leftQueue.add(new int[]{nx,ny});
                }
            }
        }

        int dist = 0;

        while (!traverse.isEmpty())
        {

            int size = traverse.size();

            for (int k=0;k<size;k++)
            {
                int[] top = traverse.poll();
                assert top != null;

                int x = top[0];
                int y = top[1];

                for(int l=0;l<dx.length;l++)
                {
                    int nx = x + dx[l];
                    int ny = y + dy[l];

                    if(gameBoard.isValidCellCoordinates(nx,ny) && rightVisited[nx][ny])
                        return dist;

                    if(gameBoard.isValidCellCoordinates(nx,ny) && board[nx][ny]==gameBoard.getEmptyCharecter() && ny==gamesize-1)
                        return dist+1;

                    if(gameBoard.isValidCellCoordinates(nx,ny) && !leftVisited[nx][ny] && board[nx][ny]==gameBoard.getEmptyCharecter())
                    {
                        traverse.add(new int[]{nx,ny});
                        leftVisited[nx][ny]=true;
                    }
                }
            }
            dist++;
        }
        return -1;
    }

    int redShortest(GameBoard gameBoard)
    {
        int gamesize = gameBoard.getGameSize();
        Character[][] board = gameBoard.getBoardState();

        int[] dx = new int[]{0,0,1,1,-1,-1};
        int[] dy = new int[]{1,-1,0,-1,0,1};

        boolean[][] topVisited = new boolean[gamesize][gamesize];
        Queue<int[]> topQueue = new LinkedList<>();

        boolean[][] bottomVisited = new boolean[gamesize][gamesize];
        Queue<int[]> bottomQueue = new LinkedList<>();

        for(int l=0;l<gamesize;l++)
        {
            if(board[0][l]=='R')
            {
                topQueue.add(new int[]{0,l});
                topVisited[0][l]=true;
            }

            if(board[gamesize-1][l]=='R')
            {
                bottomQueue.add(new int[]{gamesize-1,l});
                bottomVisited[gamesize-1][l]=true;
            }
        }

        while (!bottomQueue.isEmpty())
        {
            int[] top = bottomQueue.poll();

            int x = top[0];
            int y = top[1];

            if(topVisited[x][y])
                return 0;

            for(int i=0;i<dx.length;i++)
            {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if(gameBoard.isValidCellCoordinates(nx,ny) && !bottomVisited[nx][ny] && board[nx][ny]=='R')
                {
                    bottomVisited[nx][ny]=true;
                    bottomQueue.add(new int[]{nx,ny});
                }
            }
        }

        Queue<int[]> traverse = new LinkedList<>();

        while (!topQueue.isEmpty())
        {
            int[] top = topQueue.poll();
            traverse.add(top);

            int x = top[0];
            int y = top[1];

            for(int i=0;i<dx.length;i++)
            {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if(gameBoard.isValidCellCoordinates(nx,ny) && !topVisited[nx][ny] && board[nx][ny]=='R')
                {
                    topVisited[nx][ny]=true;
                    topQueue.add(new int[]{nx,ny});
                }
            }
        }

        int dist = 0;

        while (!traverse.isEmpty())
        {

            int size = traverse.size();

            for (int k=0;k<size;k++)
            {
                int[] top = traverse.poll();
                assert top != null;

                int x = top[0];
                int y = top[1];

                for(int l=0;l<dx.length;l++)
                {
                    int nx = x + dx[l];
                    int ny = y + dy[l];

                    if(gameBoard.isValidCellCoordinates(nx,ny) && bottomVisited[nx][ny])
                        return dist;

                    if(gameBoard.isValidCellCoordinates(nx,ny) && board[nx][ny]==gameBoard.getEmptyCharecter() && nx==gamesize-1)
                        return dist+1;

                    if(gameBoard.isValidCellCoordinates(nx,ny) && !topVisited[nx][ny] && board[nx][ny]==gameBoard.getEmptyCharecter())
                    {
                        traverse.add(new int[]{nx,ny});
                        topVisited[nx][ny]=true;
                    }
                }
            }
            dist++;

        }
        return -1;
    }





}
