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

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 *
 * @author elduderino
 */
public class AnimPool implements Actionable, Rendereble {

    Map<FlySlot, Runnable> fSlots;
    Queue<Map.Entry<FlySlot, Runnable>> queue;

    Map.Entry<FlySlot, Runnable> selected = null;

    public AnimPool() {
        fSlots = new ConcurrentHashMap<FlySlot, Runnable>();
        queue = new ConcurrentLinkedDeque<Map.Entry<FlySlot, Runnable>>();
    }

    public void putSlot(FlySlot slot, Runnable onFlyOver) {
        System.out.println("anim add >>" + slot);
        /*
        fSlots.put(slot, onFlyOver !=null ? onFlyOver : new Runnable() {
            @Override
            public void run() {

            }
        });
*/

        queue.add(new HashMap.SimpleEntry<>(slot, onFlyOver));
    }

    @Override
    public void action(float delta) {
        /*
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
        */

        if (selected != null){
            selected.getKey().action(delta);
            if (selected.getKey().isOwer()){
                if (selected.getValue()!=null){
                    selected.getValue().run();
                }
                selected = queue.poll();
            }
        }
    }

    @Override
    public void update() {
        if (selected != null){
            selected.getKey().update();
        }

        /*
        for (FlySlot slot : fSlots.keySet()) {
            slot.update();
        }
        */
    }

    @Override
    public void render(ShapeRenderer renderer, SpriteBatch spriteBatch) {


        if (selected == null){
            selected = queue.poll();
        }


        Iterator<Map.Entry<FlySlot, Runnable>> it = queue.iterator();
        while (it.hasNext()){
            it.next().getKey().render(renderer,spriteBatch);
        }

        if (selected !=null){
            selected.getKey().render(renderer, spriteBatch);
        }

        /*
        for (FlySlot slot : fSlots.keySet()) {
            slot.render(renderer, spriteBatch);
        }
        */
    }

}
