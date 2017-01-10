package com.hammerox.rollingbal.actors;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import static com.hammerox.rollingbal.Constants.*;

/**
 * Created by Mauricio on 27-Dec-16.
 */

public class Obstacle extends Actor {

    private Platform leftPlat;
    private Platform rightPlat;

    public Obstacle(Vector2 gapPosition, float gapSize, boolean leftIsDeadly, boolean rightIsDeadly) {
        super(gapPosition, new Vector2(gapSize, PLAT_SIZE_DEFAULT.y));

        leftPlat = createLeftPlat(leftIsDeadly);
        rightPlat = createRightPlat(rightIsDeadly);
    }

    public Obstacle(Vector2 gapPosition, float gapSize) {
        this(gapPosition, gapSize, false, false);
    }

    public Obstacle(Vector2 gapPosition, float gapSize, boolean bothAreDeadly) {
        this(gapPosition, gapSize, bothAreDeadly, bothAreDeadly);
    }

    @Override
    public void move(float delta) {
        // Do nothing
    }

    @Override
    public void renderShape(ShapeRenderer shapeRenderer) {
        leftPlat.render(shapeRenderer);
        rightPlat.render(shapeRenderer);
    }

    public static Obstacle randomObstacle(float positionY, boolean isDeadly) {
        float gapSize = randomGapSize();
        Vector2 position = randomGapPosition(gapSize, positionY);

        return new Obstacle(position, gapSize, isDeadly);
    }

    private static float randomGapSize() {
        return MathUtils.random() * WORLD_SIZE / 3 + BALL_RADIUS * 2;
    }

    private static Vector2 randomGapPosition(float gapSize, float positionY) {
        Vector2 position = new Vector2();
        position.x = (WORLD_SIZE - gapSize) * MathUtils.random();
        position.y = positionY;

        return position;
    }

    private Platform createLeftPlat(boolean isDeadly) {
        Vector2 leftPosition = new Vector2(0, getPosition().y);
        Vector2 leftSize = new Vector2(getPosition().x, getSize().y);
        return new Platform(leftPosition, leftSize, isDeadly);
    }

    private Platform createRightPlat(boolean isDeadly) {
        Vector2 rightPosition = new Vector2(getPosition().x + getSize().x, getPosition().y);
        Vector2 rightSize = new Vector2(WORLD_SIZE - (getPosition().x + getSize().x), getSize().y);
        return new Platform(rightPosition, rightSize, isDeadly);
    }


    /*
    GETTERS AND SETTERS
    */
    public Platform getLeftPlat() {
        return leftPlat;
    }

    public void setLeftPlat(Platform leftPlat) {
        this.leftPlat = leftPlat;
    }

    public Platform getRightPlat() {
        return rightPlat;
    }

    public void setRightPlat(Platform rightPlat) {
        this.rightPlat = rightPlat;
    }
}
