package io.github.package_game_survival.entidades.seres.animales;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.package_game_survival.entidades.seres.EntidadVisual;

public class VacaVisual extends EntidadVisual {

    public VacaVisual(int id, float x, float y, TextureAtlas atlas) {
        super(
            id,
            "Vaca",
            atlas.findRegion("vaca"), // nombre del frame
            x, y,
            48, 32
        );
    }
}
