package com.hammerox.rollingbal;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import static com.hammerox.rollingbal.Constants.*;

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
        this.gapSize = PLAT_SIZE_DEFAULT.cpy();
        this.gapSize.x = gapSize;

        Vector2 leftPosition = new Vector2(0, gapPosition.y);
        Vector2 leftSize = new Vector2(gapPosition.x, this.gapSize.y);
        left = new Platform(leftPosition, leftSize);

        Vector2 rightPosition = new Vector2(gapPosition.x + this.gapSize.x, gapPosition.y);
        Vector2 rightSize = new Vector2(WORLD_SIZE - (gapPosition.x + this.gapSize.x), this.gapSize.y);
        right = new Platform(rightPosition, rightSize);
    }

    public Obstacle(Vector2 gapPosition, float gapSize, boolean leftIsDeadly, boolean rightIsDeadly) {
        this(gapPosition, gapSize);
        left.isDeadly = leftIsDeadly;
        right.isDeadly = rightIsDeadly;
    }

    public void render(ShapeRenderer shapeRenderer) {
        left.render(shapeRenderer);
        right.render(shapeRenderer);
    }

    public static Obstacle newObstacle(RollingBallGame.Level level, float positionY) {
        float gapSize = randomGapSize();
        Vector2 position = randomGapPosition(gapSize, positionY);

        switch (level) {
            case SPIKES:
                return spikesRandomObstacle(position, gapSize);
            default:
                return new Obstacle(position, gapSize);
        }
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

    private static Obstacle spikesRandomObstacle(Vector2 position, float gapSize) {
        float random = MathUtils.random();
        return (random > SPIKE_CREATION_CHANCE)
                ? new Obstacle(position, gapSize)
                : new Obstacle(position, gapSize, true, true);
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
