package com.surajgm.calculator;

import static com.surajgm.calculator.MainActivity.THEME_PREF;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.window.OnBackInvokedDispatcher;

public class HistoryLayout extends AppCompatActivity {
    ImageButton back;
    TextView textView,clearBtn;
    Boolean isLightMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        SharedPreferences preferences = getSharedPreferences(THEME_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        View decorView = getWindow().getDecorView();


        back = findViewById(R.id.backButton);
        textView = findViewById(R.id.showHistory);
        clearBtn = findViewById(R.id.clearButton);
        textView.setText(getIntent().getStringExtra("HistoryValue"));
        isLightMode = preferences.getBoolean("isLightMode", true);
        if (isLightMode){
            back.setImageResource(R.drawable.back);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                decorView.getWindowInsetsController().setSystemBarsAppearance(
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                );
            }
        }
        else {
            back.setImageResource(R.drawable.back_white);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                decorView.getWindowInsetsController().setSystemBarsAppearance(
                        0,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                );
            }
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("historyeq","");
                editor.apply();
                textView.setText("");
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
    }
}