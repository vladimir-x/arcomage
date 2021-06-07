package ru.dude.arcomage.game.data;

/**
 * @author elduderino
 * Date: 05.06.2021
 */
public class ActionCondition {

    public enum CompareType {
        MORE, LESS, EQ
    }

    // Сравниваемый ресурс
    PlayResource compareResource;
    CompareType compareType;

    ActiontTarget leftOperandTarget;
    ActiontTarget rightOperandTarget;

    Integer leftConstantValue;
    Integer rightConstantValue;

    /**
     * Проверить условие на выполнение
     * @param owner
     * @param enemy
     * @return
     */
    public boolean check(Player owner, Player enemy) {

        Integer leftValue = selectOperandValue(owner, enemy, leftOperandTarget, leftConstantValue);
        Integer rightValue = selectOperandValue(owner, enemy, rightOperandTarget, rightConstantValue);

        int cmp = Integer.compare(leftValue, rightValue);
        switch (compareType){
            case MORE:
                return cmp>0;
            case LESS:
                return cmp<0;
            case EQ:
                return cmp == 0;
        }
        throw new IllegalArgumentException(compareType + " not supported");

    }

    private Integer selectOperandValue(Player owner, Player enemy, ActiontTarget operandTarget, Integer constantValue){
        switch (operandTarget) {
            case OWNER:
                return owner.getResource(compareResource);
            case ENEMY:
                return enemy.getResource(compareResource);
            case CONSTANTA:
                return constantValue;
        }
        throw new IllegalArgumentException(operandTarget + " not supported");
    }


    public static ActionCondition ownerEq(PlayResource compareResource, Integer constantValue) {
        ActionCondition ac = new ActionCondition();
        ac.compareResource = compareResource;
        ac.compareType = CompareType.EQ;
        ac.leftOperandTarget = ActiontTarget.OWNER;
        ac.rightOperandTarget = ActiontTarget.CONSTANTA;
        ac.rightConstantValue = constantValue;
        return ac;
    }
}
