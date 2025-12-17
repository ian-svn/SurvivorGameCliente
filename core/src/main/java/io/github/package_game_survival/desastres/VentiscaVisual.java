package io.github.package_game_survival.desastres;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class VentiscaVisual implements Disposable {

    private static class Copo {
        float x, y, vx, vy, size, alpha;
    }

    private final Array<Copo> copos = new Array<>();
    private final Texture texturaCopo;
    private final TextureRegion regionCopo;
    private final Texture texturaOverlay;

    private boolean activo = false;
    private float intensidad = 0f;

    private float ancho, alto;
    private static final int CANTIDAD = 600;

    public VentiscaVisual(float ancho, float alto) {
        this.ancho = ancho;
        this.alto = alto;

        Pixmap p = new Pixmap(2, 2, Pixmap.Format.RGBA8888);
        p.setColor(Color.WHITE);
        p.fill();
        texturaCopo = new Texture(p);
        regionCopo = new TextureRegion(texturaCopo);

        Pixmap o = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        o.setColor(Color.WHITE);
        o.fill();
        texturaOverlay = new Texture(o);

        p.dispose();
        o.dispose();

        for (int i = 0; i < CANTIDAD; i++) {
            Copo c = new Copo();
            reset(c, true);
            copos.add(c);
        }
    }

    private void reset(Copo c, boolean inicio) {
        c.x = MathUtils.random(0, ancho + 200);
        c.y = MathUtils.random(0, alto + 200);
        float v = MathUtils.random(0.5f, 1.5f);
        c.vx = -350 * v;
        c.vy = -150 * v;
        c.size = MathUtils.random(4, 10);
        c.alpha = MathUtils.random(0.3f, 0.8f);
    }

    public void setActivo(boolean a) {
        activo = a;
        intensidad = a ? 0.4f : 0f;
    }

    public void update(float delta) {
        if (!activo) return;
        for (Copo c : copos) {
            c.x += c.vx * delta;
            c.y += c.vy * delta;
            if (c.x < -100 || c.y < -100) reset(c, false);
        }
    }

    public void draw(Batch batch, float camX, float camY, float w, float h) {
        if (!activo) return;

        for (Copo c : copos) {
            batch.setColor(1, 1, 1, c.alpha);
            batch.draw(regionCopo, c.x, c.y, c.size, c.size);
        }

        batch.setColor(0.9f, 0.9f, 1f, intensidad);
        batch.draw(texturaOverlay, camX - w/2, camY - h/2, w, h);

        batch.setColor(Color.WHITE);
    }

    @Override
    public void dispose() {
        texturaCopo.dispose();
        texturaOverlay.dispose();
    }
}
