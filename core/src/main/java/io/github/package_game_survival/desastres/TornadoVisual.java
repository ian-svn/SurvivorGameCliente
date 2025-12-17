package io.github.package_game_survival.desastres;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.package_game_survival.entidades.Entidad;

public class TornadoVisual extends Entidad {

    private Animation<TextureRegion> animacion;
    private float stateTime = 0f;

    public TornadoVisual(TextureAtlas atlas) {
        super("Tornado", 0, 0, 60, 90);

        var regiones = atlas.findRegions("tornado");
        this.animacion = new Animation<>(0.1f, regiones, Animation.PlayMode.LOOP);
    }

    public void actualizarDesdeSnapshot(float x, float y) {
        setPosition(x, y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(animacion.getKeyFrame(stateTime), getX(), getY(), getWidth(), getHeight());
    }
}
