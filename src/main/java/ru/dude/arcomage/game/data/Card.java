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

    boolean switchTurn = true;

    public Card(String name, TextureRegion texture, boolean switchTurn) {
        this.name = name;
        this.texture = texture;
        this.switchTurn = switchTurn;
        this.cardActions = new ArrayList<>();

        // тестовое. Тут загружать для каждой карты свой набор
        this.cardActions.add(CardAction.ownerAddResIf(PlayResource.BEAST_COUNT, 10, 3, 1));
    }

    public String getName() {
        return name;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public boolean isSwitchTurn() {
        return switchTurn;
    }


    public List<CardAction> getCardActions() {
        return cardActions;
    }

    @Override
    public String toString() {
        return "Card{" +
                "name='" + name + '\'' +
                ", switchTurn=" + switchTurn +
                '}';
    }
}
