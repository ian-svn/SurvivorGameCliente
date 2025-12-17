package io.github.package_game_survival.entidades.seres.jugadores;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class JugadorVisual {

    protected float x;
    protected float y;

    public JugadorVisual(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public abstract void render(SpriteBatch batch);
}
