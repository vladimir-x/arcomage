/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game.slot;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.dude.arcomage.game.AppImpl;
import ru.dude.arcomage.game.data.Card;
import ru.dude.arcomage.game.interfaces.Rendereble;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author elduderino
 */
public abstract class Slot implements Rendereble {

    private static Card maskCard = AppImpl.cardManager.getUndoCard();

    private Card card;
    Rectangle rect;
    private Boolean droped;
    private Boolean masked;
    private Boolean empty;
    Integer playedStep; // на каком ходу был разыгран

    public Slot() {
        card = null;
        droped = false;
        masked = false;
        empty = false;
    }

    public Rectangle getRect() {
        return rect;
    }

    void onGetCard() {
    }

    @Override
    public void render(ShapeRenderer renderer, SpriteBatch spriteBatch) {
        if (card != null) {
            render(masked ? maskCard.getTexture() : card.getTexture(), card.getName(), renderer, spriteBatch);
        }
    }


    private void render(TextureRegion textureRegion, String text, ShapeRenderer renderer, SpriteBatch spriteBatch) {
        if (textureRegion != null) {
            spriteBatch.begin();

            if (!empty) {
                spriteBatch.draw(textureRegion, rect.x, rect.y);
            }

            AppImpl.resources.font.draw(spriteBatch, text, rect.x, rect.y +rect.height);

            spriteBatch.end();
            if (droped) {

                Gdx.gl.glEnable(GL20.GL_BLEND);
                Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                Color c = Color.GRAY.cpy().sub(0, 0, 0, 0.5f);

                renderer.begin(ShapeRenderer.ShapeType.Filled);
                renderer.setColor(c);
                renderer.rect(
                        rect.x,
                        rect.y,
                        rect.width,
                        rect.height
                );
                renderer.end();
                Gdx.gl.glDisable(GL20.GL_BLEND);
            }
        }
    }

    public void clearCard(){
        card = null;
        masked = false;
        droped = false;
        playedStep = 0;
    }

    public Boolean getDroped() {
        return droped;
    }

    public void setDroped(Boolean droped) {
        this.droped = droped;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setPlayedStep(Integer playedStep) {
        this.playedStep = playedStep;
    }

    public Integer getPlayedStep() {
        return playedStep;
    }

    public Boolean getMasked() {
        return masked;
    }

    public void setMasked(Boolean masked) {
        this.masked = masked;
    }

    public Boolean getEmpty() {
        return empty;
    }

    public void setEmpty(Boolean empty) {
        this.empty = empty;
    }

    @Override
    public String toString() {
        return "Slot{" +
                "card=" + card +
                ", droped=" + droped +
                ", masked=" + masked +
                ", empty=" + empty +
                ", playedStep=" + playedStep +
                '}';
    }
}
