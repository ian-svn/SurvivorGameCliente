package io.github.package_game_survival.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.package_game_survival.algoritmos.ClickEffect;
import io.github.package_game_survival.algoritmos.EstrategiaMoverAPunto;
import io.github.package_game_survival.entidades.TipoClase;
import io.github.package_game_survival.entidades.mapas.Escenario;
import io.github.package_game_survival.entidades.objetos.Objeto;
import io.github.package_game_survival.entidades.seres.animales.Animal;
import io.github.package_game_survival.entidades.seres.enemigos.Enemigo;
import io.github.package_game_survival.entidades.seres.jugadores.Cazador;
import io.github.package_game_survival.entidades.seres.jugadores.Guerrero;
import io.github.package_game_survival.entidades.seres.jugadores.Hud;
import io.github.package_game_survival.entidades.seres.jugadores.Jugador;
import io.github.package_game_survival.managers.Assets;
import io.github.package_game_survival.managers.Audio.AudioManager;
import io.github.package_game_survival.managers.BrilloManager;
import io.github.package_game_survival.managers.PathManager;
import io.github.package_game_survival.network.ClientThread;
import io.github.package_game_survival.standards.LabelStandard;

public class GameScreen implements Screen {

    private final MyGame game;
    private final FastMenuScreen fm;

    // Red
    private final ClientThread clientThread;

    // Escenarios
    private final Stage stageMundo;
    private final Stage stageUI;
    private final FitViewport viewportUI;
    private final Hud hud;

    // Jugadores
    private final Jugador jugadorLocal;
    private final Jugador jugadorRemoto;
    private final TipoClase tipoClaseLocal;

    private final Escenario escenario;
    private final OrthographicCamera camara;

    private final InputMultiplexer inputMultiplexer;
    private final Vector3 tempVec = new Vector3();
    private Animation<TextureRegion> clickAnimation;
    private LabelStandard labelFinJuego, labelVolverMenu;
    private Image fondoOscuro;
    private boolean juegoTerminado = false;
    private static float ANCHO_MUNDO;
    private static float ALTO_MUNDO;

    public GameScreen(MyGame game, ClientThread clientThread, TipoClase tipoClaseLocal) {
        this.game = game;
        this.clientThread = clientThread;
        this.tipoClaseLocal = tipoClaseLocal;

        // Enlazar el hilo con esta pantalla
        if (this.clientThread != null) {
            this.clientThread.setGameScreen(this);
        }

        ANCHO_MUNDO = MyGame.ANCHO_PANTALLA;
        ALTO_MUNDO = MyGame.ALTO_PANTALLA;

        this.stageMundo = new Stage(game.getViewport());
        this.viewportUI = new FitViewport(ANCHO_MUNDO, ALTO_MUNDO);
        this.stageUI = new Stage(viewportUI);

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stageUI);
        inputMultiplexer.addProcessor(stageMundo);
        Gdx.input.setInputProcessor(inputMultiplexer);

        // --- CREAR JUGADOR LOCAL Y REMOTO ---
        float startXLocal = ANCHO_MUNDO / 2 - 50;
        float startYLocal = ALTO_MUNDO / 2;
        float startXRemoto = ANCHO_MUNDO / 2 + 50;
        float startYRemoto = ALTO_MUNDO / 2;

        if (tipoClaseLocal == TipoClase.CAZADOR) {
            this.jugadorLocal = new Cazador(startXLocal, startYLocal);
        } else {
            this.jugadorLocal = new Guerrero(startXLocal, startYLocal);
        }

        // El remoto es la clase opuesta (o la misma, según tu lógica de juego)
        TipoClase tipoClaseRemota = (tipoClaseLocal == TipoClase.GUERRERO) ? TipoClase.CAZADOR : TipoClase.GUERRERO;

        if (tipoClaseRemota == TipoClase.CAZADOR) {
            this.jugadorRemoto = new Cazador(startXRemoto, startYRemoto);
        } else {
            this.jugadorRemoto = new Guerrero(startXRemoto, startYRemoto);
        }

        // El remoto no se toca con el mouse localmente
        this.jugadorRemoto.setTouchable(Touchable.disabled);

        this.hud = new Hud(game.batch, jugadorLocal, null);
        this.escenario = new Escenario(stageMundo, jugadorLocal, hud);
        this.escenario.setStageUI(stageUI);

        // ¡Importante! Agregar al remoto al escenario
        this.stageMundo.addActor(jugadorRemoto);

        this.fm = new FastMenuScreen(game, this);
        this.camara = (OrthographicCamera) stageMundo.getCamera();
        this.camara.zoom = 0.6f;
        this.camara.update();

