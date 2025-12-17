package io.github.package_game_survival.entidades.seres;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.package_game_survival.managers.GestorAnimacion;

public abstract class SerVivoVisual extends EntidadVisual {

    protected GestorAnimacion animacion;

    public SerVivoVisual(
        int id,
        String nombre,
        TextureRegion region,
        float x, float y,
        float w, float h
    ) {
        super(id, nombre, region, x, y, w, h);
        this.animacion = new GestorAnimacion();
    }

    public void updateFromServer(float x, float y) {
        setPosition(x, y);
    }
}
