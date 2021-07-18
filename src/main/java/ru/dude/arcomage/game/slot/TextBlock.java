/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game.slot;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import ru.dude.arcomage.game.AppImpl;
import ru.dude.arcomage.game.interfaces.Rendereble;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import ru.dude.arcomage.game.render.RenderUtil;

/**
 *
 * @author elduderino
 */
public abstract class TextBlock implements Rendereble {

    public enum HAlign {
        LEFT, CENTER, RIGHT
    }

    public enum VAlign {
        BOTTOM, CENTER, TOP
    }


    protected Rectangle rect;

    protected float x;

    protected float y;

    protected String text;

    // выравнивание относительно x
    protected HAlign hAlign = HAlign.CENTER;

    // выравнивание относительно y
    protected VAlign vAlign = VAlign.BOTTOM;

    protected boolean hasBackground = false;

    public TextBlock() {
    }

    abstract String updateTextOnRender();

    @Override
    public void render(ShapeRenderer renderer, SpriteBatch spriteBatch) {

        text = updateTextOnRender();

        recalculate();

        if (hasBackground) {
            renderer.begin(ShapeRenderer.ShapeType.Filled);
            renderer.setColor(Color.BLACK);
            renderer.rect(rect.x, rect.y, rect.width, rect.height);
            renderer.end();
        }

        RenderUtil.renderBorderRect(renderer, Color.ORANGE, rect);
        
        spriteBatch.begin();
        AppImpl.resources.font.draw(spriteBatch, text, rect.x, rect.y + rect.height);
        spriteBatch.end();

    }


    private void recalculate() {
        BitmapFont.TextBounds tb = AppImpl.resources.font.getBounds(text);

        float currX =
                hAlign == HAlign.LEFT ? x - tb.width/2 :
                        hAlign == HAlign.CENTER ? x :
                                hAlign == HAlign.RIGHT ? x + tb.width/2 : 0;


        float currY =
                vAlign == VAlign.BOTTOM ? y - tb.height :
                        vAlign == VAlign.CENTER ? y - tb.height / 2f :
                                vAlign == VAlign.TOP ? y : 0;

        rect = new Rectangle(currX, currY, tb.width, tb.height);
    }

    protected void updateXY(float x, float y){
        this.x = x;
        this.y = y;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
