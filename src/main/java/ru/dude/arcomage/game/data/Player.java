/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game.data;

import ru.dude.arcomage.game.AppImpl;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author admin
 */
public abstract class Player {

    String name;

    public ArrayList<Card> cards;

    public ArrayList<Card> maskCards;//карты обратной стороной

    public AtomicInteger actionsCount = new AtomicInteger(0);

    // HP
    public Integer towerHP = 0;
    public Integer wallHP = 0;

    // ресурсы
    public Integer brickIncome = 0;
    public Integer gemIncome = 0;
    public Integer beastIncome = 0;

    public Integer brickCount = 0;
    public Integer gemCount = 0;
    public Integer beastCount = 0;

    public Player(String name) {
        this.name = name;

        this.cards = new ArrayList<>();
        this.maskCards = new ArrayList<>();

        for (int i = 0; i < AppImpl.rules.startCardCount; ++i) {
            cards.add(AppImpl.cardManager.getEmptyCard());
            maskCards.add(AppImpl.cardManager.getEmptyCard());
        }

        towerHP = AppImpl.rules.startTowerHP;
        wallHP = AppImpl.rules.startWallHP;

        brickIncome = AppImpl.rules.startBrickIncome;
        gemIncome = AppImpl.rules.startGemIncome;
        beastIncome = AppImpl.rules.startBeastIncome;

        brickCount = AppImpl.rules.startBrickCount;
        gemCount = AppImpl.rules.startGemCount;
        beastCount = AppImpl.rules.startBeastCount;
    }

    // при вставке первая карта - указана. 
    public Card takeCard(Card card) {
        Card lastCard = card;

        int emptySlotindex = getEmptySlotIndex();
        if (emptySlotindex != -1) {
            cards.set(emptySlotindex, lastCard);
            maskCards.set(emptySlotindex, lastCard);

            System.out.println(name + " insert card [" + emptySlotindex + "]: " + lastCard);
            return lastCard;
        } else {
            // если не нашли пустого слота
            cards.add(lastCard);
            maskCards.add(AppImpl.cardManager.getUndoCard());
            System.out.println(name + " put card: " + lastCard);
            return lastCard;
        }
    }

    public int getEmptySlotIndex(){
        for (int i = 0; i < cards.size(); ++i) {
            if (cards.get(i).equals(AppImpl.cardManager.getEmptyCard())) {
                return i;
            }
        }
        return -1;
    }

    // взять карты в начале игры
    public void takeCards() {
        for (int i = 0; i < cards.size(); ++i) {
            if (cards.get(i).equals(AppImpl.cardManager.getEmptyCard())) {

                Card card = AppImpl.cardManager.selectRandomCard();
                cards.set(i, card);
                maskCards.set(i, AppImpl.cardManager.getUndoCard());

                System.out.println(name + " insert card [" + i + "]: " + card);
            }
        }
    }

    // событие - ход передан этому игроку
    public abstract void ding();

    public abstract ArrayList<Card> getCards();

    public boolean playable(Card card) {
        return true;
    }

    public void removeCard(Card card) {
        int k = cards.indexOf(card);
        if (k > -1) {
            cards.set(k,AppImpl.cardManager.getEmptyCard());
            maskCards.set(k,AppImpl.cardManager.getEmptyCard());
        } else {
            System.out.println("not contain");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardTitles() {

        StringBuilder sb = new StringBuilder();
        for (Card card : getCards()) {
            if (sb.length()>0){
                sb.append(",");
            }
            sb.append(card.getName());
        }

        return sb.toString();

    }

    /**
     * принять ход
     */
    public void acceptTurn() {
        System.out.println("player " + name + "acceptTurn");
        actionsCount.incrementAndGet();
        ding();
    }

    /**
     * Сыграть карту
     * @param r
     * @param card
     */
    public boolean playCard(int r, Card card){
        if (actionsCount.get() > 0 && playable(card)){
            actionsCount.decrementAndGet();
            return AppImpl.control.playCard(r, card, false);
        }
        return false;
    }

    /**
     * Сбросить карту
     * @param r
     * @param card
     */
    public boolean dropCard(int r, Card card){
        if (actionsCount.get() > 0) {
            actionsCount.decrementAndGet();
            return AppImpl.control.playCard(r, card, true);
        }
        return false;
    }

    public Integer getIncome(ResourceType resourceType) {
        switch (resourceType){
            case BRICK:
                return brickIncome;
            case GEM:
                return gemIncome;
            case BEAST:
                return beastIncome;
        }
        throw new IllegalArgumentException("ResourceType " + resourceType + " not supported");
    }

    public Integer getCurrentCount(ResourceType resourceType) {
        switch (resourceType) {
            case BRICK:
                return brickCount;
            case GEM:
                return gemCount;
            case BEAST:
                return beastCount;
        }
        throw new IllegalArgumentException("ResourceType " + resourceType + " not supported");
    }
}
