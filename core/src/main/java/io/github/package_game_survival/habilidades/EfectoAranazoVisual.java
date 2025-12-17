package io.github.package_game_survival.habilidades;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import io.github.package_game_survival.entidades.efectos.EfectoVisual;

public class EfectoAranazoVisual extends EfectoVisual {

    public EfectoAranazoVisual(TextureAtlas atlas) {
        super(atlas, "aranazo", 0.05f, false);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
    }
}
