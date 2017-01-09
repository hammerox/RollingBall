package com.hammerox.rollingbal.actors;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.hammerox.rollingbal.*;

import java.util.ArrayList;

import static com.hammerox.rollingbal.Constants.*;

/**
 * Created by Mauricio on 04-Jan-17.
 */

public class Obstacles extends ArrayList<Obstacle> {

    private RollingBallGame.Level level;
    private int totalCount = 0;
    private float lastPosition = 0;

    public Obstacles(RollingBallGame.Level level) {
        this.level = level;
    }

    @Override
    public void clear() {
        super.clear();
        totalCount = 0;
        lastPosition = 0;
    }

    public void addObstacle() {
        lastPosition -= OBSTACLE_DISTANCE;

        switch (level) {
            case SPIKES:
                add(spikeRandomGenerator(lastPosition));
                break;
            default:
                add(Obstacle.randomObstacle(lastPosition, false));
        }

        totalCount++;
    }

    public void render(ShapeRenderer shapeRenderer) {
        for (Obstacle obstacle : this) {
            obstacle.render(shapeRenderer);
        }
    }

    private static Obstacle spikeRandomGenerator(float position) {
        return (MathUtils.random() > SPIKE_CREATION_CHANCE)
                ? Obstacle.randomObstacle(position, false)
                : Obstacle.randomObstacle(position, true);
    }


    /*
    * GETTERS AND SETTERS
    * */

    public float getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(float lastPosition) {
        this.lastPosition = lastPosition;
    }

    public RollingBallGame.Level getLevel() {
        return level;
    }

    public void setLevel(RollingBallGame.Level level) {
        this.level = level;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}