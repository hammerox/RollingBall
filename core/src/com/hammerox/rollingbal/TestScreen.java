package com.hammerox.rollingbal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import static com.hammerox.rollingbal.Constants.WORLD_SIZE;

/**
 * Created by Mauricio on 22-Dec-16.
 */

public class TestScreen extends ScreenAdapter {

    public final static String TAG = TestScreen.class.getName();

    private ExtendViewport viewport;
    private ShapeRenderer shapeRenderer;

    private Ball ball;
    private Platform platform;

    @Override
    public void show() {
        Gdx.app.log(TAG, "show");

        shapeRenderer = new ShapeRenderer();
        viewport = new ExtendViewport(WORLD_SIZE, WORLD_SIZE);

        ball = new Ball(viewport);
        ball.init(WORLD_SIZE/2, WORLD_SIZE/2);

        platform = new Platform(new Vector2(WORLD_SIZE/3, WORLD_SIZE/3));
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log(TAG, "resize");
        viewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);

        // Update positions
        ball.update(delta);
        ball.landedOnPlatform(platform);

        // Render
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        platform.render(shapeRenderer);
        ball.render(shapeRenderer);
        shapeRenderer.end();
    }

    @Override
    public void hide() {
        super.hide();
        Gdx.app.log(TAG, "hide");
    }

    @Override
    public void pause() {
        super.pause();
        Gdx.app.log(TAG, "pause");
    }

    @Override
    public void resume() {
        super.resume();
        Gdx.app.log(TAG, "resume");
    }

    @Override
    public void dispose() {
        Gdx.app.log(TAG, "dispose");
        shapeRenderer.dispose();
    }
}
