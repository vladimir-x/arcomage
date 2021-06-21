package ru.dude.arcomage.game;

/**
 * Игровые правила
 *
 * @author elduderino
 * Date: 30.05.2021
 */
public class Rules {

    public Integer startCardCount;   //количество карт в руке

    public Integer startTowerHP; // стартовая башня
    public Integer startWallHP; // стартовая стена


    //стартовый прирост ресурсов
    public Integer startBrickIncome = 0;
    public Integer startGemIncome = 0;
    public Integer startBeastIncome = 0;

    //стартовое количество ресурсов
    public Integer startBrickCount = 0;
    public Integer startGemCount = 0;
    public Integer startBeastCount = 0;

    //условия победы
    public Integer endTowerHP;
    public Integer endResourceCountHP;

}
