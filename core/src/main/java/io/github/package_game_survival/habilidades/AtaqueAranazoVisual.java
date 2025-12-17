package io.github.package_game_survival.habilidades;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import io.github.package_game_survival.entidades.mapas.MundoCliente;
import io.github.package_game_survival.managers.Assets;
import io.github.package_game_survival.managers.PathManager;

public class AtaqueAranazoVisual {

    public static void ejecutar(MundoCliente mundo) {
        TextureAtlas atlas =
            Assets.get(PathManager.ARANAZO_ANIMATION, TextureAtlas.class);

        mundo.agregarEfecto(new EfectoAranazoVisual(atlas));
    }
}
