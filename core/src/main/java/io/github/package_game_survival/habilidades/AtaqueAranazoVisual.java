package io.github.package_game_survival.habilidades;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import io.github.package_game_survival.entidades.efectos.EfectoVisual;
import io.github.package_game_survival.entidades.mapas.MundoCliente;
import io.github.package_game_survival.habilidades.eventos.EventoAtaqueAranazo;
import io.github.package_game_survival.managers.Assets;
import io.github.package_game_survival.managers.Audio.AudioManager;
import io.github.package_game_survival.managers.PathManager;

public class AtaqueAranazoVisual {

    public static void render(EventoAtaqueAranazo e, MundoCliente mundo) {

        TextureAtlas atlas =
            Assets.get(PathManager.ARANAZO_ANIMATION, TextureAtlas.class);

        if (atlas == null) return;

        EfectoVisual efecto =
            new EfectoVisual(atlas, "aranazo", 0.05f, false);

        efecto.setPosition(e.x, e.y - (e.ancho / 2f));
        efecto.setOrigin(0, e.ancho / 2f);
        efecto.setSize(e.rango, e.ancho);
        efecto.setRotation(e.rotacion);
        efecto.setColor(e.color);

        mundo.agregarActor(efecto);

        try {
            AudioManager.getControler().playSound("ataque");
        } catch (Exception ignored) {}
    }
}
