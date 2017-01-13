package com.hammerox.rollingbal.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import static com.hammerox.rollingbal.Constants.*;
import static com.hammerox.rollingbal.Util.removeImprecision;

/**
 * Created by Mauricio on 23-Dec-16.
 */

public class Platform extends Actor {

    private boolean isDeadly = false;

    public Platform(Vector2 position) {
        super(position, PLAT_SIZE_DEFAULT);
    }

    public Platform(Vector2 position, Vector2 size) {
        super(position, size);
    }

    public Platform(Vector2 position, Vector2 size, boolean isDeadly) {
        this(position, size);
        this.isDeadly = isDeadly;
    }

    @Override
    public void move(float delta) {
        // Don't move
    }

    @Override
    public void renderShape(ShapeRenderer shapeRenderer) {
        setColor(shapeRenderer);
        shapeRenderer.rect(getPosition().x, getPosition().y, getSize().x, getSize().y);
    }

    private void setColor(ShapeRenderer shapeRenderer) {
        if (isDeadly) {
            shapeRenderer.setColor(Color.RED);
        } else {
            shapeRenderer.setColor(Color.BLUE);
        }
    }

    @Override
    public boolean checkCollision(Actor subject) {
        if (subject instanceof Character) {
            if (isCharacterColliding((Character) subject)) {
                subject.onCollision(this);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onCollision(Actor subject) {
        // Do nothing
    }

    private boolean isCharacterColliding(Character character) {
        return wasCharacterOver(character)
                && isCharacterUnder(character)
                && isCharacterInside(character);
    }

    private boolean wasCharacterOver(Character character) {
        float yBefore = character.getLastPosition().y - BALL_RADIUS;
        return removeImprecision(yBefore) >= removeImprecision(getTop());
    }

    private boolean isCharacterUnder(Character character) {
        float yAfter = character.getPosition().y - BALL_RADIUS;
        return yAfter < getTop();
    }

    private boolean isCharacterInside(Character character) {
        return character.getPosition().x >= getPosition().x
                && character.getPosition().x <= getRight();
    }

    /*
    * GETTERS AND SETTERS
    * */

    public boolean isDeadly() {
        return isDeadly;
    }

    public void setDeadly(boolean deadly) {
        isDeadly = deadly;
    }
}
