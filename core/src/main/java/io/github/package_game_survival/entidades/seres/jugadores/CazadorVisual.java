package io.github.package_game_survival.entidades.seres.jugadores;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CazadorVisual extends JugadorVisual {

    private final TextureRegion region;
    private final float width = 32;
    private final float height = 48;

    public CazadorVisual(TextureAtlas atlas, float x, float y) {
        super(x, y);
        this.region = atlas.findRegion("idle");
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(region, x, y, width, height);
    }
}
