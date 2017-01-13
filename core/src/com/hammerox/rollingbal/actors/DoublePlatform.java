package com.hammerox.rollingbal.actors;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import static com.hammerox.rollingbal.Constants.*;

/**
 * Created by Mauricio on 27-Dec-16.
 */

public class DoublePlatform extends Actor {

    private Platform leftPlat;
    private Platform rightPlat;

    public DoublePlatform(Vector2 gapPosition, float gapSize, boolean leftIsDeadly, boolean rightIsDeadly) {
        super(gapPosition, new Vector2(gapSize, PLAT_SIZE_DEFAULT.y));

        leftPlat = setLeftPlat(leftIsDeadly);
        rightPlat = setRightPlat(rightIsDeadly);
    }

    public DoublePlatform(Vector2 gapPosition, float gapSize) {
        this(gapPosition, gapSize, false, false);
    }

    public DoublePlatform(Vector2 gapPosition, float gapSize, boolean bothAreDeadly) {
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

    @Override
    public boolean checkCollision(Actor subject) {
        if (subject instanceof Character) {
            leftPlat.checkCollision(subject);
            rightPlat.checkCollision(subject);
        }
        return false;
    }

    @Override
    public void onCollision(Actor subject) {
        // Do nothing
    }

    private Platform setLeftPlat(boolean isDeadly) {
        Vector2 leftPosition = new Vector2(0, getPosition().y);
        Vector2 leftSize = new Vector2(getPosition().x, getSize().y);
        return new Platform(leftPosition, leftSize, isDeadly);
    }

    private Platform setRightPlat(boolean isDeadly) {
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
