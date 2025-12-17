package io.github.package_game_survival.entidades.seres.jugadores;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import io.github.package_game_survival.entidades.Entidad;
import io.github.package_game_survival.managers.GestorAnimacion;

/**
 * REPRESENTACIÓN VISUAL DEL JUGADOR EN EL CLIENTE
 * - No tiene vida real
 * - No tiene inventario real
 * - No aplica buffs
 * - Solo renderiza y se mueve según snapshot
 */
public class Jugador extends Entidad {

    private final int id;
    private GestorAnimacion animacion;
    private Rectangle hitbox;

    private float serverX;
    private float serverY;
    private boolean vivo = true;

    public Jugador(int id, TextureRegion texturaInicial) {
        super("Jugador_" + id, 0, 0, 32, 48);
        this.id = id;
        this.animacion = new GestorAnimacion(texturaInicial);
        setColor(Color.WHITE);
    }


    public void actualizarDesdeServidor(float x, float y, boolean vivo) {
        this.serverX = x;
        this.serverY = y;
        this.vivo = vivo;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Interpolación simple para suavizar movimiento
        float lerp = 10f * delta;
        setX(getX() + (serverX - getX()) * lerp);
        setY(getY() + (serverY - getY()) * lerp);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!vivo) return;

        TextureRegion frame = animacion.getFrame();
        if (frame == null) return;

        Color c = getColor();
        batch.setColor(c.r, c.g, c.b, parentAlpha);
        batch.draw(frame, getX(), getY(), getWidth(), getHeight());
        batch.setColor(Color.WHITE);
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

    public boolean isVivo() {
        return vivo;
    }
}
