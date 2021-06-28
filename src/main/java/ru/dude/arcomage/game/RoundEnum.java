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
    USER_DROPANDAGAIN,
    OPPONENT_TURN,
    OPPONENT_DROPANDAGAIN,
    ;

    public RoundEnum switchTurn(){
        switch (this) {
            case USER_TURN:
                return OPPONENT_TURN;
            case OPPONENT_TURN:
                return USER_TURN;
            case USER_DROPANDAGAIN:
                return USER_TURN;
            case OPPONENT_DROPANDAGAIN:
                return OPPONENT_DROPANDAGAIN;
            case NOGAME:
            default:
                return this;
        }
    }

    public boolean isUserTurn() {
        return this == USER_TURN || this == USER_DROPANDAGAIN;
    }

    public boolean isOpponentTurn() {
        return this == OPPONENT_TURN || this == OPPONENT_DROPANDAGAIN;
    }

    public boolean isDropAndAgain() {
        return this == USER_DROPANDAGAIN || this == OPPONENT_DROPANDAGAIN;
    }
}
