package io.github.package_game_survival.entidades.seres.enemigos;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.package_game_survival.entidades.seres.SerVivoVisual;

public class InvasorArqueroVisual extends SerVivoVisual {

    private final TextureRegion region;
    private final float width = 30;
    private final float height = 50;

    public InvasorArqueroVisual(TextureAtlas atlas, float x, float y) {
        super(x, y);
        this.region = atlas.findRegion("idle");
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(region, x, y, width, height);
    }
}
