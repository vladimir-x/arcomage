/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game.slot;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import ru.dude.arcomage.game.AppImpl;

import java.util.function.Supplier;

/**
 * Прирост ресурса
 *
 * @author elduderino
 */
public class ResIncomeBlock extends TextBlock {

    ResBlock owner;
    Supplier<String> valueGetter;

    public ResIncomeBlock(ResBlock owner, Supplier<String> valueGetter) {
        this.owner = owner;
        this.valueGetter = valueGetter;
    }

    @Override
    public void update() {

        /*
        float topSlotY = owner.resSlots[0].getRectangle().y + owner.resSlots[0].getRectangle().height;
        float topResPanelY = owner.getRectangle().y + owner.getRectangle().height;
        
        float centrX = owner.getRectangle().x + owner.getRectangle().width / 2.f;
        float centrY = topSlotY + (topResPanelY - topSlotY) / 2.f;
        */

        this.text = valueGetter.get();

        float centrX = owner.getRectangle().x;
        float centrY = owner.getRectangle().y;

        BitmapFont.TextBounds tb = AppImpl.resources.font.getBounds(text);
        
        rect = new Rectangle(owner.getRectangle().x+5f,
                owner.getRectangle().y+owner.getRectangle().height/4f,
                tb.width, tb.height);
    }

}
