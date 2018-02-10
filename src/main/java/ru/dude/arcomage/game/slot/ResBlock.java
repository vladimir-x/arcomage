/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.acromage.game.slot;

import ru.dude.acromage.game.AppImpl;
import ru.dude.acromage.game.desk.ResPanel;
import ru.dude.acromage.game.interfaces.Rendereble;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author admin
 */
public class ResBlock implements Rendereble {

    Rectangle rect;

    ResPanel owner;
    int pos;
    TextureRegion textureRegion;

    public ResBlock(ResPanel owner, int pos, TextureRegion textureRegion) {
        this.owner = owner;
        this.pos = pos;
        this.textureRegion = textureRegion;
    }

    @Override
    public void update() {
        float centrX = owner.getRectangle().x + owner.getRectangle().width / 2.f;
        float centrY = owner.getRectangle().y + owner.getRectangle().height / 2.f;

        float x = centrX - AppImpl.settings.resTextureWidth / 2.f;
        float y = centrY - AppImpl.settings.resTextureHeight * (1 / 2.f + pos - 1);

        rect = new Rectangle(x, y,
                AppImpl.settings.resTextureWidth,
                AppImpl.settings.resTextureHeight);
    }

    @Override
    public void render(ShapeRenderer renderer, SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.draw(textureRegion, rect.x, rect.y);
        spriteBatch.end();
    }

    public Rectangle getRectangle() {
        return rect;
    }

}
