package io.github.package_game_survival.standards;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.TooltipManager;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import io.github.package_game_survival.managers.Assets;
import io.github.package_game_survival.managers.PathManager;

public class TooltipStandard {

    private static final Skin skinTooltip = Assets.get(PathManager.TOOLTIP, Skin.class);
    private static final TooltipManager tm = TooltipManager.getInstance();

    static {
        tm.animations = false;
        tm.initialTime = 0.1f;       // Tiempo antes de que aparezca el tooltip
        tm.subsequentTime = 0f;      // Tiempo entre tooltips consecutivos
        tm.resetTime = 0f;            // Tiempo de reset
        tm.offsetX = 10f;             // Offset horizontal
        tm.offsetY = 10f;             // Offset vertical
    }

    private final TextTooltip tooltip;

    /**
     * Constructor para actores generales (jugadores, items, enemigos, etc.)
     */
    public TooltipStandard(String text, Actor actor) {
        tooltip = new TextTooltip(text, tm, skinTooltip);
        tooltip.getContainer().setBackground((Drawable) null); // Sin fondo por defecto
        actor.addListener(tooltip);
    }

    /**
     * Constructor de compatibilidad por si lo usas en un escenario
     */
    public TooltipStandard(String text, Actor actor, Object escenario) {
        this(text, actor);
    }

    /**
     * Permite actualizar el texto del tooltip en vivo
     */
    public void setText(String newText) {
        Label label = tooltip.getActor();
        if (label != null) {
            label.setText(newText);
            tooltip.getContainer().pack(); // Ajusta tamaño automáticamente
        }
    }

    /**
     * Método opcional para actualizar la posición del tooltip si lo necesitas
     */
    public void actualizarPosicion() {
        // Por defecto TooltipManager ya maneja la posición según el mouse
        // Si quieres moverlo manualmente, puedes agregar lógica aquí
    }
}
