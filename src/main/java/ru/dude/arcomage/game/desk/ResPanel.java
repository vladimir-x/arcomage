/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game.desk;

import ru.dude.arcomage.game.AppImpl;
import ru.dude.arcomage.game.data.Player;
import ru.dude.arcomage.game.data.ResourceType;
import ru.dude.arcomage.game.slot.NameBlock;
import ru.dude.arcomage.game.slot.ResBlock;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Ресурсы
 * @author admin
 */
public class ResPanel extends Deskzone {

    ResBlock brickSlot, gemSlot, beastSlot;

    public ResBlock[] resSlots;

    NameBlock nameBlock;

    Player player;

    public ResPanel(Zones zone, Player player) {
        super(zone);
        this.player = player;

        brickSlot = new ResBlock(this, 0, AppImpl.resources.brickTexture, ResourceType.BRICK);
        gemSlot = new ResBlock(this, 1, AppImpl.resources.gemTexture, ResourceType.GEM);
        beastSlot = new ResBlock(this, 2, AppImpl.resources.beastTexture, ResourceType.BEAST);

        resSlots = new ResBlock[]{brickSlot, gemSlot, beastSlot};

        nameBlock = new NameBlock(this);
        nameBlock.setText(player.getName());
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

    public Player getPlayer() {
        return player;
    }
}
