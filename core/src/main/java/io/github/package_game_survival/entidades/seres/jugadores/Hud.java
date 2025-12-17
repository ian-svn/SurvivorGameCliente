package io.github.package_game_survival.entidades.seres.jugadores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Hud {

    private final BitmapFont font = new BitmapFont();

    private int dia;
    private int hora;
    private int minuto;
    private String desastre = "";

    public void setTiempo(int dia, int hora, int minuto) {
        this.dia = dia;
        this.hora = hora;
        this.minuto = minuto;
    }

    public void setDesastre(String desastre) {
        this.desastre = desastre;
    }

    public void draw(Batch batch) {
        font.draw(batch, "Dia: " + dia, 10, Gdx.graphics.getHeight() - 10);
        font.draw(batch, "Hora: " + hora + ":" + minuto, 10, Gdx.graphics.getHeight() - 30);
        font.draw(batch, "Desastre: " + desastre, 10, Gdx.graphics.getHeight() - 50);
    }
}
