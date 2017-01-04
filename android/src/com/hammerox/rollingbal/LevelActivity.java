package com.hammerox.rollingbal;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hammerox.rollingbal.databinding.ActivityLevelBinding;

import static com.hammerox.rollingbal.RollingBallGame.*;

public class LevelActivity extends AppCompatActivity implements View.OnClickListener{

    public final static String TAG_LEVEL = "level";

    /* To add a new level:
    *   onClick  - Add level name to button
    *   onCreate - Bind clickListener to button
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLevelBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_level);

        binding.buttonCasual.setOnClickListener(this);
        binding.buttonSpeed.setOnClickListener(this);
        binding.buttonSpikes.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(LevelActivity.this, AndroidLauncher.class);
        String levelName;

        switch (view.getId()) {
            case R.id.button_casual:
                levelName = Level.CASUAL.name();
                break;
            case R.id.button_speed:
                levelName = Level.SPEED.name();
                break;
            case R.id.button_spikes:
                levelName = Level.SPIKES.name();
                break;
            default:
                levelName = Level.SPEED.name();
        }

        intent.putExtra(TAG_LEVEL, levelName);
        startActivity(intent);
    }
}
