/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game.slot;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import ru.dude.arcomage.game.AppImpl;
import ru.dude.arcomage.game.data.PlayResource;
import ru.dude.arcomage.game.desk.HpPanel;
import ru.dude.arcomage.game.desk.ResPanel;
import ru.dude.arcomage.game.desk.Zones;
import ru.dude.arcomage.game.interfaces.Rendereble;

/**
 * Башня с HP
 * @author elduderino
 */
public class TowerBlock implements Rendereble {

    Rectangle headTextureRect;
    Rectangle bodyTextureRect;

    HpPanel owner;
    TextureRegion headTextureRegion;
    TextureRegion bodyTextureRegion;

    TextBlock towerHpBlock;

    public TowerBlock(HpPanel owner, TextureRegion headTextureRegion, TextureRegion bodyTextureRegion) {
        this.owner = owner;
        this.headTextureRegion = headTextureRegion;
        this.bodyTextureRegion = bodyTextureRegion;

        towerHpBlock = new TextBlock() {
            @Override
            public void update() {
                updateByXYText(bodyTextureRect.x, bodyTextureRect.y);
            }

            @Override
            protected String updateTextOnRender() {
                return owner.getPlayer().getResource(PlayResource.TOWER_HP).toString();
            }
        };
    }

    @Override
    public void update() {
        updateHead();
        updateBody();
        towerHpBlock.update();
    }

    private void updateHead(){
        // чуть правее или чуть левее
        int k = owner.getZone() == Zones.HP_WEST ? 1 : 2;

        float centrX = owner.getRectangle().x + owner.getRectangle().width / 3.f * k;
        float bottomY = owner.getRectangle().y;

        // тут нужно вычислить этажность
        float bodyHeight = owner.getPlayer().getResource(PlayResource.TOWER_HP);

        float x = centrX - AppImpl.settings.towerHeadTextureWidth / 2.f;
        float y = bottomY + bodyHeight;

        headTextureRect = new Rectangle(x, y,
                AppImpl.settings.towerHeadTextureWidth,
                AppImpl.settings.towerHeadTextureHeight);
    }


    private void updateBody(){
        // чуть правее или чуть левее
        int k = owner.getZone() == Zones.HP_WEST ? 1 : 2;

        float centrX = owner.getRectangle().x + owner.getRectangle().width / 3.f * k;
        float bottomY = owner.getRectangle().y;

        // тут нужно вычислить этажность
        float bodyHeight = owner.getPlayer().getResource(PlayResource.TOWER_HP);


        float x = centrX - AppImpl.settings.towerBodyTextureWidth / 2.f;
        float y = bottomY;

        bodyTextureRect = new Rectangle(x, y,
                AppImpl.settings.towerBodyTextureWidth,
                bodyHeight);
    }

    @Override
    public void render(ShapeRenderer renderer, SpriteBatch spriteBatch) {
        update();

        spriteBatch.begin();
        spriteBatch.draw(headTextureRegion, headTextureRect.x, headTextureRect.y);
        spriteBatch.draw(bodyTextureRegion, bodyTextureRect.x, bodyTextureRect.y, bodyTextureRect.width, bodyTextureRect.height);
        spriteBatch.end();

        towerHpBlock.render(renderer,spriteBatch);
    }


}
