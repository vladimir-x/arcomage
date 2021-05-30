/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game;

/**
 * Внутренние настройки верстки интерфейса
 * @author elduderino
 */
public class Settings {

    //шрифты
    public String fontFnt;
    public String fontPng;
    
    // размеры окна
    public Integer windowWidth;
    public Integer windowHeight;
    public Boolean fullscreen;

    // размеры камеры (размеры пропорциональны файлам с ресурсами)
    public Integer cameraWidth;
    public Integer cameraHeight;

    //размеры карты в текстуре для карт
    public Integer cardHeight;
    public Integer cardWidth;

    //текстуры
    public String welcomeTexture;   //экран привествия
    public String aboutTexture;     //экран about
    public String boardTexture;     //стол
    public String deckTexture;      //колода
    public String itemsTexture;     //башни, рубашки карт, и прочие текстуры
    public String titleTexture;     //логотип Arcomage
    
    //размеры текстур
    public Integer welcomeTextureWidth;
    public Integer welcomeTextureHeight;
    public Integer boardTextureWidth;
    public Integer boardTextureHeight;

    //карты
    public Integer deckTextureWidth;
    public Integer deckTextureHeight;

    //ресурсы
    public Integer resTextureWidth;
    public Integer resTextureHeight;
    
    public Integer towerBodyTextureWidth;
    public Integer towerBodyTextureHeight;
    public Integer towerHeadTextureWidth;
    public Integer towerHeadTextureHeight;
    
    public Integer wallBodyTextureWidth;
    public Integer wallBodyTextureHeight;
    public Integer wallHeadTextureWidth;
    public Integer wallHeadTextureHeight;
    
    //Координаты для загрузки штучных текстур
    public Integer deckUndoTextureX;
    public Integer deckUndoTextureY;
    
    public Integer brickTextureX;
    public Integer brickTextureY;
    public Integer gemTextureX;
    public Integer gemTextureY;
    public Integer beastTextureX;
    public Integer beastTextureY;
    
    public Integer towerBodyTextureX;
    public Integer towerBodyTextureY;
    public Integer towerHeadRedTextureX;
    public Integer towerHeadRedTextureY;
    public Integer towerHeadBlueTextureX;
    public Integer towerHeadBlueTextureY;
    
    public Integer wallBodyTextureX;
    public Integer wallBodyTextureY;
    public Integer wallHeadTextureX;
    public Integer wallHeadTextureY;


    //количество карт в текстуре колоды
    public Integer deckCountX;
    public Integer deckCountY;
    public Integer deckCountType;

}
