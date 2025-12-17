package io.github.package_game_survival.entidades.mapas;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import io.github.package_game_survival.entidades.seres.EntidadVisual;
import io.github.package_game_survival.entidades.seres.jugadores.Hud;

import java.util.HashMap;
import java.util.Map;

public class EscenarioCliente {

    private final OrthogonalTiledMapRenderer renderer;
    private final Hud hud;

    private final Map<Integer, EntidadVisual> jugadores = new HashMap<>();
    private final Map<Integer, EntidadVisual> objetos = new HashMap<>();

    public EscenarioCliente(TiledMap map) {
        this.renderer = new OrthogonalTiledMapRenderer(map);
        this.hud = new Hud();
    }

    // ================= GETTERS =================

    public Map<Integer, EntidadVisual> getJugadores() {
        return jugadores;
    }

    public Map<Integer, EntidadVisual> getObjetos() {
        return objetos;
    }

    public Hud getHud() {
        return hud;
    }

    public void render(Batch batch) {
        renderer.render();

        batch.begin();

        for (EntidadVisual j : jugadores.values()) {
            j.draw(batch, 1f);
        }

        for (EntidadVisual o : objetos.values()) {
            o.draw(batch, 1f);
        }

        batch.end();

        hud.draw(batch);
    }

    public void dispose() {
        renderer.dispose();
    }
}
