package io.github.package_game_survival.network;

import com.badlogic.gdx.Gdx;
import io.github.package_game_survival.pantallas.GameScreen;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientThread extends Thread {

    private DatagramSocket socket;
    private InetAddress serverIp;
    private int serverPort = 5555; // Debe coincidir con el Server
    private GameScreen gameScreen;
    private GameController gameController;
    private boolean running = true;

    // Tu ID asignado (se definirá por lógica de llegada, 1 o 2)
    // UDP no tiene conexión persistente, así que usaremos el puerto local como ID temporal
    private int miIDLocal;

    public ClientThread() {
        try {
            // Cliente NO especifica puerto en el constructor (usa uno libre aleatorio)
            socket = new DatagramSocket();
            serverIp = InetAddress.getByName("127.0.0.1"); // CAMBIAR SI ES OTRA PC
            miIDLocal = socket.getLocalPort();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setGameScreen(GameScreen screen) { this.gameScreen = screen; }
    public void setGameController(GameController gc) { this.gameController = gc; }

    // ID temporal para saber "quien soy"
    public int getClienteID() { return miIDLocal; }

    @Override
    public void run() {
        System.out.println("CLIENTE UDP: Iniciado. Escuchando respuestas...");
        while (running) {
            try {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                // Bloqueante: Espera recibir datos del servidor
                socket.receive(packet);
                String msg = new String(packet.getData(), 0, packet.getLength()).trim();

                procesarMensaje(msg);

            } catch (IOException e) {
                if (running) e.printStackTrace();
            }
        }
    }

    private void procesarMensaje(String msg) {
        String[] partes = msg.split(":");
        String comando = partes[0];

        if (comando.equals("CONNECTED")) {
            System.out.println("¡Conexión confirmada por el servidor!");
            if (gameController != null) gameController.onConnected();
        }
        else if (comando.equals("START")) {
            // START:CLASE_J1:CLASE_J2
            System.out.println("¡JUEGO INICIADO!");
            if (gameController != null) gameController.startGame(msg);
        }
        else if (comando.equals("MOVE")) {
            // MOVE : ID_ORIGEN : X : Y
            if (gameScreen != null) {
                try {
                    int idRemoto = Integer.parseInt(partes[1]);
                    float x = Float.parseFloat(partes[2]);
                    float y = Float.parseFloat(partes[3]);

                    // Actualizar en el hilo principal de LibGDX
                    Gdx.app.postRunnable(() -> gameScreen.actualizarRemoto(x, y));
                } catch (Exception e) { }
            }
        }
    }

    public void sendMessage(String msg) {
        if (socket != null && !socket.isClosed()) {
            try {
                byte[] data = msg.getBytes();
                // Enviar siempre al IP y Puerto del Servidor
                DatagramPacket p = new DatagramPacket(data, data.length, serverIp, serverPort);
                socket.send(p);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void terminate() {
        running = false;
        if (socket != null) socket.close();
    }
}
