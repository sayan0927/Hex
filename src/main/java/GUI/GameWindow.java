package GUI;

import GAME.MoveDetails;

import java.util.concurrent.Semaphore;

public interface GameWindow {
    MoveDetails getLastMove();
    void setNextPlayerSymbol(Character nextPlayerSymbol);
    void showInvalidMoveError();
    void updateWindow();
    void showWinner(String text);
    void showDraw();
    void disposeWindow();

}
