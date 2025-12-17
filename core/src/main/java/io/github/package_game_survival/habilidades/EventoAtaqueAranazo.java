package io.github.package_game_survival.habilidades.eventos;

import com.badlogic.gdx.graphics.Color;

public class EventoAtaqueAranazo {

    public final int idAtacante;
    public final float x;
    public final float y;
    public final float rotacion;
    public final float rango;
    public final float ancho;
    public final Color color;

    public EventoAtaqueAranazo(
        int idAtacante,
        float x,
        float y,
        float rotacion,
        float rango,
        float ancho,
        Color color
    ) {
        this.idAtacante = idAtacante;
        this.x = x;
        this.y = y;
        this.rotacion = rotacion;
        this.rango = rango;
        this.ancho = ancho;
        this.color = color;
    }
}
