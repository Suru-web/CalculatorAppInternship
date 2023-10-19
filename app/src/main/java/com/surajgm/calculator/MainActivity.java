package com.surajgm.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridLayout;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    LottieAnimationView theme;
    Boolean light=true;
    GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        Window window = getWindow();
        int flags = window.getDecorView().getSystemUiVisibility();
        flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        window.getDecorView().setSystemUiVisibility(flags);

        theme = findViewById(R.id.themeChangeAnim);
        theme.setMinAndMaxProgress(0.5f,0.5f);
        theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (light){
                    theme.setMinAndMaxProgress(0.5f,1f);
                    theme.playAnimation();
                    light=false;
                }
                else {
                    theme.setMinAndMaxProgress(0f,0.5f);
                    theme.playAnimation();
                    light = true;
                }
            }
        });
    }
}