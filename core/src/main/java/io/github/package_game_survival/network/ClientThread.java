package io.github.package_game_survival.network;

import io.github.package_game_survival.pantallas.GameScreen;

public class ClientThread extends Thread {

    private GameController gameController;
    private GameScreen gameScreen;

    // --- Métodos para callbacks ---
    public void setGameController(GameController controller) {
        this.gameController = controller;
    }

    public void setGameScreen(GameScreen screen) {
        this.gameScreen = screen;
    }

    // --- Métodos de comunicación ---
    public void sendMessage(String msg) {
        // Aquí enviarías tu mensaje al servidor por UDP/TCP
        System.out.println("Enviando mensaje: " + msg);
    }

    public void sendInput(int dx, int dy) {
        // Aquí enviarías input al servidor
        System.out.println("Enviando input: dx=" + dx + ", dy=" + dy);
    }

    @Override
    public void run() {
        // Loop de escucha al servidor
    }
}
