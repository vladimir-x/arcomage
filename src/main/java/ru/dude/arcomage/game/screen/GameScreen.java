/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game.screen;

import ru.dude.arcomage.game.AppImpl;
import ru.dude.arcomage.game.Arcomage;
import ru.dude.arcomage.game.GameInput;
import ru.dude.arcomage.game.GridRender;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 *
 * @author elduderino
 */
public class GameScreen extends BaseScreen {

    private GameInput input;

    private Arcomage appImpl;
    public ShapeRenderer renderer;
    public SpriteBatch spriteBatch;

    GridRender gridRender;

    public GameScreen(Arcomage appImpl, GameInput input) {
        super(input);
        this.appImpl = appImpl;

        spriteBatch = new SpriteBatch();
        renderer = new ShapeRenderer();
        spriteBatch.setProjectionMatrix(cam.combined);
        spriteBatch.enableBlending();
        renderer.setProjectionMatrix(cam.combined);

        gridRender = new GridRender();

    }

    @Override
    public void render(float delta) {
        //super.render(delta);

        try {
            appImpl.action(delta);
            appImpl.render(renderer, spriteBatch);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        //gridRender.render(renderer, spriteBatch);

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        renderer.setProjectionMatrix(cam.combined);
        spriteBatch.setProjectionMatrix(cam.combined);
        appImpl.update();
        gridRender.update();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        renderer.dispose();
    }
}
