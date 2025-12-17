package io.github.package_game_survival.entidades.objetos;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import io.github.package_game_survival.entidades.Entidad;

public class Objeto extends Entidad {

    private final int id;
    private TextureRegion textura;
    private Rectangle hitbox;

    public Objeto(int id, TextureRegion textura) {
        super("Objeto_" + id, 0, 0, 32, 32);
        this.id = id;
        this.textura = textura;
    }

    public void actualizar(float x, float y, boolean activo) {
        setPosition(x, y);
        setVisible(activo);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!isVisible()) return;
        batch.draw(textura, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public Rectangle getRectColision() {
        if (hitbox == null) {
            hitbox = new Rectangle(getX(), getY(), getWidth(), getHeight());
        }
        hitbox.setPosition(getX(), getY());
        return hitbox;
    }

    public int getId() {
        return id;
    }
}
