package ru.dude.arcomage.game.data;

/**
 * Действия
 *
 * @author elduderino
 * Date: 06.06.2021
 */
public class ActionDetail {

    ActiontTarget actiontTarget;
    PlayResource playResource;
    Integer countAdd;
    Integer countSet;

    public ActiontTarget getActiontTarget() {
        return actiontTarget;
    }

    public PlayResource getPlayResource() {
        return playResource;
    }

    public Integer getCountAdd() {
        return countAdd;
    }

    public Integer getCountSet() {
        return countSet;
    }

    public static ActionDetail ownerAdd(PlayResource addResource, Integer countAdd){
        ActionDetail ad = new ActionDetail();
        ad.actiontTarget = ActiontTarget.OWNER;
        ad.playResource = addResource;
        ad.countAdd = countAdd;
        return ad;
    }
}
