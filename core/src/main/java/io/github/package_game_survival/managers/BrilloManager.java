package io.github.package_game_survival.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class BrilloManager {

    private static ShaderProgram shaderBrillo;
    private static FrameBuffer fbo;
    private static SpriteBatch batchShader;
    private static float brillo = 1f;

    public static void inicializar() {
        ShaderProgram.pedantic = false;
        shaderBrillo = new ShaderProgram(
            Gdx.files.internal(PathManager.BRILLO_VERT),
            Gdx.files.internal(PathManager.BRILLO_FRAG)
        );

        if (!shaderBrillo.isCompiled()) {
            System.err.println("❌ Error en shader: " + shaderBrillo.getLog());
        }

        // Intentamos crear el FBO inicial (protegido)
        redimensionar(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batchShader = new SpriteBatch();
    }

    public static void redimensionar(int width, int height) {
        // --- FIX CRITICO: Evita crash si la ventana está minimizada o es muy pequeña ---
        if (width <= 0 || height <= 0) return;

        if (fbo != null) {
            try { fbo.dispose(); } catch (Exception e) { /* Ignorar */ }
        }

        try {
            fbo = new FrameBuffer(
                Pixmap.Format.RGBA8888,
                width,
                height,
                false
            );
        } catch (IllegalStateException e) {
            Gdx.app.error("BrilloManager", "No se pudo crear el FBO (probablemente ventana minimizada)", e);
        }
    }

    public static void setBrillo(float valor) { brillo = valor; }
    public static float getBrillo() { return brillo; }
    public static ShaderProgram getShader() { return shaderBrillo; }
    public static FrameBuffer getFBO() { return fbo; }
    public static SpriteBatch getBatchShader() { return batchShader; }

    public static void dispose() {
        if (shaderBrillo != null) shaderBrillo.dispose();
        if (fbo != null) fbo.dispose();
        if (batchShader != null) batchShader.dispose();
    }
}
