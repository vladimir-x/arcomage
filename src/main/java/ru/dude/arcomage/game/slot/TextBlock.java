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

    protected Rectangle rect;

    protected String text;

    public TextBlock() {
    }

    abstract String updateTextOnRender();

    @Override
    public void render(ShapeRenderer renderer, SpriteBatch spriteBatch) {

        updateByXYText(rect.x, rect.y);

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.BLACK);
        renderer.rect(rect.x, rect.y, rect.width, rect.height);
        renderer.end();

        RenderUtil.renderBorderRect(renderer, Color.ORANGE, rect);
        
        spriteBatch.begin();
        AppImpl.resources.font.draw(spriteBatch, text, rect.x, rect.y + rect.height);
        spriteBatch.end();

    }

    protected void updateByXYText(float x, float y){
        text = updateTextOnRender();
        BitmapFont.TextBounds tb = AppImpl.resources.font.getBounds(text);
        rect = new Rectangle(x, y, tb.width, tb.height);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
