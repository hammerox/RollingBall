package com.hammerox.rollingbal;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import static com.hammerox.rollingbal.Constants.*;

public class RollingBallGame extends Game {

    Level level;
    TestScreen testScreen;
    FallingScreen fallingScreen;
    LevelScreen levelScreen;

    public RollingBallGame(Level level) {
        this.level = level;
    }

    @Override
	public void create () {
        testScreen = new TestScreen();
        fallingScreen = new FallingScreen(level.fallingSpeed);
        levelScreen = new LevelScreen();

        switch (Gdx.app.getType()) {
            default:
                setScreen(fallingScreen);
                break;
        }

    }


    public enum Level {
        CLASSIC(CAMERA_SPEED_SLOW),
        SPEED(CAMERA_SPEED_FAST);

        float fallingSpeed;

        Level(float fallingSpeed) {
            this.fallingSpeed = fallingSpeed;
        }
    }

}
