/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game;

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
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 *
 * @author elduderino
 */
public class Arcomage implements Rendereble, Actionable, GameControlable {

    AnimPool animPool;

    ResPanel resLeft, resRight;

    Board board;
    Hand hand, userHand, opponentHand;
    ResPanel resPanels[];

    Player player, user, opponent;
    RoundEnum round;
    CardActionEnum cardAction;

    Boolean isTurning;

    Integer stepCounter;

    public Arcomage() {

        animPool = new AnimPool();
        board = new Board(Deskzone.CENTER);
        resLeft = new ResPanel(Deskzone.WEST);
        resRight = new ResPanel(Deskzone.EAST);

        user = new User();
        opponent = new Computer();

        userHand = new Hand(Deskzone.SOUTH, board.getActiveSlot(), board.getDeckSlot(), user);
        opponentHand = new Hand(Deskzone.SOUTH, board.getActiveSlot(), board.getDeckSlot(), opponent);
        userHand.debugstr = "user";
        opponentHand.debugstr = "oppon";

        board.setColor(Color.DARK_GRAY.cpy().sub(0, 0, 0, 0.5f));
        resLeft.setColor(Color.BLUE.cpy().sub(0, 0, 0, 0.5f));
        resRight.setColor(Color.RED.cpy().sub(0, 0, 0, 0.5f));

        userHand.setColor(Color.LIGHT_GRAY.sub(0, 0, 0, 0.5f));
        opponentHand.setColor(Color.LIGHT_GRAY.sub(0, 0, 0, 0.5f));

        isTurning = !true;//поменяется в  startGame()->swicthTurn()
    }

    public void startGame() {

        //user.takeCard(AppImpl.settings.cardCount);
        //opponent.takeCard(AppImpl.settings.cardCount);
        stepCounter = 0;

        userHand.takeCard(false);
        opponentHand.takeCard(false);
        update();
        switchTurn();
        hand.setWaitingPlayer();

    }

    public void switchTurn() {
        isTurning = !isTurning;
        if (isTurning) {
            hand = userHand;
            player = user;
        } else {
            hand = opponentHand;
            player = opponent;
        }
        ++stepCounter;
        //board.clearPrevStep();
        hand.takeCard(true);
    }

    @Override
    public void render(ShapeRenderer renderer, SpriteBatch spriteBatch) {

        spriteBatch.begin();
        spriteBatch.draw(AppImpl.resources.boardTexture, 0, 0);
        spriteBatch.end();

        board.render(renderer, spriteBatch);
        resLeft.render(renderer, spriteBatch);
        resRight.render(renderer, spriteBatch);
        hand.render(renderer, spriteBatch);

        animPool.render(renderer, spriteBatch);

    }

    @Override
    public void update() {
        board.update();
        resLeft.update();
        resRight.update();

        userHand.update();
        opponentHand.update();

        animPool.update();
    }

    @Override
    public void action(float delta) {
        board.action(delta);
        hand.action(delta);
        animPool.action(delta);
    }

    public void promptToStep(float propX, float propY, int button) {
        if (isTurning) {

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
    public void AnimateFlySlot(FlySlot slot, Runnable onFlyOver) {
        animPool.putSlot(slot, onFlyOver);
    }

}
