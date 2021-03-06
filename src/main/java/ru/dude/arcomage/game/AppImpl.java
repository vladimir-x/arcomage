/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game;

import ru.dude.arcomage.desktop.MainFrame;
import ru.dude.arcomage.game.interfaces.GameControlable;
import ru.dude.arcomage.game.screen.GameScreen;
import ru.dude.arcomage.game.screen.WelcomeScreen;
import com.badlogic.gdx.Game;

/**
 *
 * @author elduderino
 */
public class AppImpl extends Game {

    public static Settings settings;
    public static Rules rules;
    public static Resource resources;
    public static GameControlable control;
    public static CardManager cardManager;

//    public Arcomage arcomage;
    private WelcomeScreen welcomeScreen;
    private GameScreen gameScreen;
    private GameInput input;

    private boolean gameStart;

    public AppImpl(Settings settings, Rules rules) {
        AppImpl.settings = settings;
        AppImpl.rules = rules;
    }

    @Override
    public void create() {
        AppImpl.resources = new Resource();
        AppImpl.cardManager = new CardManager();

        gameStart = false;
 //       arcomage = new Arcomage();
 //       control = arcomage;
        input = new GameInput(this);
        welcomeScreen = new WelcomeScreen(input);
        setScreen(welcomeScreen);
        
       // arcomage.startGame();
    }

    @Override
    public void dispose() {
        welcomeScreen.dispose();
        gameScreen.dispose();
    }

    public Settings getSettings() {
        return settings;
    }

    public void restart() {
        gameStart = true;
        Arcomage arcomage = new Arcomage(){

            @Override
            public void showEndGameDialog(String message) {
                MainFrame.getInstance().showMessageDialog(message);
            }
        };
        control = arcomage;
        if (gameScreen !=null){
            gameScreen.dispose();
        }
        gameScreen = new GameScreen(arcomage, input);
        setScreen(gameScreen);

        arcomage.startGame(true);
    }

    public void end() {
        gameStart = false;
        setScreen(welcomeScreen);
    }

    public boolean isGameStart() {
        return gameStart;
    }
}
