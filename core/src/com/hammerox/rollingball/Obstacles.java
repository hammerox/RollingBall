package com.hammerox.rollingball;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.hammerox.rollingball.actors.Actor;

import java.util.ArrayList;

import static com.hammerox.rollingball.Constants.*;

/**
 * Created by Mauricio on 04-Jan-17.
 */

public class Obstacles extends ArrayList<Actor> {

    private Level level;
    private float lastObstacleHeight = 0;

    public Obstacles(Level level) {
        this.level = level;
    }

    @Override
    public void clear() {
        super.clear();
        lastObstacleHeight = 0;
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
        while (cameraBottom - WORLD_SIZE < lastObstacleHeight) {
            addObstacle();
        }
    }

    private void removeAboveScreen(float cameraTop) {
        while (get(0).getPosition().y > cameraTop) {
            remove(0);
        }
    }

    public void addObstacle() {
        lastObstacleHeight -= OBSTACLE_DISTANCE;
        level.factory.addObstacle(this, lastObstacleHeight);
    }


    /*
    * GETTERS AND SETTERS
    * */
    public float getLastObstacleHeight() {
        return lastObstacleHeight;
    }

    public void setLastObstacleHeight(float lastObstacleHeight) {
        this.lastObstacleHeight = lastObstacleHeight;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
