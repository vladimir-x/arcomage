/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dude.arcomage.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import ru.dude.arcomage.game.data.*;
import ru.dude.arcomage.game.desk.*;
import ru.dude.arcomage.game.interfaces.Actionable;
import ru.dude.arcomage.game.interfaces.GameControlable;
import ru.dude.arcomage.game.interfaces.Rendereble;
import ru.dude.arcomage.game.slot.FlySlot;

import java.util.Optional;

/**
 * @author elduderino
 */
public abstract class Arcomage implements Rendereble, Actionable, GameControlable {

    AnimPool animPool;

    ResPanel resLeft, resRight;
    HpPanel hpLeft, hpRight;

    Board board;
    Hand hand;

    Player user, opponent;
    RoundEnum round;

    Integer stepCounter;

    public Arcomage() {

        user = new User("user", this::alertEndGameUser);
        opponent = new Computer("computer");

        animPool = new AnimPool();
        board = new Board(Zones.CENTER);
        resLeft = new ResPanel(Zones.WEST, user);
        resRight = new ResPanel(Zones.EAST, opponent);

        hpLeft = new HpPanel(Zones.HP_WEST, user);
        hpRight = new HpPanel(Zones.HP_EAST, opponent);

        hand = new Hand(Zones.SOUTH, board.getActiveSlot(), board.getDeckSlot());

        board.setColor(Color.DARK_GRAY.cpy().sub(0, 0, 0, 1f));
        resLeft.setColor(Color.BLUE.cpy().sub(0, 0, 0, 1f));
        resRight.setColor(Color.RED.cpy().sub(0, 0, 0, 1f));

        hand.setColor(Color.LIGHT_GRAY.sub(0, 0, 0, 1f));

        hpLeft.setColor(Color.BLUE.cpy().sub(0, 0, 0, 1f));
        hpRight.setColor(Color.RED.cpy().sub(0, 0, 0, 1f));

        round = RoundEnum.NOGAME;
    }

    public void startGame(boolean isUser) {
        System.out.println("---------- START GAME ----------");

        stepCounter = 0;
        round = RoundEnum.NOGAME;

        update();

        hand.setPlayer(user, round);

        round = isUser ? RoundEnum.USER_TURN : RoundEnum.OPPONENT_TURN;

        //без анимации
        user.takeCards();
        opponent.takeCards();

        hand.setPlayer(getCurrentPlayer(), round);

        startTurn();

    }

    @Override
    public void switchTurn() {
        System.out.println("---------- SWITCH TURN ----------");
        round = round.switchTurn();

        hand.setPlayer(getCurrentPlayer(), round);

        ++stepCounter;
        executeIncome();
        startTurn();
    }

    @Override
    public void playAgain() {
        System.out.println("---------- AGAIN TURN ----------");
        if (round == RoundEnum.USER_DROPANDAGAIN){
            round = RoundEnum.USER_TURN;
        }
        if (round == RoundEnum.OPPONENT_DROPANDAGAIN){
            round = RoundEnum.OPPONENT_TURN;
        }
        startTurn();
    }

    @Override
    public void playDropAndAgain(){
        System.out.println("---------- DROP AND AGAIN TURN ----------");
        round = round.isUserTurn() ? RoundEnum.USER_DROPANDAGAIN : RoundEnum.OPPONENT_DROPANDAGAIN;

        startTurn();
    }

    private void startTurn() {
        if (round != RoundEnum.NOGAME) {
            checkWin();

            System.out.println("play:" + getCurrentPlayer().getName() + " hand: " + getCurrentPlayer().getCardTitles());
            board.clearPrevStep();
            animPool.setOnAnimateComlete(() -> hand.takeCard());
        }
    }

    @Override
    public void render(ShapeRenderer renderer, SpriteBatch spriteBatch) {

        spriteBatch.begin();
        spriteBatch.draw(AppImpl.resources.boardTexture, 0, 0);
        spriteBatch.end();

        board.renderBack(renderer, spriteBatch);

        hand.render(renderer, spriteBatch);

        board.render(renderer, spriteBatch);

        resLeft.render(renderer, spriteBatch);
        resRight.render(renderer, spriteBatch);

        hpLeft.render(renderer, spriteBatch);
        hpRight.render(renderer, spriteBatch);

        animPool.render(renderer, spriteBatch);

    }

