package io.github.package_game_survival.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.package_game_survival.entidades.TipoClase;
import io.github.package_game_survival.managers.Assets;
import io.github.package_game_survival.managers.PathManager;
import io.github.package_game_survival.standards.TextButtonStandard;

public class FastMenuScreen implements Screen {

    private final MyGame game;
    private final GameScreen gameScreen;
    private Stage stage;
    private Skin skin;

    public FastMenuScreen(final MyGame game, final GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;
    }

    @Override
    public void show() {
        this.skin = Assets.get(PathManager.BACKGROUND, Skin.class);

        stage = new Stage(game.getViewport());
        Gdx.input.setInputProcessor(stage);

        // Botón Reanudar
        TextButtonStandard reanudarButton = new TextButtonStandard("Reanudar");
        reanudarButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(gameScreen);
                dispose();
            }
        });

        // Botón Resetear
        TextButtonStandard resetButton = new TextButtonStandard("Resetear Posición");
        resetButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (gameScreen != null) {
                    System.out.println("Reseteando posición...");
                    // Aquí puedes llamar a un método de GameScreen para reiniciar
                    // Ejemplo: gameScreen.reiniciarJugadores();
                    // O reiniciar la pantalla completa:
                    game.setScreen(gameScreen);
                }
            }
        });

        Table table = new Table();
        if (skin != null) table.setBackground(skin.getDrawable("fondoMenu"));

        table.setFillParent(true);
        table.add(reanudarButton).pad(10).row();
        table.add(resetButton).pad(10).row();

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0.5f);
        stage.act(delta);
        stage.draw();

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            game.setScreen(gameScreen);
            dispose();
        }
    }

    @Override public void resize(int w, int h) {
        stage.getViewport().update(w, h, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        stage.dispose();
    }
}
