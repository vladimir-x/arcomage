/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game.slot;

import ru.dude.arcomage.game.AppImpl;
import ru.dude.arcomage.game.desk.Board;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import ru.dude.arcomage.game.render.RenderUtil;

/**
 * Сыгранные карты
 *
 * @author elduderino
 */
public class PlayedSlot extends Slot {

    private static final int SPACER = 5;

    public int posX, posY;
    Board board;

    public PlayedSlot(Board board, int posX, int posY) {
        this.board = board;
        this.posX = posX;
        this.posY = posY;
        setCard(null);
        setTransparent(true);
    }

    @Override
    public void update() {

        float x = board.getRectangle().x + board.CARDS_SPACE_X + posX * (AppImpl.settings.cardWidth + SPACER);
        float y = board.getRectangle().y + board.getRectangle().height
                - board.CARDS_SPACE_Y - AppImpl.settings.cardHeight
                - posY * (AppImpl.settings.cardHeight + SPACER);
        rect = new Rectangle(x, y,
                AppImpl.settings.cardWidth,
                AppImpl.settings.cardHeight
        );
    }

    @Override
    public void render(ShapeRenderer renderer, SpriteBatch spriteBatch) {
        super.render(renderer, spriteBatch);

        RenderUtil.renderBorderRect(renderer, Color.GREEN, rect);
    }

    @Override
    void onGetCard() {
        if (getCard() != null) {
            System.out.println("PlaySlot onGet: playAgain = " + getCard().isPlayAgain() + ", dropped = " + getDroped() + ", dropAnotherAndAgain = " + getCard().isDropAnotherAndPlayAgain());
            if (AppImpl.control.isRoundDropAndAgain()) {
                AppImpl.control.playAgain();
            } else if (getDroped()) {
                AppImpl.control.switchTurn();
            } else {
                if (getCard().isPlayAgain()) {
                    AppImpl.control.playAgain();
                } else if (getCard().isDropAnotherAndPlayAgain()) {
                    AppImpl.control.playDropAndAgain();
                } else {
                    AppImpl.control.switchTurn();
                }
            }
        }
        board.makeEmptySlot();
        board.update();
    }

}
