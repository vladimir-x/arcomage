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
 * @author elduderino
 */
public class AnimPool implements Actionable, Rendereble {

    Queue<FlySlot> queue = new ConcurrentLinkedDeque<>();

    FlySlot selected = null;

    /**
     * выполняется когда вся анимация закончилась
     */
    Runnable onAnimateComlete;

    public AnimPool() {
    }

    public void putSlot(FlySlot slot) {
        System.out.println("anim add >>" + slot);


        queue.add(slot);
    }

    @Override
    public void action(float delta) {

        if (selected == null){
            selected = queue.poll();
        }

        if (selected != null) {
            selected.action(delta);

            if (selected.isOwer()) {
                selected = queue.poll();
            }
        }

        if (selected == null && onAnimateComlete !=null){
            onAnimateComlete.run();
            onAnimateComlete = null;

        }
    }

    @Override
    public void update() {
        if (selected != null) {
            selected.update();
        }
    }

    @Override
    public void render(ShapeRenderer renderer, SpriteBatch spriteBatch) {

        for (FlySlot flySlot : queue) {
            flySlot.render(renderer, spriteBatch);
        }

        if (selected != null) {
            selected.render(renderer, spriteBatch);
        }

    }

    public boolean isAnimating(){
        return selected != null;
    }

    public void setOnAnimateComlete(Runnable onAnimateComlete) {
        this.onAnimateComlete = onAnimateComlete;
    }
}
