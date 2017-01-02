package com.hammerox.rollingbal;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hammerox.rollingbal.databinding.ActivityLevelBinding;

public class LevelActivity extends AppCompatActivity {

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                default:
                    Intent intent = new Intent(LevelActivity.this, AndroidLauncher.class);
                    startActivity(intent);
                    break;
            }
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
