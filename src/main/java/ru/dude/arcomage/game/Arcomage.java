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
import ru.dude.arcomage.game.desk.Board;
import ru.dude.arcomage.game.desk.Deskzone;
import ru.dude.arcomage.game.desk.Hand;
import ru.dude.arcomage.game.desk.ResPanel;
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

    Board board;
    Hand hand;

    Player player, user, opponent;
    RoundEnum round;

    Integer stepCounter;

    public Arcomage() {

        animPool = new AnimPool();
        board = new Board(Deskzone.CENTER);
        resLeft = new ResPanel(Deskzone.WEST);
        resRight = new ResPanel(Deskzone.EAST);

        user = new User("user");
        opponent = new Computer("computer");

        resLeft.setPlayer(user);
        resRight.setPlayer(opponent);

        hand = new Hand(Deskzone.SOUTH, board.getActiveSlot(), board.getDeckSlot());

        board.setColor(Color.DARK_GRAY.cpy().sub(0, 0, 0, 0.5f));
        resLeft.setColor(Color.BLUE.cpy().sub(0, 0, 0, 0.5f));
        resRight.setColor(Color.RED.cpy().sub(0, 0, 0, 0.5f));

        hand.setColor(Color.LIGHT_GRAY.sub(0, 0, 0, 0.5f));

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
        hand.render(renderer, spriteBatch);

        animPool.render(renderer, spriteBatch);

        board.render(renderer, spriteBatch);

    }

    @Override
    public void update() {
        board.update();
        resLeft.update();
        resRight.update();

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
                hand.promptToSelect(propX, propY, false);
            } else if (button == Input.Buttons.RIGHT) {
                hand.promptToSelect(propX, propY, true);
            }

        }
    }

    @Override
    public boolean playCard(int r, Card card, boolean drop) {
        return hand.promptToSelect(r, card, drop);
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
