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
    private Skin skin;

    private boolean seleccionRealizada = false;
    private TipoClase miClaseElegida = TipoClase.GUERRERO;

    public CharacterSelectionScreen(MyGame game) {
        this.game = game;
        this.stage = new Stage(new FitViewport(MyGame.ANCHO_PANTALLA, MyGame.ALTO_PANTALLA));

        // Inicializamos hilo cliente
        this.clientThread = new ClientThread();
        this.clientThread.setGameController(this);
        this.clientThread.start();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        skin = Assets.get(PathManager.BACKGROUND, Skin.class);

        Table table = new Table();
        table.setFillParent(true);
        if (skin != null) table.setBackground(skin.getDrawable("fondoMenu"));

        estadoLabel = new LabelStandard("ELIGE TU CLASE PARA UNIRTE");

        btnGuerrero = new TextButtonStandard("Guerrero");
        btnGuerrero.setClickListener(() -> seleccionarClase(TipoClase.GUERRERO));

        btnCazador = new TextButtonStandard("Cazador");
        btnCazador.setClickListener(() -> seleccionarClase(TipoClase.CAZADOR));

        table.add(estadoLabel).pad(20).colspan(2).row();
        table.add(btnGuerrero).pad(10).width(200).height(60);
        table.add(btnCazador).pad(10).width(200).height(60);

        stage.addActor(table);
    }

    private void seleccionarClase(TipoClase clase) {
        if (seleccionRealizada) return;

        seleccionRealizada = true;
        miClaseElegida = clase;

        btnGuerrero.setDisabled(true);
        btnCazador.setDisabled(true);

        estadoLabel.setText("Conectando como " + clase.toString() + "...");

        clientThread.sendMessage("CONNECT:" + clase.toString());
    }

    @Override
    public void onConnected() {
        Gdx.app.postRunnable(() -> estadoLabel.setText("¡Conectado! Esperando al jugador 2..."));
    }

    @Override
    public void startGame(String mensajeStart) {
        Gdx.app.postRunnable(() -> {
            game.setScreen(new GameScreen(game, clientThread, miClaseElegida));
            dispose();
        });
    }

    @Override
    public void startGame() { startGame(""); }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int w, int h) { stage.getViewport().update(w, h, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { if (stage != null) stage.dispose(); }
}
