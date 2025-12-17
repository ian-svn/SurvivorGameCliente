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

    // UI
    private LabelStandard estadoLabel;
    private TextButtonStandard btnGuerrero, btnCazador;
    private Skin skin;

    // Lógica
    private boolean seleccionRealizada = false;
    private TipoClase miClaseElegida = TipoClase.GUERRERO; // Por defecto

    public CharacterSelectionScreen(MyGame game) {
        this.game = game;
        this.stage = new Stage(new FitViewport(MyGame.ANCHO_PANTALLA, MyGame.ALTO_PANTALLA));

        // 1. INICIALIZAMOS EL HILO DEL CLIENTE (UDP)
        // Se quedará escuchando respuestas del servidor en segundo plano
        this.clientThread = new ClientThread();
        this.clientThread.setGameController(this); // Para que nos avise (callbacks)
        this.clientThread.start();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        // Cargar recursos
        this.skin = Assets.get(PathManager.BACKGROUND, Skin.class);

        // Crear Tabla para organizar la UI
        Table table = new Table();
        table.setFillParent(true);
        if (skin != null) {
            table.setBackground(skin.getDrawable("fondoMenu"));
        }

        // Crear Componentes
        estadoLabel = new LabelStandard("ELIGE TU CLASE PARA UNIRTE");

        btnGuerrero = new TextButtonStandard("Guerrero");
        btnGuerrero.setClickListener(() -> seleccionarClase(TipoClase.GUERRERO));

        btnCazador = new TextButtonStandard("Cazador");
        btnCazador.setClickListener(() -> seleccionarClase(TipoClase.CAZADOR));

        // Añadir a la tabla
        table.add(estadoLabel).pad(20).colspan(2).row();
        table.add(btnGuerrero).pad(10).width(200).height(60);
        table.add(btnCazador).pad(10).width(200).height(60);

        stage.addActor(table);
    }

    private void seleccionarClase(TipoClase clase) {
        if (seleccionRealizada) return; // Evitar doble click

        seleccionRealizada = true;
        this.miClaseElegida = clase;

        // Deshabilitar botones para que no spamee
        btnGuerrero.setDisabled(true);
        btnCazador.setDisabled(true);

        estadoLabel.setText("Conectando como " + clase.toString() + "...");

        // --- ENVIAR PAQUETE UDP AL SERVIDOR ---
        // Formato: CONNECT:CLASE
        clientThread.sendMessage("CONNECT:" + clase.toString());
    }

    // --- MÉTODOS DE GAMECONTROLLER (Vienen del ClientThread) ---

    @Override
    public void onConnected() {
        // El servidor respondió "CONNECTED"
        // Usamos postRunnable porque esto viene de otro hilo
        Gdx.app.postRunnable(() -> {
            estadoLabel.setText("¡Conectado! Esperando al jugador 2...");
        });
    }

    @Override
    public void startGame(String mensajeStart) {
        // El servidor envió "START:..."
        Gdx.app.postRunnable(() -> {
            // Aquí podrías parsear el mensaje si necesitas saber la clase del enemigo
            // Ejemplo msg: "START:GUERRERO:CAZADOR"

            // Pasamos el clientThread (que ya tiene el socket abierto) a la pantalla de juego
            game.setScreen(new GameScreen(game, clientThread, miClaseElegida));

            // Liberamos la UI de selección, pero NO el hilo del cliente
            dispose();
        });
    }

    // Método por defecto de la interfaz (si lo tienes definido sin argumentos)
    @Override
    public void startGame() {
        startGame("");
    }

    // --- MÉTODOS ESTÁNDAR DE SCREEN ---

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

    @Override
    public void dispose() {
        if (stage != null) stage.dispose();
        // NOTA: No hacemos clientThread.terminate() aquí porque lo pasamos a GameScreen.
        // Solo si cerramos la app sin jugar deberíamos cerrarlo.
    }
}
