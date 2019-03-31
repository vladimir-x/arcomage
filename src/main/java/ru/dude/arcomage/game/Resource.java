/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author elduderino
 */
public class Resource {

    public TextureRegion welcomeTexture;
    public TextureRegion boardTexture;
    public TextureRegion deckUndoTexture;

    public TextureRegion brickTexture;
    public TextureRegion gemTexture;
    public TextureRegion beastTexture;

    protected static Map<String, TextureRegion> cardsTextureMap;

    public BitmapFont font;

    public Resource() {

        Texture texture;

        //фоны
        texture = new Texture(AppImpl.settings.welcomeTexture);
        welcomeTexture = new TextureRegion(texture, AppImpl.settings.welcomeTextureWidth, AppImpl.settings.welcomeTextureHeight);

        texture = new Texture(AppImpl.settings.boardTexture);
        boardTexture = new TextureRegion(texture, AppImpl.settings.boardTextureWidth, AppImpl.settings.boardTextureHeight);

        //куски
        texture = new Texture(AppImpl.settings.itemsTexture);

        deckUndoTexture = new TextureRegion(texture,
                AppImpl.settings.deckUndoTextureX,
                AppImpl.settings.deckUndoTextureY,
                AppImpl.settings.deckTextureWidth,
                AppImpl.settings.deckTextureHeight);

        brickTexture = new TextureRegion(texture,
                AppImpl.settings.brickTextureX,
                AppImpl.settings.brickTextureY,
                AppImpl.settings.resTextureWidth,
                AppImpl.settings.resTextureHeight);
        gemTexture = new TextureRegion(texture,
                AppImpl.settings.gemTextureX,
                AppImpl.settings.gemTextureY,
                AppImpl.settings.resTextureWidth,
                AppImpl.settings.resTextureHeight);
        beastTexture = new TextureRegion(texture,
                AppImpl.settings.beastTextureX,
                AppImpl.settings.beastTextureY,
                AppImpl.settings.resTextureWidth,
                AppImpl.settings.resTextureHeight);

        // Шрифты
        font = new BitmapFont(
                new FileHandle(AppImpl.settings.fontFnt),
                new FileHandle(AppImpl.settings.fontPng),
                false
        );

        //колода
        cardsTextureMap = new HashMap<String, TextureRegion>();
        texture = new Texture(AppImpl.settings.deckTexture);

        readRegions(texture);

    }

    /**
     * Загрузка текстур карт
     * @param texture
     */
    private void readRegions(Texture texture){

        int x_start = 0;
        int y_start = 0;

        int x_width = AppImpl.settings.deckTextureWidth + 1;
        int y_heigth = AppImpl.settings.deckTextureHeight ;

        int x_count = AppImpl.settings.deckCountX;
        int y_count = AppImpl.settings.deckCountY;

        int type_count = AppImpl.settings.deckCountType;

        for (int z = 0; z<3; ++z) {
            int count = 0;
            y_start = z - 1 ;
            for (int iy = 0; iy < y_count && count < type_count; ++iy) {
                for (int ix = 0; ix < x_count && count < type_count; ++ix) {

                    int iyz = iy+z*4;

                    int cx = x_start + ix * x_width;
                    int cy = y_start + iyz * y_heigth;

                    //System.out.println("cut texture [" +z+ "," + iyz + "," + ix + "] = x=" + cx + ",y=" + cy);

                    TextureRegion textureRegion = new TextureRegion(texture, cx, cy, x_width, y_heigth );

                    cardsTextureMap.put(iyz + "_" + ix, textureRegion);

                    count++;
                }
            }
        }
    }


    public TextureRegion getGameTexture(String name) {
        return cardsTextureMap.get(name);
    }

    public static Map<String, TextureRegion> getCardsTextureMap() {
        return cardsTextureMap;
    }
}
