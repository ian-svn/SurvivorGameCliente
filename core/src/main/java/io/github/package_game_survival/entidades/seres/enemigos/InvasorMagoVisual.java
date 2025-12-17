package io.github.package_game_survival.entidades.seres.enemigos;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import io.github.package_game_survival.entidades.seres.SerVivoVisual;

public class InvasorMagoVisual extends SerVivoVisual {

    public InvasorMagoVisual(
        int id,
        TextureAtlas atlas,
        float x,
        float y
    ) {
        super(id, "Invasor Mago", atlas.findRegion("idle"), x, y, 30, 40);
    }
}
