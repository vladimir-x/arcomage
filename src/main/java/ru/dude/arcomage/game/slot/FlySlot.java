/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game.slot;

import ru.dude.arcomage.game.interfaces.Actionable;
import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author elduderino
 */
public class FlySlot extends Slot implements Actionable {

    private static float FLY_TIME = 0.30f;//количество секунд, зак оторые производится прохождение полного пути полёта

    Slot destination;
    float remainingTime;
    boolean hasOwer;
    Runnable onFlyOver;

    /**
     * Выполнить действие после всей анимации
     * @param onFlyOver
     */
    public FlySlot(Runnable onFlyOver) {
        this(null, null, onFlyOver);
    }

    /**
     * анимация без какого-то действия
     * @param source
     * @param destination
     */
    public FlySlot(Slot source, Slot destination) {
        this(source, destination, null);
    }

    /**
     * Анимация и действие
     * @param source
     * @param destination
     * @param onFlyOver
     */
    public FlySlot(Slot source, Slot destination, Runnable onFlyOver) {

        if (source != null && destination != null) {
            this.rect = new Rectangle(source.getRect());

            this.destination = destination;
            this.remainingTime = FLY_TIME;

            setCard(source.getCard());
            setTransparent(source.getTransparent());
            setDroped(source.getDroped());
            setPlayedStep(source.getPlayedStep());

        } else {
            this.remainingTime = 0;
        }

        this.hasOwer = false;
        this.onFlyOver = onFlyOver;
    }

    @Override
    public void update() {
    }

    @Override
    public void action(float delta) {

        if (!hasOwer) {
            if (remainingTime < 0.00001) {
                //анимация прошла
                onDest();

            } else if (delta > 0.00001) {
                float part = delta / remainingTime;
                float deltaX = (destination.getRect().x - rect.x) * part;
                float deltaY = (destination.getRect().y - rect.y) * part;

                remainingTime -= delta;

                if (remainingTime < 0.00001) {
                    rect.x = destination.getRect().x;
                    rect.y = destination.getRect().y;
                } else {
                    rect.x += deltaX;
                    rect.y += deltaY;
                }
            }
        }
    }

    public void onDest() {
        if (destination != null) {
            destination.setCard(getCard());
            destination.setDroped(getDroped());
            destination.setPlayedStep(getPlayedStep());

            //destination.onGetCard();
        }
        if (onFlyOver != null) {
            onFlyOver.run();
        }
        setCard(null);
        hasOwer = true;
    }

    public boolean isOwer() {
        return hasOwer;
    }

}
