package com.hammerox.rollingball;

import com.badlogic.gdx.Game;


public class RollingBallGame extends Game {

    private Level level;

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

}
