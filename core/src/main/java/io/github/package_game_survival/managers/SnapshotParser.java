package io.github.package_game_survival.managers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import io.github.package_game_survival.desastres.CharcoVisual;
import io.github.package_game_survival.entidades.mapas.EscenarioCliente;
import io.github.package_game_survival.entidades.objetos.ObjetoVisual;
import io.github.package_game_survival.entidades.seres.jugadores.CazadorVisual;
import io.github.package_game_survival.entidades.seres.jugadores.GuerreroVisual;

public class SnapshotParser {

    public static void procesar(String snapshot, EscenarioCliente escenario) {
        String[] lineas = snapshot.split("\n");

        for (String l : lineas) {
            String[] p = l.split(":");

            switch (p[0]) {
                case "PLAYER": {
                    int id = Integer.parseInt(p[1]);
                    float x = Float.parseFloat(p[2].replace(",", "."));
                    float y = Float.parseFloat(p[3].replace(",", "."));
                    String clase = p[4];

                    escenario.getJugadores().computeIfAbsent(id, k -> {
                        TextureAtlas atlas = Assets.get("jugador_atlas.atlas", TextureAtlas.class);
                        if (clase.equals("GUERRERO")) {
                            return new GuerreroVisual(id, atlas, x, y);
                        } else {
                            return new CazadorVisual(id, atlas, x, y);
                        }
                    }).setPos(x, y);
                    break;
                }

                case "OBJECT": {
                    int id = Integer.parseInt(p[1]);
                    String tipo = p[2];
                    float x = Float.parseFloat(p[3].replace(",", "."));
                    float y = Float.parseFloat(p[4].replace(",", "."));

                    escenario.getObjetos().computeIfAbsent(id, k -> {
                        TextureAtlas atlas = Assets.get("objetos.atlas", TextureAtlas.class);
                        return new ObjetoVisual(id, atlas.findRegion(tipo), x, y, 32, 32);
                    }).setPos(x, y);
                    break;
                }

                case "TIME":
                    escenario.getHud().setTiempo(
                        Integer.parseInt(p[1]),
                        Integer.parseInt(p[2]),
                        Integer.parseInt(p[3])
                    );
                    break;

                case "DISASTER":
                    escenario.getHud().setDesastre(p[1]);
                    break;

                case "DISASTER_OBJECT": {
                    int id = Integer.parseInt(p[1]);
                    String tipo = p[2];
                    float x = Float.parseFloat(p[3].replace(",", "."));
                    float y = Float.parseFloat(p[4].replace(",", "."));
                    float w = Float.parseFloat(p[5].replace(",", "."));
                    float h = Float.parseFloat(p[6].replace(",", "."));

                    if (tipo.equals("CHARCO")) {
                        escenario.getObjetos().computeIfAbsent(id, k -> {
                            TextureAtlas atlas = Assets.get("desastres.atlas", TextureAtlas.class);
                            return new CharcoVisual(id, atlas.findRegion("charco"), x, y, w, h);
                        }).setPos(x, y);
                    }
                    break;
                }
            }
        }
    }
}
