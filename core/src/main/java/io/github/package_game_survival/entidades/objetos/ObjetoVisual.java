package io.github.package_game_survival.entidades.objetos;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.package_game_survival.entidades.seres.EntidadVisual;

public class ObjetoVisual extends EntidadVisual {

    private final TextureRegion region;

    public ObjetoVisual(int id, TextureRegion region, float x, float y, float w, float h) {
        super(id, "Objeto", region, x, y, w, h);
        this.region = region;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(region, getX(), getY(), getWidth(), getHeight());
    }
}
