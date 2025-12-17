package io.github.package_game_survival.entidades.seres;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class SerVivoVisual {

    protected float x;
    protected float y;

    public SerVivoVisual(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public abstract void render(SpriteBatch batch);
}
