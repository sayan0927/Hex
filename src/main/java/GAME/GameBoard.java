package GAME;


import java.util.*;
import java.util.concurrent.Semaphore;

public class GameBoard {

    public int computerBestScore=1000;
    public int humanBestScore=-1000;
    protected Character[][] board;
    public final int gamesize;
    public final Character empty_charecter = '-';
    private String winner = "None";
    private final Character you_playing_as;
    private int totalMoves;
    private int firstPlayerMoves;
    private int secondPlayerMoves;
    private Set<Integer> remainingMoves;

    GameBoard(GameBoard g)
    {
      //  this.board=g.getBoardState();
        this.board=g.board;
        this.gamesize=g.gamesize;
        this.you_playing_as = g.you_playing_as;
        this.totalMoves =g.totalMoves;
        this.firstPlayerMoves=g.firstPlayerMoves;
        this.secondPlayerMoves=g.secondPlayerMoves;
        this.remainingMoves = new HashSet<>(g.getRemainingMoves());
    }
    GameBoard(int gamesize) {
        board = new Character[gamesize][gamesize];
        you_playing_as = 'R';
        this.gamesize = gamesize;
        this.totalMoves=0;
        this.firstPlayerMoves=0;
        this.secondPlayerMoves=0;
        setBoardReady();
        setRemainingMoves();
    }
    public int getTotalMoves() {
        return totalMoves;
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

    public Set<Integer> getRemainingMoves()
    {
        return this.remainingMoves;
    }
    private void setRemainingMoves()
    {
        this.remainingMoves=new HashSet<>();

        for(int i=0;i<gamesize*gamesize;i++)
            this.remainingMoves.add(i);
    }
    public boolean check_if_game_ended() {

       int score = evaluate(true);
        System.out.println(score);
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
    protected int evaluate(boolean print)
    {
        //optimisation
       // if(totalMoves<gamesize)
         //   return 0;

        int rdist = redShortest();
        int bdist = blueShortest();

        if(print)
            System.out.println(rdist+" yo "+bdist);

        if(bdist==0)
            return computerBestScore;

        if(rdist==0)
            return humanBestScore;

        if(rdist==-1 && bdist==-1)
            return 0;

        if(rdist==-1)
            return computerBestScore/3;

        if(bdist==-1)
            return humanBestScore/3;

        return (rdist-bdist)*10;
    }
    int blueShortest()
    {
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

                if(isValid(nx,ny) && !rightVisited[nx][ny] && board[nx][ny]=='B')
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

                if(isValid(nx,ny) && !leftVisited[nx][ny] && board[nx][ny]=='B')
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

                    if(isValid(nx,ny) && rightVisited[nx][ny])
                        return dist;

                    if(isValid(nx,ny) && board[nx][ny]==empty_charecter && ny==gamesize-1)
                        return dist+1;

                    if(isValid(nx,ny) && !leftVisited[nx][ny] && board[nx][ny]==empty_charecter)
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

    int redShortest()
    {
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

                if(isValid(nx,ny) && !bottomVisited[nx][ny] && board[nx][ny]=='R')
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

                if(isValid(nx,ny) && !topVisited[nx][ny] && board[nx][ny]=='R')
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

                    if(isValid(nx,ny) && bottomVisited[nx][ny])
                        return dist;

                    if(isValid(nx,ny) && board[nx][ny]==empty_charecter && nx==gamesize-1)
                        return dist+1;

                    if(isValid(nx,ny) && !topVisited[nx][ny] && board[nx][ny]==empty_charecter)
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

    boolean isValid(int i,int j)
    {
        return i>=0 && i<board.length && j>=0 && j<board[0].length;
    }

    boolean movesLeft()
    {
        return this.remainingMoves.size()>0;
    }
    public String getWinner() {

        return winner;
    }
    public void setBoardReady() {
        for (int row = 0; row < gamesize; row++) {
            for (int col = 0; col < gamesize; col++) {
                this.board[row][col] = empty_charecter;
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

    private void undoMove(int i,int j)
    {
        board[i][j]=this.empty_charecter;
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
        return you_playing_as;
    }

    private Character[][] make_copy(Character[][] board) {
        Character[][] copy = new Character[gamesize][gamesize];

        for (int row = 0; row < gamesize; row++) {
            System.arraycopy(board[row], 0, copy[row], 0, gamesize);
        }

        return copy;

    }

    public void printBoard(Character[][] board)
    {
        for (Character[] line:board)
            System.out.println(Arrays.toString(line));
    }

}





