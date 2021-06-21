
package ru.dude.arcomage.game.interfaces;

import ru.dude.arcomage.game.data.Card;
import ru.dude.arcomage.game.slot.FlySlot;

/**
 *
 * @author elduderino
 */
public interface GameControlable {
    
    // передать ход
    void switchTurn();

    // походить ещё раз
    void playAgain();

    //
    boolean playCard(int r, Card card, boolean drop);
    
    // 
    void AnimateFlySlot(FlySlot slot);
    
    // узнать текущий № хода
    Integer getCurrentStepCount();

    void promptToStep(float propX, float propY, int button);

    /**
     * Выполнить карту.
     * Проверки на стоимость выполнения карты уже не проводятся.
     * @param card
     * @param droped
     */
    void executeCard(Card card, Boolean droped);

    /**
     * Проверить на победу
     */
    void checkWin();
}
