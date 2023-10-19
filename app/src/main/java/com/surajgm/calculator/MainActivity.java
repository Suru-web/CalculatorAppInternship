package com.surajgm.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    LottieAnimationView theme;
    Button zero, one, two, three, four, five, six, seven, eight, nine, isequal, add, sub, mul, div, mod, clear, invertsign, erase, addpoint;
    boolean isLightMode;
    private static final String THEME_PREF = "theme_pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        zero = findViewById(R.id.zeroButton);
        one = findViewById(R.id.oneButton);
        two = findViewById(R.id.twoButton);
        three = findViewById(R.id.threeButton);
        four = findViewById(R.id.fourButton);
        five = findViewById(R.id.fiveButton);
        six = findViewById(R.id.sixButton);
        seven = findViewById(R.id.severButton);
        eight = findViewById(R.id.eightButton);
        nine = findViewById(R.id.nineButton);
        isequal = findViewById(R.id.isEqualToButton);
        add = findViewById(R.id.addButton);
        sub = findViewById(R.id.subtractButton);
        mul = findViewById(R.id.multiplyButton);
        div = findViewById(R.id.divideButton);
        mod = findViewById(R.id.modulusButton);
        clear = findViewById(R.id.clearButton);
        invertsign = findViewById(R.id.plusMinusButton);
        erase = findViewById(R.id.clearButton);
        addpoint = findViewById(R.id.addPointButton);
        theme = findViewById(R.id.themeChangeAnim);


        isLightMode = loadThemePreference();
        setLottieAnimation(isLightMode);
        toggleTheme(isLightMode);

        theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLightMode = !isLightMode;
                saveThemePreference(isLightMode);
                setLottieAnimation(isLightMode);
                toggleTheme(isLightMode);
            }
        });
    }

    private void toggleTheme(boolean isLightMode) {
        int mode = isLightMode ? AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES;
        AppCompatDelegate.setDefaultNightMode(mode);
        View decorView = getWindow().getDecorView();
        if (isLightMode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                decorView.getWindowInsetsController().setSystemBarsAppearance(
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                );
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                decorView.getWindowInsetsController().setSystemBarsAppearance(
                        0,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                );
            }
        }
    }

    private void setLottieAnimation(boolean isLightMode) {
        if (isLightMode) {
            theme.setMinAndMaxProgress(0.0f,0.5f);
        } else {
            theme.setMinAndMaxProgress(0.5f,1f);
        }
        theme.playAnimation();
    }

    private void saveThemePreference(boolean isLightMode) {
        SharedPreferences preferences = getSharedPreferences(THEME_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLightMode", isLightMode);
        editor.apply();
    }

    private boolean loadThemePreference() {
        SharedPreferences preferences = getSharedPreferences(THEME_PREF, MODE_PRIVATE);
        return preferences.getBoolean("isLightMode", true);
    }
}
