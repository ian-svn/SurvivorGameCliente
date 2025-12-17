package io.github.package_game_survival.entidades.seres.enemigos;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import io.github.package_game_survival.entidades.seres.SerVivoVisual;

public class InvasorArqueroVisual extends SerVivoVisual {

    public InvasorArqueroVisual(
        int id,
        TextureAtlas atlas,
        float x,
        float y
    ) {
        super(id, "Invasor Arquero", atlas.findRegion("idle"), x, y, 30, 50);
    }
}
