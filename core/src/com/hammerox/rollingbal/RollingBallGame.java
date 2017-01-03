package com.hammerox.rollingbal;

import com.badlogic.gdx.Game;

import static com.hammerox.rollingbal.Constants.*;

public class RollingBallGame extends Game {

    Level level;

    public RollingBallGame() {
        // Default level
        level = Level.SPIKES;
    }

    public RollingBallGame(Level level) {
        this.level = level;
    }

    @Override
	public void create () {
        setScreen(new ClassicScreen(level));
    }


    public enum Level {
        CASUAL(CAMERA_SPEED_SLOW, false),
        SPEED(CAMERA_SPEED_FAST, false),
        SPIKES(CAMERA_SPEED_SLOW, true);

        float gameSpeed;
        boolean gameCanKill;

        Level(float gameSpeed, boolean gameCanKill) {
            this.gameSpeed = gameSpeed;
            this.gameCanKill = gameCanKill;
        }
    }

}