        inicializarUI();
        cargarEfectosVisuales();
    }

    public void actualizarRemoto(float x, float y) {
        if (jugadorRemoto != null) {
            // Aquí podrías interpolar para suavizar
            jugadorRemoto.setPosition(x, y);
        }
    }

    public TipoClase getTipoClaseActual() {
        return tipoClaseLocal;
    }

    private void enviarMiPosicion() {
        if (clientThread != null && !juegoTerminado) {
            int miID = clientThread.getClienteID();
            // Enviamos MOVE : ID : X : Y
            String msg = "MOVE:" + miID + ":" + jugadorLocal.getX() + ":" + jugadorLocal.getY();
            clientThread.sendMessage(msg);
        }
    }

    private void inicializarUI() {
        fondoOscuro = new Image(Assets.get(PathManager.GAME_BACKGROUND_TEXTURE, Texture.class));
        fondoOscuro.setColor(0, 0, 0, 0.6f);
        fondoOscuro.setSize(ANCHO_MUNDO, ALTO_MUNDO);
        fondoOscuro.setVisible(false);

        labelFinJuego = new LabelStandard("");
        labelFinJuego.setColor(Color.RED);
        labelFinJuego.setFontScale(3f);
        labelFinJuego.setVisible(false);

        labelVolverMenu = new LabelStandard("Presione [ESC] para volver al menu");
        labelVolverMenu.setColor(Color.WHITE);
        labelVolverMenu.setFontScale(0.6f);
        labelVolverMenu.setVisible(false);

        stageUI.addActor(fondoOscuro);
        stageUI.addActor(labelFinJuego);
        stageUI.addActor(labelVolverMenu);
    }

    private void cargarEfectosVisuales() {
        try {
            TextureAtlas atlas = Assets.get(PathManager.CLICK_ANIMATION, TextureAtlas.class);
            Array<TextureRegion> frames = new Array<>();
            for (int i = 1; i <= 5; i++) frames.add(atlas.findRegion("click" + i));
            clickAnimation = new Animation<>(0.08f, frames, Animation.PlayMode.NORMAL);
        } catch (Exception e) {}
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        actualizarCamara();

        escenario.renderConShader(camara, delta);

        if (!juegoTerminado) {
            hud.update(delta);
            hud.draw();
            // Enviamos posición en cada frame
            enviarMiPosicion();
        }

        stageUI.act(delta);
        stageUI.draw();

        if (escenario.getGestorTiempo().isJuegoGanado()) {
            if (!juegoTerminado) terminarJuego("GANASTE");
        } else if (juegoTerminado) {
            jugadorLocal.setEstrategia(null);
            jugadorRemoto.setEstrategia(null);
        } else {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
                tempVec.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                camara.unproject(tempVec);
                Vector2 dest = new Vector2(tempVec.x, tempVec.y);
                jugadorLocal.setEstrategia(new EstrategiaMoverAPunto(dest, escenario.getBloques()));
                if(clickAnimation!=null) stageMundo.addActor(new ClickEffect(clickAnimation, dest.x, dest.y));
            }
            if(jugadorLocal.getVida() <= 0) terminarJuego("PERDISTE");
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(fm);
            this.camara.zoom = 1f;
        }
    }

    private void actualizarCamara() {
        float zoom = camara.zoom;
        float vw = camara.viewportWidth * zoom;
        float vh = camara.viewportHeight * zoom;
        float x = Math.max(vw/2, Math.min(jugadorLocal.getX(), ANCHO_MUNDO - vw/2));
        float y = Math.max(vh/2, Math.min(jugadorLocal.getY(), ALTO_MUNDO - vh/2));
        camara.position.set(x, y, 0);
        camara.update();
    }

    @Override
    public void resize(int w, int h) {
        // PROTECCIÓN CONTRA MINIMIZAR
        if (w == 0 || h == 0) return;

        stageMundo.getViewport().update(w, h, true);
        viewportUI.update(w, h, true);
        hud.resize(w, h);
        BrilloManager.redimensionar(w, h);
        camara.update();
    }

    private void terminarJuego(String msg) {
        for(Enemigo e : escenario.getEnemigos()) e.remove();
        for(Objeto o : escenario.getObjetos()) o.remove();
        for(Animal a : escenario.getAnimales()) a.remove();
        if(jugadorRemoto != null) jugadorRemoto.remove();

        juegoTerminado = true;
        fondoOscuro.setVisible(true); fondoOscuro.toFront();
        labelFinJuego.setText(msg); labelFinJuego.setVisible(true); labelFinJuego.toFront();
        labelVolverMenu.setVisible(true); labelVolverMenu.toFront();
        labelFinJuego.setPosition(ANCHO_MUNDO/2f - labelFinJuego.getPrefWidth()/2f, ALTO_MUNDO/2f + 50);
        labelVolverMenu.setPosition(ANCHO_MUNDO/2f - labelVolverMenu.getPrefWidth()/2f, ALTO_MUNDO/2f - 100);
    }

    public OrthographicCamera getCamara() { return camara; }

    @Override public void dispose() {
        stageMundo.dispose();
        stageUI.dispose();
        if(hud!=null) hud.dispose();
        if(escenario!=null) escenario.dispose();
        AudioManager.getControler().stopMusic();
        if(clientThread != null) clientThread.terminate();
    }

    @Override public void show() {
        Gdx.input.setInputProcessor(inputMultiplexer);
        BrilloManager.redimensionar(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
