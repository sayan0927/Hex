Welcome to the HEX Board Game project! 
This Java-based implementation of the classic HEX game features both human and AI players. 
Developers can easily extend the game's functionality by creating custom AI players and board evaluators.

# Features
- **AI Players**: Compete against AI players that use the minimax algorithm with alpha-beta pruning. 
  The current AI relies on simple heuristics for board evaluation due to the large state space and branching factor.
- **Custom AI Players**: Create your own AI players by extending the abstract Player class and overriding the newMove() method.
- **Custom Board Evaluators**: Implement your own board evaluation logic by implementing the BoardEvaluator interface and its evaluate method.
- **Runnable JAR**: The game can be run from the JAR file in the out folder, provided you have a Java Runtime Environment (JRE) installed
- **Portable Version**: A portable version that does not require a pre-installed JRE will be released soon.


# Running JAR ( requires installed JRE ) 

- **Windows**: Simply click the JAR , if it doesnt work try to run run.bat
- **Linux**: cd to folder containing the JAR.
             Enter "java -jar Hex.jar" in terminal  

# Creating Custom AI Players

- **Extend the Player Class**: Create a new class that extends the abstract Player class.
- **Override the newMove() Method**: Implement your custom move logic by overriding the newMove() method.


```  public class YourAIPlayer extends Player {
    @Override
    public MoveDetails newMove(GameBoard gameBoard) {
        //    IMPORTANT! ENSURE gameBoard is unchanged after the method finishes

        //    Your custom AI logic here
        //    You may use score provided by BoardEvaluator.evaluate() to measure goodness of a move
        //    ( Incase of Minimax Based Players ,  make Computer Player as Maximizer.
        //    ( Minimizer will try to reduce evaluated score , Maximizer will try to increase evaluated score)
        //    ( Current AI Implementation has Computer Player as Maximizer  )

        MoveDetails bestMove = .....;
        return bestMove;
    }
}
```

# Creating Custom Board Evaluators

- **Implement the BoardEvaluator Interface**: Create a new class that implements the BoardEvaluator interface.
- **Implement the evaluate() Method**: Provide your custom board evaluation logic in the evaluate() method.


```  public class YourEvaluator implements BoardEvaluator  {
    
     public int evaluate(GameBoard gameBoard) {
        //  IMPORTANT! ENSURE gameBoard is unchanged after the method finishes


        // return an integer representing board score for the provided GameBoard instance
        // if Red player(Human) has won 
        //      return gameBoard.getHumanBestScore() (-1000)   
        // if Blue player has(2nd Human/AI) won
        //      return gameBoard.getComputerBestScore() (1000) , Remember Computer Player is Maximizer for Current AI Implementation

        // Your custom evaluation logic here
        // 
        int boardScore = .....;
        return boardScore;
    }
}
```

# Known Issues
 - **GUI Bugs**: Some GUI elements on the Welcome Page Right Panel does not appear after restarting the game.
 - **AI issues**: Due to large state space and simple heuristics used, the AI player may not perform well in game sizes larger than 6x6.
