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

    /**
     * Парсинг одного действия
     * @param oneActionLine
     * @return
     */
    public static CardAction parse(String oneActionLine) {
        if (oneActionLine != null && oneActionLine.length() > 0){
            String[] parts = oneActionLine.split(":");
            if (parts.length == 1) {
                //только действие
                return withoutCondition(ActionDetail.parse(oneActionLine));
            } else if (parts.length == 4){

                return withCondition(
                        ActionCondition.parse(parts[1]),
                        ActionDetail.parse(parts[3]),
                        null
                );
            } else if (parts.length == 6) {
                return withCondition(
                        ActionCondition.parse(parts[1]),
                        ActionDetail.parse(parts[3]),
                        ActionDetail.parse(parts[5])
                );
            }
        }
        return null;
    }

}
