package com.hammerox.rollingbal;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hammerox.rollingbal.databinding.ActivityLevelBinding;


public class LevelActivity extends AppCompatActivity {

    public final static String TAG_LEVEL = "level";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLevelBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_level);

        ButtonFactory.createButtons(this, binding.activityLevel);
    }

}


class ButtonFactory {

    private static LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

    private static View.OnClickListener clickListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), AndroidLauncher.class);
                    String levelName = ((Button) view).getText().toString();
                    intent.putExtra(LevelActivity.TAG_LEVEL, levelName);
                    view.getContext().startActivity(intent);
                }
            };

    static void createButtons(Context context, LinearLayout layout) {
        for (Level level: Level.class.getEnumConstants()) {
            Button button = new Button(context);
            button.setLayoutParams(params);
            button.setText(level.name());
            button.setOnClickListener(clickListener);
            layout.addView(button);
        }
    }
}
