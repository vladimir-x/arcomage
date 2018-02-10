
package ru.dude.arcomage.game.interfaces;

import ru.dude.arcomage.game.data.Card;
import ru.dude.arcomage.game.slot.FlySlot;

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
