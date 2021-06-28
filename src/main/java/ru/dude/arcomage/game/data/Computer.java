/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game.data;

import ru.dude.arcomage.game.AppImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author elduderino
 */
public class Computer extends Player {

    private static Random randomGen = new Random(System.currentTimeMillis());

    public Computer(String name) {
        super(name);
    }

    @Override
    public void ding() {
        System.out.println("computer turn st");
        if (AppImpl.control.isRoundDropAndAgain()){
            randomDrop();
        } else {
            randomStep();
        }
        System.out.println("computer turn en");

    }

    public void randomStep() {
        List<Card> canPlayCards = cards.stream().filter(c -> playable(c)).collect(Collectors.toList());

        if (canPlayCards.size() > 0) {
            int r = randomGen.nextInt(canPlayCards.size());
            Card card = canPlayCards.get(r);
            System.out.println("Computer want to play : " + card.getName());
            playCard(r, card);
        } else {
            randomDrop();
        }
    }

    private void randomDrop(){
        int r = randomGen.nextInt(cards.size());
        Card card = cards.get(r);
        System.out.println("Computer want to drop : " + card.getName());
        dropCard(r, card);
    }

    @Override
    public ArrayList<Card> getCards() {
        return maskCards;
    }

    @Override
    public void endGame(EndGameResult end) {
        System.out.println("computer is " + end.name());
    }
}
