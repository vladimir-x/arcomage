/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game;

import ru.dude.arcomage.game.interfaces.Actionable;
import ru.dude.arcomage.game.interfaces.Rendereble;
import ru.dude.arcomage.game.slot.FlySlot;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author elduderino
 */
public class AnimPool implements Actionable, Rendereble {

    Map<FlySlot, Runnable> fSlots;

    public AnimPool() {
        fSlots = new ConcurrentHashMap<FlySlot, Runnable>();
    }

    public void putSlot(FlySlot slot, Runnable onFlyOver) {
        System.out.println("anim add >>" + slot);
        fSlots.put(slot, onFlyOver !=null ? onFlyOver : new Runnable() {
            @Override
            public void run() {

            }
        });
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
