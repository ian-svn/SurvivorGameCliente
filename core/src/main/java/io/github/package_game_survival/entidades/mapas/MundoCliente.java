package io.github.package_game_survival.entidades.mapas;

import java.util.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.package_game_survival.entidades.efectos.EfectoVisual;
import io.github.package_game_survival.entidades.seres.jugadores.*;

public class MundoCliente {

    private final Map<Integer, Jugador> jugadores = new HashMap<>();
    private final List<EfectoVisual> efectos = new ArrayList<>();

    public Map<Integer, Jugador> getJugadores() {
        return jugadores;
    }

    public List<EfectoVisual> getEfectos() {
        return efectos;
    }

    public void agregarEfecto(EfectoVisual e) {
        efectos.add(e);
    }

    public void actualizarJugador(int id, float x, float y) {
        Jugador jugador = jugadores.computeIfAbsent(id,
            k -> new Jugador(k, new GuerreroVisual(x, y), x, y)
        );
        jugador.actualizarDesdeServidor(x, y);
    }

    public void render(SpriteBatch batch) {
        for (Jugador j : jugadores.values()) {
            j.render(batch);
        }
        for (EfectoVisual e : efectos) {
            e.render(batch);
        }
    }
}
