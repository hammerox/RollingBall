package com.hammerox.rollingbal;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hammerox.rollingbal.databinding.ActivityLevelBinding;

public class LevelActivity extends AppCompatActivity {

    public final static String TAG_LEVEL = "level";

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(LevelActivity.this, AndroidLauncher.class);

            switch (view.getId()) {
                case R.id.button_classic:
                    intent.putExtra(TAG_LEVEL, RollingBallGame.Level.CLASSIC.name());
                    break;
                case R.id.button_speed:
                    intent.putExtra(TAG_LEVEL, RollingBallGame.Level.SPEED.name());
                    break;
                default:
                    intent.putExtra(TAG_LEVEL, RollingBallGame.Level.SPEED.name());
                    break;
            }

            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLevelBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_level);

        binding.buttonClassic.setOnClickListener(clickListener);
        binding.buttonSpeed.setOnClickListener(clickListener);
    }
}
