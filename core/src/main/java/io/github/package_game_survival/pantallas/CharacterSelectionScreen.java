package io.github.package_game_survival.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.package_game_survival.entidades.TipoClase;
import io.github.package_game_survival.managers.Assets;
import io.github.package_game_survival.managers.PathManager;
import io.github.package_game_survival.network.ClientThread;
import io.github.package_game_survival.network.GameController;
import io.github.package_game_survival.standards.LabelStandard;
import io.github.package_game_survival.standards.TextButtonStandard;

public class CharacterSelectionScreen implements Screen, GameController {

    private final MyGame game;
    private final Stage stage;
    private ClientThread clientThread;

    private LabelStandard estadoLabel;
    private TextButtonStandard btnGuerrero, btnCazador;

    private boolean seleccionRealizada = false;
    private boolean juegoIniciado = false;
    private TipoClase miClaseElegida = TipoClase.GUERRERO; // Por defecto

    public CharacterSelectionScreen(MyGame game) {
        this.game = game;
        this.stage = new Stage(new FitViewport(MyGame.ANCHO_PANTALLA, MyGame.ALTO_PANTALLA));
        Gdx.input.setInputProcessor(stage);

        clientThread = new ClientThread();
        clientThread.start();

        crearUI();
    }

    private void crearUI() {
        Table tabla = new Table();
        tabla.setFillParent(true);
        Skin skin = Assets.get(PathManager.BACKGROUND, Skin.class);
        if(skin != null) tabla.setBackground(skin.getDrawable("fondoMenu"));

        LabelStandard titulo = new LabelStandard("ELIGE TU CLASE");
        estadoLabel = new LabelStandard("Estado: Elige personaje para conectar...");

        btnGuerrero = new TextButtonStandard("GUERRERO");
        btnGuerrero.setClickListener(() -> enviarSeleccion(TipoClase.GUERRERO));

        btnCazador = new TextButtonStandard("CAZADOR");
        btnCazador.setClickListener(() -> enviarSeleccion(TipoClase.CAZADOR));

        tabla.add(titulo).padBottom(30).colspan(2).row();
        tabla.add(btnGuerrero).size(200, 60).pad(10);
        tabla.add(btnCazador).size(200, 60).pad(10).row();
        tabla.add(estadoLabel).padTop(30).colspan(2);

        stage.addActor(tabla);
    }

    private void enviarSeleccion(TipoClase clase) {
        if (seleccionRealizada) return;
        seleccionRealizada = true;
        this.miClaseElegida = clase; // Guardamos la selección

        btnGuerrero.setDisabled(true);
        btnCazador.setDisabled(true);
        estadoLabel.setText("Conectando como " + clase.toString() + "...");

        clientThread.sendMessage("CONNECT:" + clase.toString());
    }

    @Override
    public void onConnected() {
        Gdx.app.postRunnable(() -> {
            estadoLabel.setText("¡Conectado! Esperando al otro jugador (1/2)...");
            estadoLabel.setColor(0, 1, 0, 1);
        });
    }

    @Override
    public void startGame() {
        if (juegoIniciado) return;
        juegoIniciado = true;

        Gdx.app.postRunnable(() -> {
            // --- AQUÍ ESTABA EL ERROR: AHORA PASAMOS LOS 3 PARÁMETROS ---
            game.setScreen(new GameScreen(game, clientThread, miClaseElegida));
            dispose();
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int w, int h) { stage.getViewport().update(w, h, true); }

    @Override
    public void dispose() {
        if(stage != null) stage.dispose();
        // No cerramos el clientThread porque se lo pasamos al juego
    }

    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
