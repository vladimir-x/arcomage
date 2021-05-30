/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game.slot;

import ru.dude.arcomage.game.desk.Hand;
import ru.dude.arcomage.game.AppImpl;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import ru.dude.arcomage.game.render.RenderUtil;

/**
 *
 * @author elduderino
 */
public class HandSlot extends Slot {

    public static final int SLOT_SPACER = 10;

    private Hand hand;
    private int pos;

    public HandSlot(Hand hand, int pos) {
        this.hand = hand;
        this.pos = pos;
    }

    public Rectangle calcRect() {

        float width = hand.getRectangle().width / (AppImpl.rules.startCardCount);
        float centrX = width / 2.f;

        float centrY = hand.getRectangle().height / 2.f;

        Rectangle rect = new Rectangle(
                hand.getRectangle().x + (width * pos + centrX - AppImpl.settings.cardWidth / 2.f),
                hand.getRectangle().y + (centrY  - AppImpl.settings.cardHeight / 2.f),
                AppImpl.settings.cardWidth,
                AppImpl.settings.cardHeight
        );
        return rect;
    }

    public boolean contains(float propX, float propY) {
        return rect.contains(propX, propY);
    }

    @Override
    public void update() {
        rect = calcRect();
    }

    @Override
    public void render(ShapeRenderer renderer, SpriteBatch spriteBatch) {
        super.render(renderer, spriteBatch);

        RenderUtil.renderBorderRect(renderer, Color.GREEN, rect);
        renderer.end();
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void umasked() {
        setMasked(false);
    }
}
