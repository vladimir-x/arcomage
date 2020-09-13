/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game.desk;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import ru.dude.arcomage.game.AppImpl;
import ru.dude.arcomage.game.data.Player;
import ru.dude.arcomage.game.slot.TowerBlock;
import ru.dude.arcomage.game.slot.WallBlock;

/**
 * Башня и стена
 * @author admin
 */
public class HpPanel extends Deskzone {

    TowerBlock towerBlock;
    WallBlock wallBlock;

    Player player;

    public HpPanel(Zones zone, Player player) {
        super(zone);
        this.player = player;

        if (zone == Zones.HP_EAST) {
            towerBlock = new TowerBlock(this, AppImpl.resources.towerHeadBlueTexture, AppImpl.resources.towerBodyTexture);
        } else if (zone == Zones.HP_WEST) {
            towerBlock = new TowerBlock(this, AppImpl.resources.towerHeadRedTexture, AppImpl.resources.towerBodyTexture);
        } else {
            throw new IllegalArgumentException("no supported zone=" + zone);
        }

        wallBlock = new WallBlock(this, AppImpl.resources.towerHeadRedTexture, AppImpl.resources.towerBodyTexture);
    }

    @Override
    public void update() {
        super.update();

        towerBlock.update();
        wallBlock.update();
    }

    @Override
    public void render(ShapeRenderer renderer, SpriteBatch spriteBatch) {
        super.render(renderer, spriteBatch);

        towerBlock.render(renderer, spriteBatch);
        wallBlock.render(renderer, spriteBatch);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
