package io.github.package_game_survival.entidades.seres.animales;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.package_game_survival.entidades.seres.EntidadVisual;

public abstract class AnimalVisual extends EntidadVisual {

    protected int id;

    public AnimalVisual(int id, String tipo, TextureRegion region, float x, float y, float w, float h) {
        super(id, tipo, region, x, y, w, h);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public abstract void updateFromServer(float x, float y);
}
