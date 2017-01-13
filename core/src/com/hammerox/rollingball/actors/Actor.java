package com.hammerox.rollingball.actors;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Mauricio on 10-Jan-17.
 */

public abstract class Actor implements CollisionListener {

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 lastPosition;
    private Vector2 lastVelocity;
    private Vector2 size;

    public Actor(float initialX, float initialY, float width, float height) {
        position = new Vector2(initialX, initialY);
        velocity = new Vector2();

        setLastPosition(getPosition().cpy());
        setLastVelocity(getVelocity().cpy());

        size = new Vector2(width, height);
    }

    public Actor(Vector2 position, Vector2 size) {
        this(position.x, position.y, size.x, size.y);
    }

    public abstract void renderShape(ShapeRenderer shapeRenderer);
    public abstract void move(float delta);

    public void render(ShapeRenderer renderer) {
        saveMovement();
        renderShape(renderer);
    }

    public void saveMovement() {
        lastPosition.set(position);
        lastVelocity.set(velocity);
    }

    /*
    * GETTERS AND SETTERS
    */
    public Vector2 getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(Vector2 lastPosition) {
        this.lastPosition = lastPosition;
    }

    public Vector2 getLastVelocity() {
        return lastVelocity;
    }

    public void setLastVelocity(Vector2 lastVelocity) {
        this.lastVelocity = lastVelocity;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getSize() {
        return size;
    }

    public void setSize(Vector2 size) {
        this.size = size;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public float getTop() {
        return position.y + size.y;
    }

    public float getRight() {
        return position.x + size.x;
    }
}
