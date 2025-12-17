package io.github.package_game_survival.network;

public interface GameController {
    void onConnected();

    void startGame(String mensaje);

    void startGame();
}
