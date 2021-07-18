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

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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

    public TextureRegion towerBodyTexture;
    public TextureRegion towerHeadRedTexture;
    public TextureRegion towerHeadBlueTexture;

    public TextureRegion wallBodyTexture;
    public TextureRegion wallHeadTexture;

    protected Map<String, TextureRegion> cardsTextureMap;
    protected Map<String, String> cardsActionsMap;

    public BitmapFont font;

    public String dropAndAgainMessage;

    public Resource() {

        //фоны
        welcomeTexture = new TextureRegion(new Texture(AppImpl.settings.welcomeTexture), AppImpl.settings.welcomeTextureWidth, AppImpl.settings.welcomeTextureHeight);

        boardTexture = new TextureRegion(new Texture(AppImpl.settings.boardTexture), AppImpl.settings.boardTextureWidth, AppImpl.settings.boardTextureHeight);

        //куски
        Texture itemsTexture = new Texture(AppImpl.settings.itemsTexture);

        deckUndoTexture = new TextureRegion(itemsTexture,
                AppImpl.settings.deckUndoTextureX,
                AppImpl.settings.deckUndoTextureY,
                AppImpl.settings.deckTextureWidth,
                AppImpl.settings.deckTextureHeight);

        brickTexture = new TextureRegion(itemsTexture,
                AppImpl.settings.brickTextureX,
                AppImpl.settings.brickTextureY,
                AppImpl.settings.resTextureWidth,
                AppImpl.settings.resTextureHeight);
        gemTexture = new TextureRegion(itemsTexture,
                AppImpl.settings.gemTextureX,
                AppImpl.settings.gemTextureY,
                AppImpl.settings.resTextureWidth,
                AppImpl.settings.resTextureHeight);
        beastTexture = new TextureRegion(itemsTexture,
                AppImpl.settings.beastTextureX,
                AppImpl.settings.beastTextureY,
                AppImpl.settings.resTextureWidth,
                AppImpl.settings.resTextureHeight);

        towerBodyTexture = new TextureRegion(itemsTexture,
                AppImpl.settings.towerBodyTextureX,
                AppImpl.settings.towerBodyTextureY,
                AppImpl.settings.towerBodyTextureWidth,
                AppImpl.settings.towerBodyTextureHeight);

        towerHeadRedTexture = new TextureRegion(itemsTexture,
                AppImpl.settings.towerHeadRedTextureX,
                AppImpl.settings.towerHeadRedTextureY,
                AppImpl.settings.towerHeadTextureWidth,
                AppImpl.settings.towerHeadTextureHeight);

        towerHeadBlueTexture = new TextureRegion(itemsTexture,
                AppImpl.settings.towerHeadBlueTextureX,
                AppImpl.settings.towerHeadBlueTextureY,
                AppImpl.settings.towerHeadTextureWidth,
                AppImpl.settings.towerHeadTextureHeight);

        wallBodyTexture = new TextureRegion(itemsTexture,
                AppImpl.settings.wallBodyTextureX,
                AppImpl.settings.wallBodyTextureY,
                AppImpl.settings.wallBodyTextureWidth,
                AppImpl.settings.wallBodyTextureHeight);

        wallHeadTexture = new TextureRegion(itemsTexture,
                AppImpl.settings.wallHeadTextureX,
                AppImpl.settings.wallHeadTextureY,
                AppImpl.settings.wallHeadTextureWidth,
                AppImpl.settings.wallHeadTextureHeight);

        // Шрифты
        font = new BitmapFont(
                new FileHandle(AppImpl.settings.fontFnt),
                new FileHandle(AppImpl.settings.fontPng),
                false
        );

        //колода
        cardsActionsMap = new HashMap<>();
        cardsTextureMap = new HashMap<>();

        readActions(AppImpl.settings.deckActions);
        readRegions(new Texture(AppImpl.settings.deckTexture));

        //текстовые строки

        dropAndAgainMessage = "SELECT CARD TO DROP";
    }

    /**
     * Загрузка действий карт, без анализа
     */
    private void readActions(String filePath){
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream(filePath));
            prop.forEach((k, v) -> cardsActionsMap.put(k.toString(),v.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        for (int z = 0; z < 3; ++z) {
            int count = 0;
            y_start = z - 1;
            for (int iy = 0; iy < y_count && count < type_count; ++iy) {
                for (int ix = 0; ix < x_count && count < type_count; ++ix) {

                    int iyz = iy + z * 4;

                    int cx = x_start + ix * x_width;
                    int cy = y_start + iyz * y_heigth;

                    //System.out.println("cut texture [" +z+ "," + iyz + "," + ix + "] = x=" + cx + ",y=" + cy);

                    TextureRegion textureRegion = new TextureRegion(texture, cx, cy, x_width-1, y_heigth);

                    cardsTextureMap.put(iyz + "_" + ix, textureRegion);

                    count++;
                }
            }
        }
    }


    public TextureRegion getGameTexture(String name) {
        return cardsTextureMap.get(name);
    }

    public Map<String, TextureRegion> getCardsTextureMap() {
        return cardsTextureMap;
    }

    public String getCardActionsLine(String name) {
        return cardsActionsMap.get(name);
    }
}
