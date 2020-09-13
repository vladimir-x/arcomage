/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game.desk;

import ru.dude.arcomage.game.AppImpl;
import ru.dude.arcomage.game.RoundEnum;
import ru.dude.arcomage.game.data.Card;
import ru.dude.arcomage.game.data.Player;
import ru.dude.arcomage.game.interfaces.Actionable;
import ru.dude.arcomage.game.slot.ActiveSlot;
import ru.dude.arcomage.game.slot.DeckSlot;
import ru.dude.arcomage.game.slot.FlySlot;
import ru.dude.arcomage.game.slot.HandSlot;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Рука
 * @author elduderino
 */
public class Hand extends Deskzone implements Actionable {


    private static Map<Integer, ArrayList<HandSlot>> slotsCashe = new ConcurrentHashMap<Integer, ArrayList<HandSlot>>();

    DeckSlot deckSlot;
    ActiveSlot activeSlot;

    Player player;
    RoundEnum round;

    public Hand(Zones zone, ActiveSlot activeSlot, DeckSlot deckSlot) {
        super(zone);
        this.deckSlot = deckSlot;
        this.activeSlot = activeSlot;
    }


    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render(ShapeRenderer renderer, SpriteBatch spriteBatch) {
        super.render(renderer, spriteBatch);

        ArrayList<HandSlot> handSlots = getHandSlots(player);

        int emptySlotIndex = player.getEmptySlotIndex();
        for (int i = 0; i < handSlots.size(); ++i) {
            HandSlot slot = handSlots.get(i);

            slot.setCard(player.getCards().get(i));

            slot.setEmpty(emptySlotIndex != -1 && emptySlotIndex == i);

            slot.setMasked(isMasked());
            slot.render(renderer, spriteBatch);
        }
    }

    @Override
    public void action(float delta) {

    }

    public boolean selectAndPlay(float propX, float propY, boolean drop) {
        for (HandSlot handSlot : getHandSlots(player)) {
            if (handSlot.contains(propX, propY)) {
                Card card = player.getCards().get(handSlot.getPos());

                if (drop){
                    return player.dropCard(handSlot.getPos(),card);
                } else {
                    return player.playCard(handSlot.getPos(),card);
                }
            }
        }
        return false;
    }

    public boolean selectAndPlay(int position, Card card, boolean drop) {
        ArrayList<HandSlot> slots = getHandSlots(player);
        if (position >= 0 && position < slots.size()) {
            HandSlot handSlot = slots.get(position);
            handSlot.setCard(card);
            handSlot.umasked();
            return playSlot(handSlot, card, drop);
        } else {
            System.out.println(" promptToSelect position [" + position + "] incorrect . slot.size() =" + slots.size());
            return false;
        }
    }

    public boolean playSlot(HandSlot handSlot, Card card, boolean drop) {

        if (player.playable(card) || drop) {

            System.out.println(">> play slot [" + handSlot.getPos() + "] " + card);

            FlySlot selectedSlot = new FlySlot(handSlot, activeSlot, new Runnable() {
                @Override
                public void run() {
                    activeSlot.onGetCard();
                }
            });

            selectedSlot.setPlayedStep(AppImpl.control.getCurrentStepCount());
            selectedSlot.setDroped(drop);
            selectedSlot.setCard(card);

            handSlot.clearCard();
            player.removeCard(card);

            AppImpl.control.AnimateFlySlot(selectedSlot);

            return true;
        }
        return false;
    }

    public void takeCard() {

        for (int i = 0; i < player.getCards().size(); ++i) {
            if (player.getCards().get(i).equals(AppImpl.cardManager.getEmptyCard())) {
                HandSlot emptySlot = getHandSlots(player).get(i);
                takeOneCard(emptySlot);
            }
        }

        AppImpl.control.AnimateFlySlot(new FlySlot(new Runnable() {
            @Override
            public void run() {
                player.acceptTurn();
            }
        }));
    }

    private void takeOneCard(HandSlot handSlot) {
        final Card card = AppImpl.cardManager.selectRandomCard();

        final FlySlot newCardSlot = new FlySlot(deckSlot, handSlot, new Runnable() {
            @Override
            public void run() {
                player.takeCard(card);
            }
        });
        newCardSlot.setMasked(isMasked());
        newCardSlot.setCard(card);


        AppImpl.control.AnimateFlySlot(newCardSlot);
    }

    /**
     * получает слоты в руке для карт
     *
     * @param player
     * @return
     */
    private ArrayList<HandSlot> getHandSlots(Player player) {

        int size = player.getCards().size();
        if (slotsCashe.containsKey(size)) {
            return slotsCashe.get(size);
        }
        ArrayList<HandSlot> slots = new ArrayList<HandSlot>();

        int pos = 0;
        for (Card card : player.getCards()) {
            HandSlot handSlot = new HandSlot(this, pos++);
            handSlot.setCard(card);
            handSlot.setMasked(isMasked());
            handSlot.update();
            slots.add(handSlot);
        }

        slotsCashe.put(size, slots);
        return slots;
    }

    public void setPlayer(Player player, RoundEnum round) {
        this.player = player;
        this.round = round;

    }

    private boolean isMasked() {
        return round != RoundEnum.USER_TURN;
    }


}
