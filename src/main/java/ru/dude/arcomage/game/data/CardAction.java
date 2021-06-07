package ru.dude.arcomage.game.data;

/**
 * Действия, которые делает карта
 *
 * @author elduderino
 * Date: 05.06.2021
 */
public class CardAction {

    ActionCondition condition;

    // действие без условий, или при выполнении условия
    ActionDetail normalAction;

    // действие при НЕ выполнении условия
    ActionDetail elseAction;

    public ActionCondition getCondition() {
        return condition;
    }

    public ActionDetail getNormalAction() {
        return normalAction;
    }

    public ActionDetail getElseAction() {
        return elseAction;
    }

    private static CardAction withoutCondition(ActionDetail normalAction) {
        CardAction ca = new CardAction();
        ca.normalAction = normalAction;
        return ca;
    }

    private static CardAction withCondition(ActionCondition condition, ActionDetail normalAction, ActionDetail elseAction) {
        CardAction ca = new CardAction();
        ca.condition = condition;
        ca.normalAction = normalAction;
        ca.elseAction = elseAction;
        return ca;
    }

    public static CardAction ownerAddResIf(PlayResource playResource, Integer compareValue, Integer ifEqual, Integer ifNotEqual) {
        return withCondition(
                ActionCondition.ownerEq(playResource, compareValue),
                ActionDetail.ownerAdd(playResource, ifEqual),
                ActionDetail.ownerAdd(playResource, ifNotEqual)
        );
    }
}
