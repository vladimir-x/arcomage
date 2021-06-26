/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game.data;

import java.util.ArrayList;

/**
 *
 * @author elduderino
 */
public class User extends Player {


    public User(String name) {
        super(name);
    }

    @Override
    public void ding() {
        System.out.println("user turn !");
    }

    @Override
    public ArrayList<Card> getCards() {
        return cards;
    }

    @Override
    public void endGame(EndGameResult end) {
        System.out.println("user is " + end.name());
    }

}
