package io.github.package_game_survival.entidades.mapas;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import io.github.package_game_survival.managers.PathManager;

public class Mapa {

    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;

    public Mapa() {
        map = new TmxMapLoader().load(PathManager.MAPA_BOSQUE);
        renderer = new OrthogonalTiledMapRenderer(map);
    }

    public void render(OrthographicCamera camera) {
        renderer.setView(camera);
        renderer.render();
    }
}
