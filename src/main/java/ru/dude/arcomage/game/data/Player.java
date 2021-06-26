/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game.data;

import ru.dude.arcomage.game.AppImpl;
import ru.dude.arcomage.game.Arcomage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author elduderino
 */
public abstract class Player {

    String name;

    public ArrayList<Card> cards;

    public ArrayList<Card> maskCards;//карты обратной стороной

    public AtomicInteger actionsCount = new AtomicInteger(0);

    public Map<PlayResource,Integer> playResources = new HashMap<>();

    public Player(String name) {
        this.name = name;

        this.cards = new ArrayList<>();
        this.maskCards = new ArrayList<>();

        for (int i = 0; i < AppImpl.rules.startCardCount; ++i) {
            cards.add(AppImpl.cardManager.getEmptyCard());
            maskCards.add(AppImpl.cardManager.getEmptyCard());
        }

        playResources.put(PlayResource.TOWER_HP, AppImpl.rules.startTowerHP);
        playResources.put(PlayResource.WALL_HP, AppImpl.rules.startWallHP);

        playResources.put(PlayResource.BRICK_INCOME, AppImpl.rules.startBrickIncome);
        playResources.put(PlayResource.GEM_INCOME, AppImpl.rules.startGemIncome);
        playResources.put(PlayResource.BEAST_INCOME, AppImpl.rules.startBeastIncome);

        playResources.put(PlayResource.BRICK_COUNT, AppImpl.rules.startBrickCount);
        playResources.put(PlayResource.GEM_COUNT, AppImpl.rules.startGemCount);
        playResources.put(PlayResource.BEAST_COUNT, AppImpl.rules.startBeastCount);
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


    /**
     * Может ли игрок сыграть эту курту (хватает ли ресурсов)
     * @param card
     * @return
     */
    public boolean playable(Card card) {
        return card != null && card.getCostCount() != null && getResource(card.getCostType()) >= card.getCostCount();
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
        System.out.println("player " + name + " acceptTurn");
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


    public Integer getResource(PlayResource playResource) {
        return playResources.getOrDefault(playResource, 0);
    }

    public void addResource(PlayResource playResource, Integer addCount) {
        int newCount = Math.max(0, getResource(playResource) + addCount);
        playResources.put(playResource, newCount);
    }

    public void setResource(PlayResource playResource, Integer newCount) {
        playResources.put(playResource, newCount);
    }


    /**
     * Получить урон
     * Сначала по стене, потом по башне
     * @param count
     */
    public void damage(Integer count) {
        int damageWall = Math.min(getResource(PlayResource.WALL_HP), count);
        addResource(PlayResource.WALL_HP, -damageWall);
        addResource(PlayResource.TOWER_HP, damageWall - count);
    }

    /**
     * Событие завершения игры
     * @param end
     */
    public abstract void endGame(EndGameResult end);
}
