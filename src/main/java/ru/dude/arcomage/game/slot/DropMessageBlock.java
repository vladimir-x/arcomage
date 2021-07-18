/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game.slot;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import ru.dude.arcomage.game.AppImpl;
import ru.dude.arcomage.game.desk.Board;

/**
 * @author elduderino
 */
public class DropMessageBlock extends TextBlock {

    Board owner;

    public DropMessageBlock(Board owner) {
        this.owner = owner;
        this.hAlign = HAlign.LEFT;
        this.vAlign = VAlign.BOTTOM;
    }

    @Override
    public void render(ShapeRenderer renderer, SpriteBatch spriteBatch) {
        super.render(renderer, spriteBatch);
    }

    @Override
    public void update() {
        float centrX = owner.getRectangle().x + owner.getRectangle().width / 2.f;
        float centrY = owner.getRectangle().y ;

        updateXY(centrX, centrY);
    }

    @Override
    protected String updateTextOnRender() {
        return AppImpl.resources.dropAndAgainMessage;
    }
}
