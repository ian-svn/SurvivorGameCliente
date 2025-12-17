package io.github.package_game_survival.entidades.mapas;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EscenarioCliente {

    private final MundoCliente mundo;
    private final Mapa mapa;

    public EscenarioCliente(MundoCliente mundo) {
        this.mundo = mundo;
        this.mapa = new Mapa();
    }

    public MundoCliente getMundo() {
        return mundo;
    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        mapa.render(camera);
        mundo.render(batch);
        batch.end();
    }
}
