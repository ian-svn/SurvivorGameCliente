package io.github.package_game_survival.entidades.seres.animales;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import io.github.package_game_survival.entidades.seres.EntidadVisual;

public class OvejaVisual extends EntidadVisual {

    public OvejaVisual(int id, float x, float y, TextureAtlas atlas) {
        super(
            id,
            "Oveja",
            atlas.findRegion("oveja"),
            x, y,
            42, 32
        );
    }
}
