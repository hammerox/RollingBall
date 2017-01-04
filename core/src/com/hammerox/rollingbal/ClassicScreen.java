package com.hammerox.rollingbal;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


import static com.hammerox.rollingbal.Constants.*;

/**
 * Created by Mauricio on 03-Jan-17.
 */

public class ClassicScreen extends FallingScreen {

    private RollingBallGame.Level level;

    private Character character;
    private Obstacles obstacles;


    public ClassicScreen(RollingBallGame.Level level) {
        super(level.gameSpeed);
        this.level = level;
    }


    @Override
    void newGame() {
        super.newGame();

        character = new Character();
        character.init(WORLD_SIZE/2, 0);
        obstacles = new Obstacles(level);
    }

    @Override
    void startGame() {
        super.startGame();
        character.setFalling(true);
    }

    @Override
    public void updateActors(float delta) {

        // Update player
        character.update(delta);

        // Player-Platform collisions
        Platform landedPlatform = character.getLandedPlatform(obstacles);
        boolean isCharacterDead = false;
        if (landedPlatform != null)
            if (landedPlatform.isDeadly)
                isCharacterDead = true;

        float characterY = character.getPosition().y;

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
        while (getCameraBottomPosition() - WORLD_SIZE < obstacles.getLastPosition()) {
            obstacles.addObstacle();
        }

        // Remove obstacle from top, if necessary
        boolean isObstacleAboveScreen = obstacles.get(0).getGapPosition().y > getCameraTopPosition();
        if (isObstacleAboveScreen)
            obstacles.remove(0);


        // Update score if better
        boolean isToUpdateScore = getScore() < -characterY;
        if (isToUpdateScore)
            setScore(- Math.round(characterY));

        // End game if player lose
        boolean isCharacterAboveScreen = characterY - BALL_RADIUS > getCameraTopPosition();
        if (isCharacterAboveScreen || isCharacterDead)
            setGameOver(true);
    }


    @Override
    public void renderActors() {
        ShapeRenderer shapeRenderer = getShapeRenderer();

        // SHAPE RENDER
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Render obstacles
        obstacles.render(shapeRenderer);

        // Render character
        character.render(shapeRenderer);

        shapeRenderer.end();
    }

}
