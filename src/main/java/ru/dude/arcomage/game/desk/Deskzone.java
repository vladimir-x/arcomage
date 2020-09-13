/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game.desk;

import ru.dude.arcomage.game.AppImpl;
import ru.dude.arcomage.game.interfaces.Rendereble;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * Одна из зон на доске
 * @author elduderino
 */
public class Deskzone implements Rendereble {

    private static float vertBound = 0.15f;
    private static float horizBound = 0.3f;

    private static int hpPanelWidth = 120;

    private Zones zone;

    private Rectangle rect;

    //
    private Color color;

    public Deskzone() {
        this.zone = Zones.CENTER;
    }

    public Deskzone(Zones zone) {
        this.zone = zone;
    }

    public Rectangle calcRectangle() {
        int width = AppImpl.settings.cameraWidth;
        int height = AppImpl.settings.cameraHeight;

        float leftBotX = width * vertBound;
        float leftBotY = height * horizBound;
        float rightTopX = width - leftBotX;
        float rightTopY = height - leftBotY;

        Rectangle rect = null;

        switch (zone) {
            case CENTER:
                return new Rectangle(leftBotX, leftBotY, rightTopX - leftBotX, rightTopY);
            case WEST://запад
                return new Rectangle(0, leftBotY, leftBotX, rightTopY);
            case SOUTH:
                return new Rectangle(0, 0, width, leftBotY);
            case EAST:
                return new Rectangle(rightTopX, leftBotY, leftBotX, rightTopY);

            case HP_WEST:
                return new Rectangle(leftBotX, leftBotY, hpPanelWidth, rightTopY);
            case HP_EAST:
                return new Rectangle(rightTopX - hpPanelWidth, leftBotY, hpPanelWidth, rightTopY);
            default:
                throw new AssertionError();
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void update() {
        rect = calcRectangle();
    }

    @Override
    public void render(ShapeRenderer renderer, SpriteBatch spriteBatch) {

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(color);
        renderer.rect(
                rect.x,
                rect.y,
                rect.width,
                rect.height
        );
        renderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

    }

    //@Override
    public Rectangle getRectangle() {
        return rect;
    }

}
