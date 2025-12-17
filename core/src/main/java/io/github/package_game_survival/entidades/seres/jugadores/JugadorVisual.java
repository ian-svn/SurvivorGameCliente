package io.github.package_game_survival.entidades.seres.jugadores;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.package_game_survival.entidades.seres.SerVivoVisual;

public abstract class JugadorVisual extends SerVivoVisual {

    public JugadorVisual(
        int id,
        String nombre,
        TextureRegion region,
        float x,
        float y,
        float w,
        float h
    ) {
        super(id, nombre, region, x, y, w, h);
    }
}
