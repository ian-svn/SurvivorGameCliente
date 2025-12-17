package io.github.package_game_survival.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.package_game_survival.entidades.TipoClase;
import io.github.package_game_survival.managers.Assets;
import io.github.package_game_survival.managers.PathManager;
import io.github.package_game_survival.network.ClientThread;

import java.util.HashMap;
import java.util.Map;

public class GameScreen implements Screen {

    private final MyGame game;
    private final Stage stage;
    private final ClientThread clientThread;
    private final TipoClase tipoClaseActual;

    private final Map<Integer, Image> jugadores = new HashMap<>();
    private TextureRegion texJugador;

    public GameScreen(MyGame game, ClientThread clientThread, TipoClase tipoClase) {
        this.game = game;
        this.clientThread = clientThread;
        this.tipoClaseActual = tipoClase;

        this.stage = new Stage(new FitViewport(MyGame.ANCHO_PANTALLA, MyGame.ALTO_PANTALLA));
        Gdx.input.setInputProcessor(stage);

        TextureAtlas atlas = Assets.get(PathManager.PLAYER_ATLAS, TextureAtlas.class);
        texJugador = atlas.getRegions().get(0);

        clientThread.setGameScreen(this);
    }

    @Override
    public void show() {
        System.out.println("GameScreen iniciada. Clase: " + tipoClaseActual);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        enviarInput();
        stage.act(delta);
        stage.draw();
    }

    private void enviarInput() {
        int dx = 0, dy = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) dy += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) dy -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) dx -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) dx += 1;

        if (dx != 0 || dy != 0) {
            clientThread.sendInput(dx, dy);
        }
    }

    public void actualizarRemoto(int id, float x, float y) {
        Image jugador = jugadores.get(id);

        if (jugador == null) {
            jugador = new Image(texJugador);
            jugador.setPosition(x, y);
            jugadores.put(id, jugador);
            stage.addActor(jugador);
        } else {
            jugador.setPosition(x, y);
        }
    }

    @Override public void resize(int w, int h) { stage.getViewport().update(w, h, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); }
}
