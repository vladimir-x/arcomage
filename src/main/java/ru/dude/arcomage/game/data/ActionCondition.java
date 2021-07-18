package ru.dude.arcomage.game.data;

/**
 * @author elduderino
 * Date: 05.06.2021
 */
public class ActionCondition {

    public enum CompareType {
        MORE, LESS, EQ
    }

    //сторона сравнения
    ActiontTarget leftOperandTarget;
    ActiontTarget rightOperandTarget;

    // Сравниваемый ресурс
    PlayResource leftCompareResource;
    PlayResource rightCompareResource;
    CompareType compareType;

    Integer leftConstantValue;
    Integer rightConstantValue;

    /**
     * Проверить условие на выполнение
     * @param owner
     * @param enemy
     * @return
     */
    public boolean check(Player owner, Player enemy) {

        Integer leftValue = selectOperandValue(owner, enemy, leftOperandTarget, leftCompareResource, leftConstantValue);
        Integer rightValue = selectOperandValue(owner, enemy, rightOperandTarget, rightCompareResource, rightConstantValue);

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

    private Integer selectOperandValue(Player owner, Player enemy, ActiontTarget operandTarget, PlayResource compareResource, Integer constantValue){
        switch (operandTarget) {
            case OWNER:
                return owner.getResource(compareResource);
            case ENEMY:
                return enemy.getResource(compareResource);
            case CONST:
                return constantValue;
        }
        throw new IllegalArgumentException(operandTarget + " not supported");
    }

    public static ActionCondition parse(String line) {
        String[] parts = line.split("\\.");

        ActionCondition ac = new ActionCondition();
        ac.leftOperandTarget = ActiontTarget.valueOf(parts[0]);
        ac.leftCompareResource = PlayResource.valueOf(parts[1]);
        ac.rightCompareResource = ac.leftCompareResource;
        ac.compareType = CompareType.valueOf(parts[2]);
        String rightPart = parts[3];
        if (rightPart.equals(ActiontTarget.ENEMY.name()) || rightPart.equals(ActiontTarget.OWNER.name())){
            ac.rightOperandTarget = ActiontTarget.valueOf(rightPart);
        } else {
            ac.rightOperandTarget = ActiontTarget.CONST;
            ac.rightConstantValue = Integer.valueOf(rightPart);
        }
        if (rightPart.length() > 4) {
            ac.rightCompareResource = PlayResource.valueOf(parts[4]);
        }
        return ac;
    }
}
