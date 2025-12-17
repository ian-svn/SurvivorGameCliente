package io.github.package_game_survival.entidades.mapas;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MundoCliente {

    private Stage stage;

    public void agregarActor(Actor a) {
        stage.addActor(a);
    }

    public Stage getStage() {
        return stage;
    }
}
