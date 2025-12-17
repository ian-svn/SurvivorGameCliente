package io.github.package_game_survival.entidades.seres.jugadores;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.package_game_survival.entidades.seres.SerVivoVisual;

public class Jugador extends SerVivoVisual {

    private final int id;
    private final JugadorVisual visual;

    public Jugador(int id, JugadorVisual visual, float x, float y) {
        super(x, y);
        this.id = id;
        this.visual = visual;
    }

    public int getId() {
        return id;
    }

    public void actualizarDesdeServidor(float x, float y) {
        this.x = x;
        this.y = y;
        visual.setX(x);
        visual.setY(y);
    }

    @Override
    public void render(SpriteBatch batch) {
        visual.render(batch);
    }
}
