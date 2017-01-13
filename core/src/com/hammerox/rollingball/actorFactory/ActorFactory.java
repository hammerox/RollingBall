package com.hammerox.rollingball.actorFactory;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.hammerox.rollingball.Obstacles;
import com.hammerox.rollingball.actors.Character;
import com.hammerox.rollingball.actors.DoublePlatform;

import static com.hammerox.rollingball.Constants.*;

/**
 * Created by Mauricio on 12-Jan-17.
 */

public abstract class ActorFactory {

    public abstract Character addCharacter();
    public abstract void addObstacle(Obstacles obstacleList, float height);

    public static DoublePlatform createRandomDoublePlatform(float positionY, boolean isDeadly) {
        float gapSize = randomGapSize();
        Vector2 position = randomGapPosition(gapSize, positionY);
        return new DoublePlatform(position, gapSize, isDeadly);
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

}
