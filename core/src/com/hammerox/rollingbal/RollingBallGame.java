package com.hammerox.rollingbal;

import com.badlogic.gdx.Game;

import static com.hammerox.rollingbal.Constants.*;

public class RollingBallGame extends Game {

    Level level;

    public RollingBallGame() {
        // Default level
        level = Level.SPEED;
    }

    public RollingBallGame(Level level) {
        this.level = level;
    }

    @Override
	public void create () {
        setScreen(level.screen);
    }


    public enum Level {
        CASUAL(new ClassicScreen(CAMERA_SPEED_SLOW)),
        SPEED(new ClassicScreen(CAMERA_SPEED_FAST));

        FallingScreen screen;

        Level(FallingScreen screen) {
            this.screen = screen;
        }
    }

}
