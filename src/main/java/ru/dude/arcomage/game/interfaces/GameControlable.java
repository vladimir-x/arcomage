
package ru.dude.acromage.game.interfaces;

import ru.dude.acromage.game.data.Card;
import ru.dude.acromage.game.slot.FlySlot;

/**
 *
 * @author admin
 */
public interface GameControlable {
    
    // передать ход
    void switchTurn();

    //
    boolean playCard(int r, Card card, boolean drop);
    
    // 
    void AnimateFlySlot(FlySlot slot, Runnable onFlyOver);
    
    // узнать текущий № хода
    Integer getCurrentStepCount();
}
