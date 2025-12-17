package io.github.package_game_survival.entidades.seres.jugadores;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.package_game_survival.managers.Assets;
import io.github.package_game_survival.managers.PathManager;

public class GuerreroVisual extends JugadorVisual {

    private final Texture texture;

    public GuerreroVisual(float x, float y) {
        super(x, y);
        texture = Assets.get(PathManager.GUERRERO_TEXTURE, Texture.class);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }
}
