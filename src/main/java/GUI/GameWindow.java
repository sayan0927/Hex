package GUI;

import GAME.MoveDetails;

import java.util.concurrent.Semaphore;

public interface GameWindow {
    MoveDetails getLastMove();
    void setCurrentSymbol(Character currentSymbol);
     void setSemaphore(Semaphore semaphore);
    void showInvalidMoveError();
    void updateWindow();
    void showWinner(String text);
    void showDraw();
    void disposeWindow();

}
