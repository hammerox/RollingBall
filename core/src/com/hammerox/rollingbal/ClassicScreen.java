package com.hammerox.rollingbal;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.hammerox.rollingbal.actors.Actor;
import com.hammerox.rollingbal.actors.DoublePlatform;
import com.hammerox.rollingbal.actors.Obstacles;
import com.hammerox.rollingbal.actors.Character;


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

        character = new com.hammerox.rollingbal.actors.Character(WORLD_SIZE/2, 0);
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
        character.move(delta);

        moveCameraWithActor(delta, character);
        updateScore(character);

        // TODO - This goes to Simple Factory Pattern
        // Create new obstacles, if necessary
        while (getCameraBottomPosition() - WORLD_SIZE < obstacles.getLastPosition()) {
            obstacles.addObstacle();
            int position = obstacles.size() - 1;
            DoublePlatform plat = obstacles.get(position);

            character.addObstacle(plat);
        }
        // Remove obstacle from top, if necessary
        boolean isObstacleAboveScreen = obstacles.get(0).getPosition().y > getCameraTopPosition();
        if (isObstacleAboveScreen) {
            character.removeObstacle(obstacles.get(0));
            obstacles.remove(0);
        }

        // End game if player lose
        boolean isCharacterAboveScreen = character.getPosition().y - BALL_RADIUS > getCameraTopPosition();
        if (isCharacterAboveScreen || character.isDead())
            setGameOver(true);
    }


    @Override
    public void renderActors() {
        ShapeRenderer shapeRenderer = getShapeRenderer();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        obstacles.render(shapeRenderer);
        character.render(shapeRenderer);
        shapeRenderer.end();
    }

    private void updateScore(Actor actor) {
        boolean isToUpdateScore = getScore() < -actor.getPosition().y;
        if (isToUpdateScore)
            setScore(- Math.round(actor.getPosition().y));
    }

}
