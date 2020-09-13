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
import ru.dude.arcomage.game.interfaces.Rendereble;

/**
 * Стена (щит)
 * @author admin
 */
public class WallBlock implements Rendereble {

    Rectangle rect;

    HpPanel owner;
    TextureRegion headTextureRegion;
    TextureRegion bodyTextureRegion;

    public WallBlock(HpPanel owner, TextureRegion headTextureRegion, TextureRegion bodyTextureRegion) {
        this.owner = owner;
        this.headTextureRegion = headTextureRegion;
        this.bodyTextureRegion = bodyTextureRegion;
    }

    @Override
    public void update() {
        float centrX = owner.getRectangle().x + owner.getRectangle().width / 2.f;
        float centrY = owner.getRectangle().y + owner.getRectangle().height / 2.f;

        float x = centrX - AppImpl.settings.resTextureWidth / 2.f;
        float y = centrY - AppImpl.settings.resTextureHeight * (1 / 2.f);

        rect = new Rectangle(x, y,
                AppImpl.settings.resTextureWidth,
                AppImpl.settings.resTextureHeight);
    }

    @Override
    public void render(ShapeRenderer renderer, SpriteBatch spriteBatch) {
        spriteBatch.begin();
      //  spriteBatch.draw(textureRegion, rect.x, rect.y);
        spriteBatch.end();
    }

    public Rectangle getRectangle() {
        return rect;
    }

}
