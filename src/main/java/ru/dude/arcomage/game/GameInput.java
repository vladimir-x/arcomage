/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game;

import com.badlogic.gdx.InputProcessor;
import ru.dude.arcomage.desktop.MainFrame;

/**
 *
 * @author elduderino
 */
public class GameInput implements InputProcessor {

    private AppImpl appImpl;

    public GameInput(AppImpl appImpl) {
        this.appImpl = appImpl;
    }

    @Override
    public boolean keyDown(int keycode) {
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if (appImpl.control != null) {
            appImpl.control.promptToStep(recalcX(screenX), recalcY(screenY), button);
        }

        return true;

    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {

        //
        MainFrame.getInstance().setTitle("coord: x = " + recalcX(screenX) + ", y = " + recalcY(screenY));
        //
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return true;
    }


    private float recalcX(int screenX){
        return screenX * AppImpl.settings.cameraWidth / (1.0f * AppImpl.settings.windowWidth);
    }

    private float recalcY(int screenY){
        return (AppImpl.settings.windowHeight - screenY) * AppImpl.settings.cameraHeight / (1.0f * AppImpl.settings.windowHeight);
    }

}
