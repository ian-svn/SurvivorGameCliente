package io.github.package_game_survival.network;

public interface GameController {
    void onConnected(); // Para avisar a la UI que conectó
    void startGame();   // Para iniciar el juego
}
