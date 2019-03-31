/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game.data;

import ru.dude.arcomage.game.AppImpl;
import java.util.ArrayList;

/**
 *
 * @author admin
 */
public abstract class Player {

    String name;

    public ArrayList<Card> cards;

    public ArrayList<Card> maskCards;//карты обратной стороной

    public Player(String name) {
        this.name = name;
        this.cards = new ArrayList<Card>();
        this.maskCards = new ArrayList<Card>();
    }

    // при вставке первая карта - указана. 
    public Card takeCard(Card card) {
        Card lastCard = card;
        cards.add(lastCard);
        maskCards.add(AppImpl.cardManager.getUndoCard());
        return lastCard;
    }

    // взять карты в начале игры
    public Card takeCards() {

        Card lastCard = null;
        for (int i = cards.size(); i < AppImpl.settings.cardCount; ++i) {
            lastCard = AppImpl.cardManager.selectRandomCard();
            cards.add(lastCard);
            maskCards.add(AppImpl.cardManager.getUndoCard());
        }
        return lastCard;
    }

    // событие - ход передан этому игроку
    public abstract void ding();

    public abstract ArrayList<Card> getCards();

    public boolean playable(Card card) {
        return true;
    }

    public void removeCard(Card card) {
        if (cards.contains(card)) {
            cards.remove(card);
            maskCards.remove(1);
        } else {
            System.out.println("not contain (");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
