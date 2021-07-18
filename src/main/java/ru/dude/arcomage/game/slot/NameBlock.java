/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game.slot;

import ru.dude.arcomage.game.AppImpl;
import ru.dude.arcomage.game.desk.ResPanel;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 *
 * @author elduderino
 */
public class NameBlock extends TextBlock {

    ResPanel owner;

    public NameBlock(ResPanel owner) {
        this.owner = owner;
        this.hasBackground = true;
        this.hAlign = HAlign.LEFT;
        this.vAlign = VAlign.TOP;
    }

    @Override
    public void update() {

        float centrX = owner.getRectangle().x + owner.getRectangle().width / 2.f;
        float topSlotY = owner.resSlots[0].getRectangle().y + owner.resSlots[0].getRectangle().height ;

        updateXY(centrX, topSlotY );
    }

    @Override
    protected String updateTextOnRender() {
        return text;
    }
}
