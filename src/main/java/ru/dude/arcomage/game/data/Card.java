/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game.data;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 *
 * @author elduderino
 */
public class Card {

    String name;
    TextureRegion texture;

    // some action fields here

    boolean switchTurn = true;

    public Card(String name, TextureRegion texture, boolean switchTurn) {
        this.name = name;
        this.texture = texture;
        this.switchTurn = switchTurn;
    }

    public void play(){
        //
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
}
