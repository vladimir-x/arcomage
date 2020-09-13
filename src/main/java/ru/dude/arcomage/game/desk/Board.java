/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game.desk;

import ru.dude.arcomage.game.AppImpl;
import ru.dude.arcomage.game.interfaces.Actionable;
import ru.dude.arcomage.game.slot.ActiveSlot;
import ru.dude.arcomage.game.slot.DeckSlot;
import ru.dude.arcomage.game.slot.FlySlot;
import ru.dude.arcomage.game.slot.PlayedSlot;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

/**
 * Стол с картами
 * @author admin
 */
public class Board extends Deskzone implements Actionable {

    private static final int LINE_CNT = 4;
    public static final int CARDS_SPACE_X = 30;
    public static final int CARDS_SPACE_Y = 40;

    
    DeckSlot discardSlot;
    DeckSlot deckSlot;
    ActiveSlot activeSlot;

    ArrayList<PlayedSlot> playedSlots;
    PlayedSlot lastSlot;

    public Board(Zones zone) {
        super(zone);
        discardSlot = new DeckSlot(this);
        deckSlot = new DeckSlot(this);
        activeSlot = new ActiveSlot(this);
        playedSlots = new ArrayList<PlayedSlot>();
        makeEmptySlot();
    }

    @Override
    public void update() {
        super.update();

        discardSlot.update();
        deckSlot.update();
        activeSlot.update();

        for (PlayedSlot playedSlot : playedSlots) {
            playedSlot.update();
        }

    }


    public void renderBack(ShapeRenderer renderer, SpriteBatch spriteBatch) {
        super.render(renderer, spriteBatch);


    }
    @Override
    public void render(ShapeRenderer renderer, SpriteBatch spriteBatch) {

        for (PlayedSlot playedSlot : playedSlots) {
            playedSlot.render(renderer, spriteBatch);
        }
        discardSlot.render(renderer, spriteBatch);
        deckSlot.render(renderer, spriteBatch);
        activeSlot.render(renderer, spriteBatch);

    }

    public void makeEmptySlot() {

        if (playedSlots.size() > 0 && playedSlots.get(playedSlots.size() - 1).getCard() == null) {
            return;
        }

        int posX = 1, posY = 0;
        if (lastSlot != null) {
            if (lastSlot.posX != LINE_CNT - 1) {
                posX = lastSlot.posX + 1;
                posY = lastSlot.posY;
            } else {
                posX = 0;
                posY = lastSlot.posY + 1;
            }
        }

        lastSlot = new PlayedSlot(this, posX, posY);

        playedSlots.add(lastSlot);

    }

    public ActiveSlot getActiveSlot() {
        return activeSlot;
    }

    public DeckSlot getDeckSlot() {
        return deckSlot;
    }

    public PlayedSlot getLastPlayedSlot() {
        return lastSlot;
    }

    @Override
    public void action(float delta) {
        activeSlot.action(delta);
    }

    /**
     * убрать с поля карты с предыдущего хода
     */
    public void clearPrevStep() {

        Integer currentStep = AppImpl.control.getCurrentStepCount();
        for (int i = 0; i < playedSlots.size(); ++i) {
            PlayedSlot slot = playedSlots.get(i);
            Integer cartStep = slot.getPlayedStep();

            if (cartStep != null && (currentStep - cartStep > 1)) {

                FlySlot erasing = new FlySlot(slot, discardSlot);
                slot.setCard(null);
                AppImpl.control.AnimateFlySlot(erasing);
            } else {

                break;
            }
        }

        int empty = playedSlots.size();

        for (int i = 0; i < playedSlots.size(); ++i) {
            PlayedSlot slot = playedSlots.get(i);
            if (slot.getCard() == null) {
                if (i<empty){
                    empty = i;
                }
            } else if (empty<i){
                FlySlot shifting = new FlySlot(slot, playedSlots.get(empty));
                empty++;
                slot.setCard(null);
                AppImpl.control.AnimateFlySlot(shifting);
            }
        }

    }
}
