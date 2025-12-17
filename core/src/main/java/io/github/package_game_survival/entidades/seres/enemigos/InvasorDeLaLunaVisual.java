package io.github.package_game_survival.entidades.seres.enemigos;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import io.github.package_game_survival.entidades.seres.SerVivoVisual;

public class InvasorDeLaLunaVisual extends SerVivoVisual {

    public InvasorDeLaLunaVisual(
        int id,
        TextureAtlas atlas,
        float x,
        float y
    ) {
        super(
            id,
            "Invasor De La Luna",
            atlas.findRegion("idle"),
            x, y,
            30, 40
        );
    }
}
