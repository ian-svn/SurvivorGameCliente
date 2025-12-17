package io.github.package_game_survival.managers;

import io.github.package_game_survival.entidades.mapas.EscenarioCliente;
import io.github.package_game_survival.entidades.mapas.MundoCliente;

public class SnapshotParser {

    private final EscenarioCliente escenario;

    public SnapshotParser(EscenarioCliente escenario) {
        this.escenario = escenario;
    }

    public void procesar(String msg) {
        // FORMATO: PLAYER:id:x:y
        if (msg.startsWith("PLAYER:")) {
            String[] p = msg.split(":");
            int id = Integer.parseInt(p[1]);
            float x = Float.parseFloat(p[2]);
            float y = Float.parseFloat(p[3]);

            MundoCliente mundo = escenario.getMundo();
            mundo.actualizarJugador(id, x, y);
        }
    }
}
