package com.hammerox.rollingball;

import android.os.Bundle;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {

    RollingBallGame game;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        String levelName = getIntent().getStringExtra(LevelActivity.TAG_LEVEL);
        Level level = Level.valueOf(levelName);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        game = new RollingBallGame(level);
		initialize(game, config);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}
}
