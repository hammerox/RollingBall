package com.hammerox.rollingbal.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hammerox.rollingbal.RollingBallGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.height = 1000;
        config.width = 500;
		new LwjglApplication(new RollingBallGame(), config);
	}
}
