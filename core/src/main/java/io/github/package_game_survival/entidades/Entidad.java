package io.github.package_game_survival.entidades;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Entidad extends Actor {

    protected String nombre;
    protected Rectangle hitbox = new Rectangle();

    public Entidad(String nombre, float x, float y, float ancho, float alto) {
        this.nombre = nombre;
        setBounds(x, y, ancho, alto);
        setColor(Color.WHITE);
    }

    public void setPos(float x, float y) {
        setPosition(x, y);
    }

    public void setTam(float ancho, float alto) {
        setSize(ancho, alto);
    }

    @Override
    public abstract void draw(Batch batch, float parentAlpha);

    public Rectangle getRectColision() {
        hitbox.set(getX(), getY(), getWidth(), getHeight());
        return hitbox;
    }

    public String getNombre() {
        return nombre;
    }
}
