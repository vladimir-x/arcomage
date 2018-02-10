/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.acromage.game.slot;

import ru.dude.acromage.game.AppImpl;
import ru.dude.acromage.game.data.Card;
import ru.dude.acromage.game.desk.Board;
import com.badlogic.gdx.math.Rectangle;

/**
 * Колода
 *
 * @author elduderino
 */
public class DeckSlot extends Slot {

    Board owner;

    public DeckSlot(Board owner) {
        this.owner = owner;
        setCard(Card.getUndoCard());
    }

    @Override
    public void update() {
        float x = owner.getRectangle().x + owner.CARDS_SPACE_X;
        float y = owner.getRectangle().y +owner.getRectangle().height 
                -owner.CARDS_SPACE_Y - AppImpl.settings.cardHeight;
        rect = new Rectangle(
                x,
                y,
                AppImpl.settings.cardWidth,
                AppImpl.settings.cardHeight
        );
    }

  
    

}
