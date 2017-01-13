package com.hammerox.rollingbal;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.hammerox.rollingbal.actorFactory.ActorFactory;
import com.hammerox.rollingbal.actors.Actor;
import com.hammerox.rollingbal.actors.Character;


import static com.hammerox.rollingbal.Constants.*;

/**
 * Created by Mauricio on 03-Jan-17.
 */

public class ClassicScreen extends FallingScreen {

    private Level level;
    private Character character;
    private Obstacles obstacles;


    public ClassicScreen(Level level) {
        super(level.gameSpeed);
        this.level = level;
    }


    @Override
    void newGame() {
        super.newGame();
        character = level.factory.addCharacter();
        obstacles = new Obstacles(level);
        character.setObstacles(obstacles);
    }

    @Override
    void startGame() {
        super.startGame();
        character.setGravityOn(true);
    }

    @Override
    public boolean gameOverCondition() {
        boolean isCharacterAboveScreen = character.getPosition().y - BALL_RADIUS > getCameraTopPosition();
        return character.isDead() || isCharacterAboveScreen;
    }

    @Override
    public void updateActors(float delta) {
        character.move(delta);
        moveCameraWithActor(delta, character);
        updateScore(character);
        obstacles.handleObstaclesOutsideScreen(getCameraBottomPosition(), getCameraTopPosition());
    }


    @Override
    public void renderActors() {
        ShapeRenderer shapeRenderer = getShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        obstacles.renderAll(shapeRenderer);
        character.render(shapeRenderer);
        shapeRenderer.end();
    }

    private void updateScore(Actor actor) {
        boolean isToUpdateScore = getScore() < -actor.getPosition().y;
        if (isToUpdateScore)
            setScore(- Math.round(actor.getPosition().y));
    }

}
