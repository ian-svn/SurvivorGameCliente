package io.github.package_game_survival.entidades.seres.animales;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import io.github.package_game_survival.entidades.seres.EntidadVisual;

public class JabaliVisual extends EntidadVisual {

    public JabaliVisual(int id, float x, float y, TextureAtlas atlas) {
        super(
            id,
            "Jabali",
            atlas.findRegion("jabali"),
            x, y,
            54, 42
        );
    }
}
