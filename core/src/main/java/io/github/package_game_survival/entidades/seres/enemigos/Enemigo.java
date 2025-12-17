package io.github.package_game_survival.entidades.seres.enemigos;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Enemigo extends Actor {

    private final TextureRegion sprite;
    private final Rectangle hitbox = new Rectangle();

    public Enemigo(TextureRegion sprite) {
        this.sprite = sprite;
        setSize(32, 32);
    }

    public void actualizarDesdeServidor(float x, float y, int vida) {
        setPosition(x, y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(sprite, getX(), getY(), getWidth(), getHeight());
    }

    public Rectangle getRectColision() {
        hitbox.set(getX(), getY(), getWidth(), getHeight());
        return hitbox;
    }
}
