package io.github.package_game_survival.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.package_game_survival.network.ClientThread;
import io.github.package_game_survival.standards.TextButtonStandard;

public class CharacterSelectionScreen implements Screen {

    private final MyGame game;
    private final Stage stage;
    private final ClientThread clientThread;

    public CharacterSelectionScreen(MyGame game) {
        this.game = game;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // 🔹 Cliente se crea UNA sola vez
        clientThread = new ClientThread();
        clientThread.start();

        // 🔹 Botón standard (el tuyo)
        TextButtonStandard btnJugar = new TextButtonStandard("JUGAR");
        btnJugar.setPosition(400, 240);
        btnJugar.setEscalaFuente(1.2f);

        btnJugar.setClickListener(() -> {
            // ✅ Constructor correcto
            game.setScreen(new GameScreen(clientThread));
        });

        stage.addActor(btnJugar);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }
}
