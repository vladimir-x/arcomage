/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game.slot;

import com.badlogic.gdx.graphics.Color;
import ru.dude.arcomage.game.AppImpl;
import ru.dude.arcomage.game.data.ResourceType;
import ru.dude.arcomage.game.desk.ResPanel;
import ru.dude.arcomage.game.interfaces.Rendereble;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import ru.dude.arcomage.game.render.RenderUtil;

/**
 *
 * @author admin
 */
public class ResBlock implements Rendereble {

    Rectangle rect;

    ResPanel owner;
    int pos;
    TextureRegion textureRegion;

    //тип ресурса
    ResourceType resourceType;

    //Прирост
    ResIncomeBlock incomeBlock;

    //текущее значение
    ResCountBlock currentCountBlock;


    public ResBlock(ResPanel owner, int pos, TextureRegion textureRegion, ResourceType resourceType) {
        this.owner = owner;
        this.pos = pos;
        this.textureRegion = textureRegion;

        this.resourceType = resourceType;

        this.incomeBlock = new ResIncomeBlock(this, () -> owner.getPlayer().getIncome(resourceType).toString());
        this.currentCountBlock = new ResCountBlock(this, () -> owner.getPlayer().getCurrentCount(resourceType).toString());
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

        incomeBlock.update();
        currentCountBlock.update();
    }

    @Override
    public void render(ShapeRenderer renderer, SpriteBatch spriteBatch) {


        RenderUtil.renderBorderRect(renderer, Color.ORANGE, rect);

        spriteBatch.begin();
        spriteBatch.draw(textureRegion, rect.x, rect.y);
        spriteBatch.end();

        incomeBlock.render(renderer,spriteBatch);
        currentCountBlock.render(renderer,spriteBatch);
    }

    public Rectangle getRectangle() {
        return rect;
    }

}
