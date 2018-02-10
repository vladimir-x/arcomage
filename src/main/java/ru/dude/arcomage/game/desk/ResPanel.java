/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.acromage.game.desk;

import ru.dude.acromage.game.AppImpl;
import ru.dude.acromage.game.slot.NameBlock;
import ru.dude.acromage.game.slot.ResBlock;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 *
 * @author admin
 */
public class ResPanel extends Deskzone {

    ResBlock brickSlot, gemSlot, beastSlot;

    public ResBlock[] resSlots;

    NameBlock nameBlock;

    public ResPanel(int zone) {
        super(zone);

        brickSlot = new ResBlock(this, 0, AppImpl.resources.brickTexture);
        gemSlot = new ResBlock(this, 1, AppImpl.resources.gemTexture);
        beastSlot = new ResBlock(this, 2, AppImpl.resources.beastTexture);

        resSlots = new ResBlock[]{brickSlot, gemSlot, beastSlot};

        nameBlock = new NameBlock(this);
        nameBlock.setText("text");
    }

    @Override
    public void update() {
        super.update();

        for (ResBlock resSlot : resSlots) {
            resSlot.update();
        }
        nameBlock.update();
    }

    @Override
    public void render(ShapeRenderer renderer, SpriteBatch spriteBatch) {
        super.render(renderer, spriteBatch);

        for (ResBlock resSlot : resSlots) {
            resSlot.render(renderer, spriteBatch);
        }
        nameBlock.render(renderer, spriteBatch);
    }

}
