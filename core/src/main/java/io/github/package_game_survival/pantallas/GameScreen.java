package io.github.package_game_survival.pantallas;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import io.github.package_game_survival.entidades.mapas.*;
import io.github.package_game_survival.network.ClientThread;

public class GameScreen implements Screen {

    private final SpriteBatch batch = new SpriteBatch();
    private final OrthographicCamera camera;
    private final MundoCliente mundo;
    private final EscenarioCliente escenario;
    private final ClientThread clientThread;

    public GameScreen(ClientThread clientThread) {
        this.clientThread = clientThread;

        camera = new OrthographicCamera(1280, 720);
        camera.position.set(640, 360, 0);
        camera.update();

        mundo = new MundoCliente();
        escenario = new EscenarioCliente(mundo);

        clientThread.setMundo(mundo);
        clientThread.start();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            Vector3 v = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(v);
            clientThread.sendMessage("MOVE:" + v.x + ":" + v.y);
        }

        escenario.render(batch, camera);
    }

    @Override public void resize(int w, int h) {}
    @Override public void show() {}
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void dispose() { batch.dispose(); }
}
