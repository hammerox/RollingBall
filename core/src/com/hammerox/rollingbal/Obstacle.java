package com.hammerox.rollingbal;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Mauricio on 27-Dec-16.
 */

public class Obstacle {

    private Vector2 gapPosition;
    private Vector2 gapSize;

    private Platform left;
    private Platform right;

    public Obstacle(Vector2 gapPosition, float gapSize) {
        this.gapPosition = gapPosition;
        this.gapSize = Constants.PLAT_SIZE_DEFAULT.cpy();
        this.gapSize.x = gapSize;

        Vector2 leftPosition = new Vector2(0, gapPosition.y);
        Vector2 leftSize = new Vector2(gapPosition.x, this.gapSize.y);
        left = new Platform(leftPosition, leftSize);

        Vector2 rightPosition = new Vector2(gapPosition.x + this.gapSize.x, gapPosition.y);
        Vector2 rightSize = new Vector2(Constants.WORLD_SIZE - (gapPosition.x + this.gapSize.x), this.gapSize.y);
        right = new Platform(rightPosition, rightSize);
    }

    public void render(ShapeRenderer shapeRenderer) {
        left.render(shapeRenderer);
        right.render(shapeRenderer);
    }

    public static Obstacle newRandomObstacle(float height) {
        Vector2 position = new Vector2();
        float gapSize = MathUtils.random() * Constants.WORLD_SIZE / 3 + Constants.BALL_RADIUS * 2;
        position.x = (Constants.WORLD_SIZE - gapSize) * MathUtils.random();
        position.y = height;
        return new Obstacle(position, gapSize);
    }

    /*
    GETTERS AND SETTERS
    */

    public Vector2 getGapPosition() {
        return gapPosition;
    }

    public void setGapPosition(Vector2 gapPosition) {
        this.gapPosition = gapPosition;
    }

    public Vector2 getGapSize() {
        return gapSize;
    }

    public void setGapSize(Vector2 gapSize) {
        this.gapSize = gapSize;
    }

    public Platform getLeft() {
        return left;
    }

    public void setLeft(Platform left) {
        this.left = left;
    }

    public Platform getRight() {
        return right;
    }

    public void setRight(Platform right) {
        this.right = right;
    }
}
