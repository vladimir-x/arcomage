package ru.dude.arcomage.game.data;

/**
 * Действия
 *
 * @author elduderino
 * Date: 06.06.2021
 */
public class ActionDetail {

    public enum Command {
        ADD,
        SET_INT,
        SET_AS_OPPONENT,
        SET_ALL_AS_MAX,
        SWAP,
        DROP_ONE,
    }


    ActiontTarget actiontTarget;
    PlayResource playResource;
    Command command;
    Integer count;

    public ActiontTarget getActiontTarget() {
        return actiontTarget;
    }

    public PlayResource getPlayResource() {
        return playResource;
    }

    public Command getCommand() {
        return command;
    }

    public Integer getCount() {
        return count;
    }


    public static ActionDetail parse(String line) {

        String[] parts = line.split("\\.");

        ActionDetail ad = new ActionDetail();
        ad.actiontTarget = ActiontTarget.valueOf(parts[0]);
        ad.playResource = PlayResource.valueOf(parts[1]);

        String rightPart = parts[2];
        if (rightPart.startsWith("+") || rightPart.startsWith("-")) {
            ad.command = Command.ADD;
            ad.count = Integer.parseInt(rightPart);
        } else {
            if (ad.playResource == PlayResource.DAMAGE){
                ad.count = Integer.parseInt(rightPart);
            } else {
                ad.command = Command.valueOf(rightPart);
            }
        }

        return ad;
    }

    @Override
    public String toString() {
        return "ActionDetail{" +
                "actiontTarget=" + actiontTarget +
                ", playResource=" + playResource +
                ", command=" + command +
                ", count=" + count +
                '}';
    }
}
