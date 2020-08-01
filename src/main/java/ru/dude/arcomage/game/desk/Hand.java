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
 *
 * @author elduderino
 */
public class Hand extends Deskzone implements Actionable {


    private static Map<Integer,  ArrayList<HandSlot>> slotsCashe = new ConcurrentHashMap<Integer, ArrayList<HandSlot>>();

    //ArrayList<HandSlot> slots;
    DeckSlot deckSlot;
    ActiveSlot activeSlot;
    //!!!! HandSlot emptySlot; // у разных игроков она разная

    Player player;
    RoundEnum round;

    volatile boolean playing;

    public String debugstr;

    public Hand(int zone, ActiveSlot activeSlot, DeckSlot deckSlot) {
        super(zone);
        this.deckSlot = deckSlot;
        this.activeSlot = activeSlot;
   //     this.player = player;
      //  slots = new ArrayList<HandSlot>();

        playing = true;
    }


    @Override
    public void update() {
        super.update();
        /*
         slots.clear();
         for (int i = 0; i < player.getCards().size(); ++i) {
         HandSlot slot = new HandSlot(this, i);
         slot.update();
         slot.setCard(player.getCards().get(i));
         slots.add(slot);
         }*/
    }

    @Override
    public void render(ShapeRenderer renderer, SpriteBatch spriteBatch) {
        super.render(renderer, spriteBatch);

        for (HandSlot slot : getHandSlots(player)) {
            slot.setMasked(isMasked());
            slot.render(renderer, spriteBatch);
        }
    }

    @Override
    public void action(float delta) {

    }

    public boolean promptToSelect(float propX, float propY, boolean drop) {
        for (HandSlot handSlot : getHandSlots(player)) {
            if (handSlot.contains(propX, propY)) {
                Card card = player.getCards().get(handSlot.getPos());
                return playSlot(handSlot, card, drop);
            }
        }
        return false;
    }

    public boolean promptToSelect(int position, Card card, boolean drop) {
        ArrayList<HandSlot> slots = getHandSlots(player);
        if (position >= 0 && position < slots.size()) {
            HandSlot handSlot = slots.get(position);
            handSlot.setCard(card);
            handSlot.umasked();
            return playSlot(handSlot,card, drop);
        } else {
            System.out.println(" promptToSelect position ["+position+"] incorrect . slot.size() =" +slots.size() );
            return false;
        }
    }

    public boolean playSlot(HandSlot handSlot, Card card, boolean drop) {

        if (player.playable(card) || drop) {

            System.out.println(">> play slot " + card);
            //playing = false;
            FlySlot selectedSlot = new FlySlot(handSlot, activeSlot,true);
            selectedSlot.setPlayedStep(AppImpl.control.getCurrentStepCount());
            selectedSlot.setDroped(drop);
            selectedSlot.setCard(card);

            handSlot.clearCard();
            player.removeCard(card);
            //emptySlot = handSlot;

            AppImpl.control.AnimateFlySlot(selectedSlot, null);

            return true;
        }
        return false;
    }

    public void takeCard(boolean atStep) {

        int emptySlotIndex = player.getEmptySlotIndex();
        if (emptySlotIndex == -1) {
            if (player.getCards().size()<AppImpl.settings.cardCount) {
                for (int i = player.getCards().size(); i < AppImpl.settings.cardCount; ++i) {
                    HandSlot slot = new HandSlot(this, i);
                    slot.update();
                    takeOneCard(slot);
                }
            } else {
                player.ding();
            }
        } else {
            HandSlot emptySlot = getHandSlots(player).get(emptySlotIndex);
            takeOneCard(emptySlot);
        }
    }

    private void takeOneCard(HandSlot handSlot) {
        FlySlot newCardSlot = new FlySlot(deckSlot, handSlot,false);
        newCardSlot.setMasked(isMasked());
        final Card card = AppImpl.cardManager.selectRandomCard();
        newCardSlot.setCard(card);
        /*
        if (!atStep) {
            player.takeCard(card);
        }
        */
        AppImpl.control.AnimateFlySlot(newCardSlot, new Runnable() {

            @Override
            public void run() {
                player.takeCard(card);
                player.ding();
            }
        });
    }

    /**
     * получает слоты в руке для карт
     * @param player
     * @return
     */
    private ArrayList<HandSlot> getHandSlots(Player player){

        int size = player.getCards().size();
        if (slotsCashe.containsKey(size)){
            return slotsCashe.get(size);
        }
        ArrayList<HandSlot> slots  = new ArrayList<HandSlot>();

        int pos = 0;
        for (Card card : player.getCards()) {
            HandSlot handSlot = new HandSlot(this, pos++);
            handSlot.setCard(card);
            handSlot.setMasked(isMasked());
            handSlot.update();
            slots.add(handSlot);
        }

        slotsCashe.put(size,slots);
        return slots;
    }

    public void setPlayer(Player player, RoundEnum round){
        this.player = player;
        this.round = round;

    }

    public void setPlaying(RoundEnum round) {
        playing = round == RoundEnum.USER_TURN;

        player.ding();
    }

    private boolean isMasked(){
        return round != RoundEnum.USER_TURN;
    }



}
