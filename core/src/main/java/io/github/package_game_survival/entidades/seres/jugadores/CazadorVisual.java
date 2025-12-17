package io.github.package_game_survival.entidades.seres.jugadores;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class CazadorVisual extends JugadorVisual {

    public CazadorVisual(
        int id,
        TextureAtlas atlas,
        float x,
        float y
    ) {
        super(
            id,
            "Cazador",
            atlas.findRegion("idle"),
            x, y,
            32, 32
        );
    }
}
