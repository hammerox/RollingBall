package com.hammerox.rollingbal;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.LinkedList;
import java.util.List;

import static com.hammerox.rollingbal.Constants.BALL_RADIUS;
import static com.hammerox.rollingbal.Constants.OBSTACLE_DISTANCE;
import static com.hammerox.rollingbal.Constants.WORLD_SIZE;

/**
 * Created by Mauricio on 03-Jan-17.
 */

public class ClassicScreen extends FallingScreen {

    private Character character;
    private float characterY;
    private List<Obstacle> allObstacles;
    private float lastObstaclePosition;

    public ClassicScreen(float cameraSpeed) {
        super(cameraSpeed);
    }

    @Override
    public void updateActors(float delta) {

        // Update player
        character.update(delta);

        // Player-Platform collisions
        for (Obstacle obstacle : allObstacles) {
            character.landedOnPlatform(obstacle.getLeft());
            character.landedOnPlatform(obstacle.getRight());
        }
        characterY = character.getPosition().y;

        // Update camera
        boolean isCharacterBelowLimit = characterY < getCameraBottomPosition() + getLimitToBottomSize();
        if (isCharacterBelowLimit) {
            followCharacter(characterY);
            getFontScore().setColor(Color.RED);
        } else {
            getFontScore().setColor(Color.BLACK);
        }

        if (hasGameStarted())
            moveCamera(delta);

        // Create new obstacles, if necessary
        while (getCameraBottomPosition() - WORLD_SIZE < lastObstaclePosition) {
            lastObstaclePosition -= OBSTACLE_DISTANCE;
            allObstacles.add(Obstacle.newRandomObstacle(lastObstaclePosition));
        }

        // Remove obstacle from top, if necessary
        boolean isObstacleAboveScreen = allObstacles.get(0).getGapPosition().y > getCameraTopPosition();
        if (isObstacleAboveScreen)
            allObstacles.remove(0);


        // Update score if better
        boolean isToUpdateScore = getScore() < -characterY;
        if (isToUpdateScore)
            setScore(- Math.round(characterY));

        // End game if player lose
        boolean isCharacterAboveScreen = characterY - BALL_RADIUS > getCameraTopPosition();
        if (isCharacterAboveScreen)
            setGameOver(true);
    }


    @Override
    public void renderActors() {
        ShapeRenderer shapeRenderer = getShapeRenderer();

        // SHAPE RENDER
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Render obstacles
        for (Obstacle obstacle : allObstacles) {
            obstacle.render(shapeRenderer);
        }

        // Render character
        character.render(shapeRenderer);

        shapeRenderer.end();
    }

    @Override
    void startGame() {
        super.startGame();
        character.setFalling(true);
    }

    @Override
    void resetGame() {
        super.resetGame();

        character = new Character(getViewport());
        character.init(WORLD_SIZE/2, 0);
        allObstacles = new LinkedList<Obstacle>();
        allObstacles.clear();
        lastObstaclePosition = 0;
    }
}