    @Override
    public void update() {
        board.update();
        resLeft.update();
        resRight.update();

        hpLeft.update();
        hpRight.update();


        hand.update();

        animPool.update();
    }

    @Override
    public void action(float delta) {
        board.action(delta);
        hand.action(delta);
        animPool.action(delta);
    }

    @Override
    public void promptToStep(float propX, float propY, int button) {
        if (round == RoundEnum.USER_TURN) {

            if (button == Input.Buttons.LEFT) {
                hand.selectAndPlay(propX, propY, false);
            } else if (button == Input.Buttons.RIGHT) {
                hand.selectAndPlay(propX, propY, true);
            }

        } else if (round == RoundEnum.USER_DROPANDAGAIN){
            if (button == Input.Buttons.LEFT || button == Input.Buttons.RIGHT) {
                hand.selectAndPlay(propX, propY, true);
            }
        }
    }

    @Override
    public boolean playCard(int r, Card card, boolean drop) {
        return hand.selectAndPlay(r, card, drop || isRoundDropAndAgain());
    }

    @Override
    public Integer getCurrentStepCount() {
        return stepCounter;
    }

    @Override
    public boolean isRoundDropAndAgain() {
        return round.isDropAndAgain();
    }

    @Override
    public void AnimateFlySlot(FlySlot slot) {
        animPool.putSlot(slot);
    }


    @Override
    public void executeCard(Card card, Boolean droped) {

        Player owner = getCurrentPlayer();
        Player enemy = getOpponent();
        System.out.println("executeCard=" + card + "; drop=" + droped + "; owner=" + owner.getName() + " ; enemy=" + enemy.getName());

        if (droped) {
            // just drop
        } else {

            owner.addResource(card.getCostType(), -card.getCostCount());

            for (CardAction cardAction : card.getCardActions()) {
                Boolean conditionCheck = Optional.ofNullable(cardAction.getCondition()).map(c -> c.check(owner, enemy)).orElse(true);

                ActionDetail actionDetail = conditionCheck ? cardAction.getNormalAction() : cardAction.getElseAction();

                if (actionDetail != null) {

                    switch (actionDetail.getActiontTarget()) {
                        case OWNER:
                            executeOneAction(owner, actionDetail);
                            break;
                        case ENEMY:
                            executeOneAction(enemy, actionDetail);
                            break;
                        case LESS_WALL_HP:
                            Integer ownerWal = owner.getResource(PlayResource.WALL_HP);
                            Integer enemyWal = enemy.getResource(PlayResource.WALL_HP);

                            if (ownerWal < enemyWal) {
                                executeOneAction(owner, actionDetail);
                            } else if (enemyWal < ownerWal) {
                                executeOneAction(enemy, actionDetail);
                            }

                            break;
                        case ALL:
                            executeOneAction(owner, actionDetail);
                            executeOneAction(enemy, actionDetail);
                            break;
                        case CONST:
                        default:
                            throw new IllegalStateException("Unexpected value: " + actionDetail.getActiontTarget());
                    }
                }
            }
        }

    }

    private Player getCurrentPlayer() {
        return round.isUserTurn() ? user : opponent;
    }

    private Player getOpponent() {
        return getCurrentPlayer() == user ? opponent : user;
    }

