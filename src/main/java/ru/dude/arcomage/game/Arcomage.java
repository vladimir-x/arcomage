/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import ru.dude.arcomage.game.data.Card;
import ru.dude.arcomage.game.data.Computer;
import ru.dude.arcomage.game.data.Player;
import ru.dude.arcomage.game.data.User;
import ru.dude.arcomage.game.desk.*;
import ru.dude.arcomage.game.interfaces.Actionable;
import ru.dude.arcomage.game.interfaces.GameControlable;
import ru.dude.arcomage.game.interfaces.Rendereble;
import ru.dude.arcomage.game.slot.FlySlot;

/**
 * @author elduderino
 */
public class Arcomage implements Rendereble, Actionable, GameControlable {

    AnimPool animPool;

    ResPanel resLeft, resRight;
    HpPanel hpLeft, hpRight;

    Board board;
    Hand hand;

    Player player, user, opponent;
    RoundEnum round;

    Integer stepCounter;

    public Arcomage() {

        user = new User("user");
        opponent = new Computer("computer");

        animPool = new AnimPool();
        board = new Board(Zones.CENTER);
        resLeft = new ResPanel(Zones.WEST, user);
        resRight = new ResPanel(Zones.EAST, opponent);

        hpLeft = new HpPanel(Zones.HP_WEST, user);
        hpRight = new HpPanel(Zones.HP_EAST, opponent);

        hand = new Hand(Zones.SOUTH, board.getActiveSlot(), board.getDeckSlot());

        board.setColor(Color.DARK_GRAY.cpy().sub(0, 0, 0, 0.5f));
        resLeft.setColor(Color.BLUE.cpy().sub(0, 0, 0, 0.5f));
        resRight.setColor(Color.RED.cpy().sub(0, 0, 0, 0.5f));

        hand.setColor(Color.LIGHT_GRAY.sub(0, 0, 0, 0.5f));

        hpLeft.setColor(Color.BLUE.cpy().sub(0, 0, 0, 0.7f));
        hpRight.setColor(Color.RED.cpy().sub(0, 0, 0, 0.7f));

        round = RoundEnum.NOGAME;
    }

    public void startGame(boolean isUser) {
        System.out.println("---------- START GAME ----------");

        stepCounter = 0;
        round = RoundEnum.NOGAME;

        update();

        hand.setPlayer(user,round);

        round = isUser ? RoundEnum.USER_TURN : RoundEnum.OPPONENT_TURN;
        player = isUser ? user : opponent;
        hand.setPlayer(player,round);

        //без анимации
        opponent.takeCards();


        startTurn();

    }

    public void switchTurn() {
        round = round.switchTurn();

        player = round == RoundEnum.USER_TURN ? user : opponent;
        hand.setPlayer(player,round);

        ++stepCounter;

        //hand.takeCard(true);
        startTurn();
    }

    private void startTurn(){
        System.out.println("---------- SWITCH TURN ----------");
        System.out.println("play:" + player.getName() + " hand: " + player.getCardTitles());
        board.clearPrevStep();
        hand.takeCard();
    }

    @Override
    public void render(ShapeRenderer renderer, SpriteBatch spriteBatch) {

        spriteBatch.begin();
        spriteBatch.draw(AppImpl.resources.boardTexture, 0, 0);
        spriteBatch.end();


        board.renderBack(renderer, spriteBatch);

        resLeft.render(renderer, spriteBatch);
        resRight.render(renderer, spriteBatch);


        hpLeft.render(renderer, spriteBatch);
        hpRight.render(renderer, spriteBatch);

        hand.render(renderer, spriteBatch);

        animPool.render(renderer, spriteBatch);

        board.render(renderer, spriteBatch);

    }

    @Override
    public void update() {
        board.update();
        resLeft.update();
        resRight.update();

        hpLeft.update();
        hpRight.update();


        hand.update();

        animPool.update();
    }

    @Override
    public void action(float delta) {
        board.action(delta);
        hand.action(delta);
        animPool.action(delta);
    }

    public void promptToStep(float propX, float propY, int button) {
        if (round == RoundEnum.USER_TURN) {

            if (button == Input.Buttons.LEFT) {
                hand.selectAndPlay(propX, propY, false);
            } else if (button == Input.Buttons.RIGHT) {
                hand.selectAndPlay(propX, propY, true);
            }

        }
    }

    @Override
    public boolean playCard(int r, Card card, boolean drop) {
        return hand.selectAndPlay(r, card, drop);
    }

    @Override
    public Integer getCurrentStepCount() {
        return stepCounter;
    }

    @Override
    public void AnimateFlySlot(FlySlot slot) {
        animPool.putSlot(slot);
    }

}
