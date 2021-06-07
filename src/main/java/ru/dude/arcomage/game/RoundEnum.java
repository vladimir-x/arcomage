/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.dude.arcomage.game;

/**
 *
 * @author elduderino
 */
public enum RoundEnum {
    
    NOGAME,
    USER_TURN,
    OPPONENT_TURN
    ;

    public RoundEnum switchTurn(){
        if (this == USER_TURN) return OPPONENT_TURN;
        if (this == OPPONENT_TURN ) return USER_TURN;
        return this;
    }
}
