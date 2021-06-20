/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game.data;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author elduderino
 */
public class Card {

    String name;
    TextureRegion texture;

    // стимость игры карты
    PlayResource costType;
    Integer costCount;

    List<CardAction> cardActions;

    /**
     * Позволяет сыграть ещё одну карту в этом ходе
     */
    boolean playAgain;

    /**
     * Может быть сброшена
     */
    boolean droppable;

    public Card(String name, TextureRegion texture, PlayResource costType, Integer costCount, List<CardAction> cardActions, boolean playAgain, boolean droppable) {
        this.name = name;
        this.texture = texture;
        this.costType = costType;
        this.costCount = costCount;
        this.cardActions = cardActions;

        this.playAgain = playAgain;
        this.droppable = droppable;

        if (cardActions != null && cardActions.size() == 0) {
            System.out.println("warn: card " + name + " without actions");
        }
/*
        if (cardActions != null) {
            // тестовое. Тут загружать для каждой карты свой набор
            this.cardActions.add(CardAction.ownerAddResIf(PlayResource.BEAST_COUNT, 10, 3, 1));
            this.cardActions.add(CardAction.ownerAdd(PlayResource.TOWER_HP, 1));
            this.cardActions.add(CardAction.enemyAdd(PlayResource.WALL_HP, 1));
        }*/
    }


    public Card copy() {
        return new Card(name, texture, costType, costCount, cardActions, playAgain, droppable);
    }

    public static Card undo(TextureRegion texture) {
        return new Card("undo", texture, null, null, null, false, false);
    }

    public static Card empty() {
        return new Card("empty", null, null, null, null, false, false);
    }

    public static Card parse(String name, TextureRegion texture, String cardActionsLine) {

        List<CardAction> cardActions = new ArrayList<>();
        try {
            if (cardActionsLine != null && cardActionsLine.length() > 0) {
                String[] parts = cardActionsLine.split(";");

                String[] costParts = parts[0].split("\\.");

                PlayResource costType = PlayResource.valueOf(costParts[0]);
                Integer costCount = Integer.valueOf(costParts[1]);
                boolean playAgain = false;
                boolean canDrop = true;

                for (int i = 1; i < parts.length; ++i) {
                    if (parts[i].equals("PLAY_AGAIN")) {
                        playAgain = true;
                    } else if (parts[i].equals("CAN_NOT_DROP")) {
                        canDrop = false;
                    } else {
                        cardActions.add(CardAction.parse(parts[i]));
                    }
                }

                return new Card(name, texture, costType, costCount, cardActions, playAgain, canDrop);
            }
        } catch (Exception ex) {
            System.out.println("warn: card " + name + " parse exception");
            ex.printStackTrace();
        }

        return null;
    }


    public String getName() {
        return name;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public boolean isPlayAgain() {
        return playAgain;
    }

    public PlayResource getCostType() {
        return costType;
    }

    public Integer getCostCount() {
        return costCount;
    }

    public boolean isDroppable() {
        return droppable;
    }

    public List<CardAction> getCardActions() {
        return cardActions;
    }

    @Override
    public String toString() {
        return "Card{" +
                "name='" + name + '\'' +
                ", playAgain=" + playAgain +
                '}';
    }
}
