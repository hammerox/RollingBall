package com.hammerox.rollingbal;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.hammerox.rollingbal.actors.Actor;

import java.util.ArrayList;

import static com.hammerox.rollingbal.Constants.*;

/**
 * Created by Mauricio on 04-Jan-17.
 */

public class Obstacles extends ArrayList<Actor> {

    private Level level;
    private int totalObstaclesCreated = 0;
    private float lastPosition = 0;

    public Obstacles(Level level) {
        this.level = level;
    }

    @Override
    public void clear() {
        super.clear();
        totalObstaclesCreated = 0;
        lastPosition = 0;
    }

    public void renderAll(ShapeRenderer shapeRenderer) {
        for (Actor obstacle : this) {
            obstacle.render(shapeRenderer);
        }
    }

    public void handleObstaclesOutsideScreen(float cameraBottom, float cameraTop) {
        addBelowScreen(cameraBottom);
        removeAboveScreen(cameraTop);
    }

    private void addBelowScreen(float cameraBottom) {
        while (cameraBottom - WORLD_SIZE < getLastPosition()) {
            addObstacle();
        }
    }

    private void removeAboveScreen(float cameraTop) {
        while (get(0).getPosition().y > cameraTop) {
            remove(0);
        }
    }

    public void addObstacle() {
        lastPosition -= OBSTACLE_DISTANCE;
        add(ObstacleFactory.create(level, lastPosition));
        totalObstaclesCreated++;
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

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public int getTotalObstaclesCreated() {
        return totalObstaclesCreated;
    }

    public void setTotalObstaclesCreated(int totalObstaclesCreated) {
        this.totalObstaclesCreated = totalObstaclesCreated;
    }
}
