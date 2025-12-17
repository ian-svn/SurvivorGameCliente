package io.github.package_game_survival.network;

import java.io.*;
import java.net.Socket;

import io.github.package_game_survival.entidades.mapas.MundoCliente;

public class ClientThread extends Thread {

    private MundoCliente mundo;
    private PrintWriter out;

    public void setMundo(MundoCliente mundo) {
        this.mundo = mundo;
    }

    public void sendMessage(String msg) {
        if (out != null) out.println(msg);
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket("localhost", 5555);
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );

            String line;
            while ((line = in.readLine()) != null) {
                String[] p = line.split(":");
                if (p[0].equals("POS")) {
                    int id = Integer.parseInt(p[1]);
                    float x = Float.parseFloat(p[2]);
                    float y = Float.parseFloat(p[3]);
                    mundo.actualizarJugador(id, x, y);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
