package ru.dude.arcomage.game.interfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 *
 * @author elduderino
 */
public interface Rendereble {
 
    // прямоугольник, в котором находится объект
    //Rectangle getRectangle();
    
    // пересчитать данные для рендеринга (перемещение объекта или изменение настроек экрана)
    void update();
    
    //отрисовка примитивов
    void render(ShapeRenderer renderer,SpriteBatch spriteBatch);
    
    
}
