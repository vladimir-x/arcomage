/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game.desk;

import ru.dude.arcomage.game.AppImpl;
import ru.dude.arcomage.game.data.Player;
import ru.dude.arcomage.game.slot.NameBlock;
import ru.dude.arcomage.game.slot.ResBlock;
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

    Player player;

    public ResPanel(int zone) {
        super(zone);

        brickSlot = new ResBlock(this, 0, AppImpl.resources.brickTexture);
        gemSlot = new ResBlock(this, 1, AppImpl.resources.gemTexture);
        beastSlot = new ResBlock(this, 2, AppImpl.resources.beastTexture);

        resSlots = new ResBlock[]{brickSlot, gemSlot, beastSlot};

        nameBlock = new NameBlock(this);
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

    public void setPlayer(Player player) {
        this.player = player;
        nameBlock.setText(player.getName());
    }
}
