package io.github.package_game_survival.network;

import com.badlogic.gdx.Gdx;
import io.github.package_game_survival.pantallas.GameScreen;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private GameScreen gameScreen;
    private boolean running = true;

    // ID único asignado por el servidor
    private int clienteID = -1;

    public ClientThread() {
        // Constructor vacío
    }

    public void setGameScreen(GameScreen screen) {
        this.gameScreen = screen;
    }

    public int getClienteID() {
        return clienteID;
    }

    @Override
    public void run() {
        try {
            // Intentamos conectar
            socket = new Socket("localhost", 9999);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            System.out.println("✅ Conectado al servidor con éxito.");

            // Bucle principal de escucha
            while (running) {
                String msg = in.readUTF();
                procesarMensaje(msg);
            }

        } catch (java.net.ConnectException e) {
            // ESTO CAPTURA EL ERROR SI EL SERVER ESTÁ APAGADO
            System.err.println("⚠️ No se encontró el servidor. Jugando en modo OFFLINE.");
            // Aquí podrías poner una variable boolean offline = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void procesarMensaje(String msg) {
        String[] partes = msg.split(":");
        String comando = partes[0];

        // 1. Recibir mi ID al conectarme
        if (comando.equals("ID")) {
            this.clienteID = Integer.parseInt(partes[1]);
            System.out.println("Servidor me asignó ID: " + clienteID);
        }

        // 2. Recibir movimiento de otro jugador
        else if (comando.equals("MOVE")) {
            if (gameScreen != null) {
                try {
                    int idEmisor = Integer.parseInt(partes[1]);
                    float x = Float.parseFloat(partes[2]);
                    float y = Float.parseFloat(partes[3]);

                    // Solo movemos si el ID NO es el nuestro
                    if (idEmisor != this.clienteID) {
                        Gdx.app.postRunnable(() -> {
                            gameScreen.actualizarRemoto(x, y);
                        });
                    }
                } catch (Exception e) {
                    System.err.println("Error procesando MOVE: " + msg);
                }
            }
        }
    }

    public void sendMessage(String msg) {
        if (out != null) {
            try {
                out.writeUTF(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void terminate() {
        running = false;
        try {
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