    private void executeOneAction(Player target, ActionDetail actionDetail) {
        PlayResource playRes = actionDetail.getPlayResource();

        System.out.println("execute target:" + target.getName() + "; " + actionDetail);

        if (playRes == PlayResource.DAMAGE) {
            target.damage(actionDetail.getCount());
        } else {

            Player owner = getCurrentPlayer();
            Player enemy = getOpponent();

            switch (actionDetail.getCommand()) {

                case ADD:
                    target.addResource(playRes, actionDetail.getCount());
                    break;
                case SET_INT:
                    target.setResource(playRes, actionDetail.getCount());
                    break;
                case SET_AS_OPPONENT:
                    Player opponent = target == enemy ? owner : enemy;
                    target.setResource(playRes, opponent.getResource(playRes));
                    break;
                case SET_ALL_AS_MAX:
                    int maxCount = Math.max(owner.getResource(playRes), enemy.getResource(playRes));
                    owner.setResource(playRes, maxCount);
                    enemy.setResource(playRes, maxCount);
                    break;
                case SWAP:
                    int last = owner.getResource(playRes);
                    owner.setResource(playRes, enemy.getResource(playRes));
                    enemy.setResource(playRes, last);
                    break;
                case DROP_ONE:
                    // специальный режим
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + actionDetail.getCommand());
            }
        }
    }

    /**
     * Добавить ресурсы в начале хода
     */
    private void executeIncome() {
        Player player = getCurrentPlayer();
        player.addResource(PlayResource.BRICK_COUNT, player.getResource(PlayResource.BRICK_INCOME));
        player.addResource(PlayResource.GEM_COUNT, player.getResource(PlayResource.GEM_INCOME));
        player.addResource(PlayResource.BEAST_COUNT, player.getResource(PlayResource.BEAST_INCOME));
    }

    /**
     * Проверить что игра закончилась
     */
    @Override
    public void checkWin() {

        EndGameResult playerCR = checkPlayerEnd(user);
        EndGameResult opponentCR = checkPlayerEnd(opponent);

        if (playerCR == EndGameResult.WIN && opponentCR == EndGameResult.WIN) {
            endGame(null, null, true);
        } else if (playerCR == EndGameResult.WIN && opponentCR != EndGameResult.WIN) {
            endGame(user, opponent, false);
        } else if (playerCR != EndGameResult.WIN && opponentCR == EndGameResult.WIN) {
            endGame(opponent, user, false);
        } else if (playerCR == EndGameResult.LOOSE && opponentCR == EndGameResult.LOOSE) {
            endGame(null, null, true);
        } else if (playerCR == EndGameResult.LOOSE && opponentCR != EndGameResult.LOOSE) {
            endGame(opponent, user, false);
        } else if (playerCR != EndGameResult.LOOSE && opponentCR == EndGameResult.LOOSE) {
            endGame(user, opponent, false);
        }

    }

    /**
     *
     * @param winner - победитель
     * @param standoff - ничья
     */
    private void endGame(Player winner,Player looser, boolean standoff){
        System.out.println("------ END GAME -----");
        System.out.println("winner: " + winner.getName());
        Player player = getCurrentPlayer();

        round = RoundEnum.NOGAME;

        if (standoff) {
            player.endGame(EndGameResult.STANDOFF);
            opponent.endGame(EndGameResult.STANDOFF);
        } else {
            winner.endGame(EndGameResult.WIN);
            looser.endGame(EndGameResult.LOOSE);
        }

    }

    /**
     * оповестить игрока User об окончании игры
     * @param endGameResult
     */
    private void alertEndGameUser(EndGameResult endGameResult){
        switch (endGameResult) {
            case WIN:
                showEndGameDialog("Перемога !!! wup-wup-wup :)");
                break;
            case LOOSE:
                showEndGameDialog("Defeat :(");
                break;
            case STANDOFF:
                showEndGameDialog("НИЧЬЯ");
                break;
            case NOTHING:
                break;
        }
    }

    private EndGameResult checkPlayerEnd(Player player) {
        if (player.getResource(PlayResource.TOWER_HP) == 0) {
            return EndGameResult.LOOSE;
        }
        if (player.getResource(PlayResource.TOWER_HP) >= AppImpl.rules.endTowerHP) {
            return EndGameResult.WIN;
        }
        if (player.getResource(PlayResource.BRICK_COUNT) >= AppImpl.rules.endResourceCountHP) {
            return EndGameResult.WIN;
        }
        if (player.getResource(PlayResource.GEM_COUNT) >= AppImpl.rules.endResourceCountHP) {
            return EndGameResult.WIN;
        }
        if (player.getResource(PlayResource.BEAST_COUNT) >= AppImpl.rules.endResourceCountHP) {
            return EndGameResult.WIN;
        }
        return EndGameResult.NOTHING;
    }
}
