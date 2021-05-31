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
import ru.dude.arcomage.game.desk.HpPanel;
import ru.dude.arcomage.game.desk.Zones;
import ru.dude.arcomage.game.interfaces.Rendereble;

/**
 * Стена (щит)
 * @author admin
 */
public class WallBlock implements Rendereble {

    Rectangle headTextureRect;
    Rectangle bodyTextureRect;

    HpPanel owner;
    TextureRegion headTextureRegion;
    TextureRegion bodyTextureRegion;

    TextBlock wallHpText;

    public WallBlock(HpPanel owner, TextureRegion headTextureRegion, TextureRegion bodyTextureRegion) {
        this.owner = owner;
        this.headTextureRegion = headTextureRegion;
        this.bodyTextureRegion = bodyTextureRegion;

        wallHpText = new TextBlock() {
            @Override
            public void update() {
                updateByXYText(bodyTextureRect.x, bodyTextureRect.y, owner.getPlayer().wallHP.toString());
            }
        };
    }

    @Override
    public void update() {
        updateBody();
        wallHpText.update();
    }

    private void updateBody(){
        // чуть правее или чуть левее
        int k = owner.getZone() == Zones.HP_WEST ? 5 : 1;

        float centrX = owner.getRectangle().x + owner.getRectangle().width / 6.f * k;
        float bottomY = owner.getRectangle().y;

        // тут нужно вычислить этажность
        float bodyHeight = owner.getPlayer().wallHP;


        float x = centrX - AppImpl.settings.wallBodyTextureWidth / 2.f;
        float y = bottomY;

        bodyTextureRect = new Rectangle(x, y,
                AppImpl.settings.wallBodyTextureWidth,
                bodyHeight);
    }

    @Override
    public void render(ShapeRenderer renderer, SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.draw(bodyTextureRegion, bodyTextureRect.x, bodyTextureRect.y, bodyTextureRect.width, bodyTextureRect.height);
        spriteBatch.end();

        wallHpText.render(renderer,spriteBatch);
    }

}
