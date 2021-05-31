/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.desktop;

import ru.dude.arcomage.game.AppImpl;
import ru.dude.arcomage.game.Rules;
import ru.dude.arcomage.game.Settings;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.io.File;

/**
 *
 * @author admin
 */
public class ArcomageDesktop {

    static MainFrame frame;
    static Settings settings;
    static Rules rules;

    public static void main(String[] argv) {

        makePanel();
    }

    public static void makePanel() {

        settings = loadSettings();
        rules = loadDefaultRules();
        AppImpl applicationImpl = new AppImpl(settings, rules);

        frame = new MainFrame(applicationImpl, settings);
    }

    public static void closeWindow(Window window) {
        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
    }

    public static void applyVideoSettings() {
        frame.setResizable(true);
        frame.setSize(settings.windowWidth, settings.windowHeight);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }

    public static Settings loadSettings() {
        // реализация из файла настроек позже
        Settings settings = new Settings();

        settings.windowWidth = 800;
        settings.windowHeight = 600;

        settings.fullscreen = false;

        settings.cameraWidth = 640;
        settings.cameraHeight = 480;

        settings.cardWidth = 95;
        settings.cardHeight = 128;

        //шрифты
        settings.fontFnt ="data" + File.separator + "fc.fnt";
        settings.fontPng ="data" + File.separator + "fc.png";
        
        //текстуры
        settings.welcomeTexture = "data" + File.separator + "layout.bmp";
        settings.aboutTexture = "data" + File.separator + "credits.bmp";
        settings.boardTexture = "data" + File.separator + "layout.bmp";
        settings.deckTexture = "data" + File.separator + "deck_ru.bmp";
        settings.itemsTexture = "data" + File.separator + "items.png";
        settings.titleTexture = "data" + File.separator + "title.bmp";

        // ширина и высота текстур
        settings.welcomeTextureWidth = 640;
        settings.welcomeTextureHeight = 480;

        settings.boardTextureWidth = 640;
        settings.boardTextureHeight = 480;

        settings.deckTextureWidth = 95;
        settings.deckTextureHeight = 128;

        settings.towerBodyTextureWidth = 45;
        settings.towerBodyTextureHeight = 200;
        settings.towerHeadTextureWidth = 67;
        settings.towerHeadTextureHeight = 94;
        
        settings.wallBodyTextureWidth =24;
        settings.wallBodyTextureHeight =200;
        settings.wallHeadTextureWidth=45;
        settings.wallHeadTextureHeight=38;
        
        settings.resTextureWidth = 78;
        settings.resTextureHeight = 72;

        // место текстуры
        settings.deckUndoTextureX = 0;
        settings.deckUndoTextureY = 0;

        settings.towerBodyTextureX = 330;
        settings.towerBodyTextureY = 0;
        settings.towerHeadRedTextureX =101;
        settings.towerHeadRedTextureY = 0;
        settings.towerHeadBlueTextureX =100;
        settings.towerHeadBlueTextureY =100;
        
        settings.wallBodyTextureX =300;
        settings.wallBodyTextureY =0;
        settings.wallHeadTextureX=0;
        settings.wallHeadTextureY=128;
        
        settings.brickTextureX = 200;
        settings.brickTextureY = 0;
        settings.gemTextureX = 200;
        settings.gemTextureY = 72;
        settings.beastTextureX = 200;
        settings.beastTextureY = 144;

        // количество в циклах
        settings.deckCountX = 10;
        settings.deckCountY = 12;
        settings.deckCountType = 34;

        return settings;
    }

    /**
     * Загрузка правил.
     * Потом переделать на настраиваемые правила и на загрузку из шаблона таверны
     */
    public static Rules loadDefaultRules() {
        Rules rules = new Rules();

        rules.startCardCount = 6;

        rules.startTowerHP = 80;
        rules.startWallHP = 40;

        rules.startBrickIncome = 1;
        rules.startGemIncome = 2;
        rules.startBeastIncome = 3;

        rules.startBrickCount = 5;
        rules.startGemCount = 6;
        rules.startBeastCount = 7;

        return rules;
    }

    public static void saveSettings(Settings settings) {
        // реализация в фаил настроек позже
    }
}
