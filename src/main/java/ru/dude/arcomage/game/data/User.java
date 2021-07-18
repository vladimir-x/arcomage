/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game.data;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 *
 * @author elduderino
 */
public class User extends Player {


    /**
     * Функция вызвается при завершении игры
     */
    private Consumer<EndGameResult> endGameAlertFunction;

    public User(String name, Consumer<EndGameResult> endGameAlertFunction) {
        super(name);
        this.endGameAlertFunction = endGameAlertFunction;
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

        if (endGameAlertFunction != null) {
            endGameAlertFunction.accept(end);
        }
    }

}
