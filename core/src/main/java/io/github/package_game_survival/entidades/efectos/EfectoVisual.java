package io.github.package_game_survival.entidades.efectos;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class EfectoVisual {

    private final Sprite sprite;

    public float x;
    public float y;
    public float ancho;
    public float rango;
    public float rotacion;
    public Color color = Color.WHITE;

    public EfectoVisual(TextureAtlas atlas, String region, float scale, boolean flipX) {
        sprite = atlas.createSprite(region);
        sprite.setScale(scale);

        if (flipX) sprite.flip(true, false);

        ancho = sprite.getHeight();
        rango = sprite.getWidth();
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        sprite.setPosition(x, y);
    }

    public void setOrigin(float ox, float oy) {
        sprite.setOrigin(ox, oy);
    }

    public void setSize(float w, float h) {
        sprite.setSize(w, h);
    }

    public void setRotation(float rot) {
        this.rotacion = rot;
        sprite.setRotation(rot);
    }

    public void setColor(Color c) {
        this.color = c;
        sprite.setColor(c);
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
