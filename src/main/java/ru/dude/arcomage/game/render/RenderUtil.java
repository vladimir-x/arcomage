package ru.dude.arcomage.game.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * Утилитарный класс для всяких отрисовок
 * @author elduderino
 * Date: 30.05.2021
 */
public class RenderUtil {

    /**
     * Рисует пустой прямоугольник линиями заданным цветом
     * @param renderer
     * @param color
     * @param rect
     */
    public static void renderBorderRect(ShapeRenderer renderer, Color color, Rectangle rect) {
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(color);
        renderer.rect(rect.x, rect.y, rect.width, rect.height);
        renderer.end();
    }


}
