/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game.slot;

import ru.dude.arcomage.game.AppImpl;
import ru.dude.arcomage.game.desk.Board;
import ru.dude.arcomage.game.interfaces.Actionable;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import ru.dude.arcomage.game.render.RenderUtil;

/**
 * Играемая карта
 *
 * @author elduderino
 */
public class ActiveSlot extends Slot implements Actionable {

    private static final float CARD_ACTIVE_TIME = 0.5f;

    Board owner;
    float remainingTime;

    FlySlot playedCard;

    public ActiveSlot(Board owner) {
        this.owner = owner;
        remainingTime = 0f;
        setCard(null);
    }

    @Override
    public void update() {
        float centrX = owner.getRectangle().x + owner.getRectangle().width / 2.f;
        float botY = owner.getRectangle().y;

        rect = new Rectangle(
                centrX - AppImpl.settings.cardWidth / 2.f,
                botY,
                AppImpl.settings.cardWidth,
                AppImpl.settings.cardHeight);
    }

    @Override
    public void render(ShapeRenderer renderer, SpriteBatch spriteBatch) {
        super.render(renderer, spriteBatch);

        RenderUtil.renderBorderRect(renderer, Color.ORANGE, rect);

        if (playedCard != null) {
            playedCard.render(renderer, spriteBatch);
        }
    }

    @Override
    public void onGetCard() {
        remainingTime = CARD_ACTIVE_TIME;
        executeCard();
    }

    @Override
    public void action(float delta) {
        if (remainingTime > 0.000001f) {

            remainingTime -= delta;

            if (remainingTime < 0.000001f) {
                final PlayedSlot destonation = owner.getLastPlayedSlot();
                playedCard = new FlySlot(this, destonation, new Runnable() {
                    @Override
                    public void run() {
                        destonation.onGetCard();
                    }
                });
                playedCard.setTransparent(true);
    //            executeCard();
                setCard(null);
            }
        }
        if (playedCard != null) {
            playedCard.action(delta);
            if (playedCard.getCard() == null) {
                playedCard = null;
            }
        }
    }
    
    private void executeCard(){
        System.out.println(" executeCard ");
        getCard().play();
        if (getCard().isSwitchTurn()){
//            AppImpl.control.switchTurn();
        }
    }

}
