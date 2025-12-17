package io.github.package_game_survival.entidades.seres;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.package_game_survival.entidades.Entidad;

public abstract class EntidadVisual extends Entidad {

    protected final int id;
    protected TextureRegion region;

    public EntidadVisual(
        int id,
        String nombre,
        TextureRegion region,
        float x, float y,
        float w, float h
    ) {
        super(nombre, x, y, w, h);
        this.id = id;
        this.region = region;
    }

    public int getId() {
        return id;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (region != null) {
            batch.draw(region, getX(), getY(), getWidth(), getHeight());
        }
    }
}
