/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.dude.arcomage.game.data.Card;
import ru.dude.arcomage.game.data.CardAction;

import java.util.*;

/**
 *
 * @author elduderino
 */
public class CardManager {

    private static Random rand = new Random(System.currentTimeMillis());

    private List<Card> cards;

    private Card undoCard;

    private Card emptyCard;

    public CardManager() {
        loadCards();
    }

    private void loadCards(){
        cards = new ArrayList<Card>();

        for (Map.Entry<String, TextureRegion> entry : AppImpl.resources.getCardsTextureMap().entrySet()) {
            String name = entry.getKey();
            TextureRegion texture = entry.getValue();
            String cardActionsLine = AppImpl.resources.getCardActionsLine(name);

            Card card = Card.parse(name, texture, cardActionsLine);
            if (card != null) {
                cards.add(card);
            }
        }

        undoCard = Card.undo(AppImpl.resources.deckUndoTexture);

        emptyCard = Card.empty();
    }

    public Card selectRandomCard() {
        int index = rand.nextInt(cards.size());
        return cards.get(index).copy();
    }

    public Card getUndoCard() {
        return undoCard;
    }

    public Card getEmptyCard() {
        return emptyCard;
    }
}
