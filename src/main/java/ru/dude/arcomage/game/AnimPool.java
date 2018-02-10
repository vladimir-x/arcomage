/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.acromage.game;

import ru.dude.acromage.game.interfaces.Actionable;
import ru.dude.acromage.game.interfaces.Rendereble;
import ru.dude.acromage.game.slot.FlySlot;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author elduderino
 */
public class AnimPool implements Actionable, Rendereble {

    Map<FlySlot, Runnable> fSlots;

    public AnimPool() {
        fSlots = new HashMap<FlySlot, Runnable>();
    }

    public void putSlot(FlySlot slot, Runnable onFlyOver) {
        fSlots.put(slot, onFlyOver);
    }

    @Override
    public void action(float delta) {
        ArrayList<FlySlot> toDelete = new ArrayList<FlySlot>();
        for (FlySlot slot : fSlots.keySet()) {
            slot.action(delta);
            if (slot.isOwer()) {
                if (fSlots.get(slot) != null) {
                    fSlots.get(slot).run();
                }
                toDelete.add(slot);
            }
        }
        
        for (FlySlot slot : toDelete) {
            fSlots.remove(slot);
        }
    }

    @Override
    public void update() {
        for (FlySlot slot : fSlots.keySet()) {
            slot.update();

        }
    }

    @Override
    public void render(ShapeRenderer renderer, SpriteBatch spriteBatch) {
        for (FlySlot slot : fSlots.keySet()) {
            slot.render(renderer, spriteBatch);
        }
    }

}
