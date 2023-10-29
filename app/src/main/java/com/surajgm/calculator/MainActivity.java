package com.surajgm.calculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LottieAnimationView theme;
    Button zero, one, two, three, four, five, six, seven, eight, nine, isequal, add, sub, mul, div, mod, clear, power, erase, addpoint;
    boolean isLightMode;
    public static final String THEME_PREF = "theme_pref";
    Vibrator vibrator;
    TextView history,current;
    String currentEq,historyEq="0",currentHistory;
    Boolean isPointPressed = false,isEqualPressed = false;
    ImageButton historyBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
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
        power = findViewById(R.id.powerButton);
        erase = findViewById(R.id.backspaceButton);
        addpoint = findViewById(R.id.addPointButton);
        theme = findViewById(R.id.themeChangeAnim);
        history = findViewById(R.id.historyNumber);
        current = findViewById(R.id.currentNumbers);
        historyBtn = findViewById(R.id.clickhistoryButton);

        zero.setOnClickListener(this);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        isequal.setOnClickListener(this);
        add.setOnClickListener(this);
        sub.setOnClickListener(this);
        mul.setOnClickListener(this);
        div.setOnClickListener(this);
        mod.setOnClickListener(this);
        clear.setOnClickListener(this);
        power.setOnClickListener(this);
        erase.setOnClickListener(this);
        addpoint.setOnClickListener(this);
        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences(THEME_PREF, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("currenteq",currentEq);
                editor.putString("historyeq",historyEq);
                editor.apply();
                Intent intent = new Intent(MainActivity.this, HistoryLayout.class);
                intent.putExtra("HistoryValue",historyEq);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });


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

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences(THEME_PREF, MODE_PRIVATE);
        currentEq = preferences.getString("currenteq","");
        historyEq = preferences.getString("historyeq","");
    }

    @Override
    public void onClick(View v) {
        currentEq = current.getText().toString();
        vibrator.vibrate(1);
        if (v.getId()==R.id.isEqualToButton){
            isEqualPressed = true;
            Float answer;
            if (!currentEq.isEmpty() && isNumber(currentEq.substring(currentEq.length()-1))) {
                answer = evaluateExpression(tokenizeEquation(currentEq));
                currentHistory = currentEq+" ="+" "+ answer;
                historyEq += currentEq+" ="+" "+ answer+"\n";
                history.setText(currentHistory);
                current.setText(String.valueOf(answer));
            }else if (currentEq.length() > 0 && isOperator(currentEq.substring(currentEq.length()-1))){
                current.setText(currentEq);
            }
        } else if (v.getId()==R.id.oneButton) {
            if (isEqualPressed){
                currentEq = "";
                current.setText(currentEq);
            }
            currentEq = currentEq.concat("1");
            current.setText(currentEq);
            isEqualPressed = false;
        } else if (v.getId()==R.id.twoButton) {
            if (isEqualPressed){
                currentEq = "";
                current.setText(currentEq);
            }
            currentEq = currentEq.concat("2");
            current.setText(currentEq);
            isEqualPressed = false;
        } else if (v.getId()==R.id.threeButton) {
            if (isEqualPressed){
                currentEq = "";
                current.setText(currentEq);
            }
            currentEq = currentEq.concat("3");
            current.setText(currentEq);
            isEqualPressed = false;
        } else if (v.getId()==R.id.fourButton) {
            if (isEqualPressed){
                currentEq = "";
                current.setText(currentEq);
            }
            currentEq = currentEq.concat("4");
            current.setText(currentEq);
            isEqualPressed = false;
        } else if (v.getId()==R.id.fiveButton) {
            if (isEqualPressed){
                currentEq = "";
                current.setText(currentEq);
            }
            currentEq = currentEq.concat("5");
            current.setText(currentEq);
            isEqualPressed = false;
        } else if (v.getId()==R.id.sixButton) {
            if (isEqualPressed){
                currentEq = "";
                current.setText(currentEq);
            }
            currentEq = currentEq.concat("6");
            current.setText(currentEq);
            isEqualPressed = false;
        } else if (v.getId()==R.id.severButton) {
            if (isEqualPressed){
                currentEq = "";
                current.setText(currentEq);
            }
            currentEq = currentEq.concat("7");
            current.setText(currentEq);
            isEqualPressed = false;
        } else if (v.getId()==R.id.eightButton) {
            if (isEqualPressed){
                currentEq = "";
                current.setText(currentEq);
            }
            currentEq = currentEq.concat("8");
            current.setText(currentEq);
            isEqualPressed = false;
        } else if (v.getId()==R.id.nineButton) {
            if (isEqualPressed){
                currentEq = "";
                current.setText(currentEq);
            }
            currentEq = currentEq.concat("9");
            current.setText(currentEq);
            isEqualPressed = false;
        } else if (v.getId()==R.id.zeroButton) {
            if (isEqualPressed){
                currentEq = "";
                current.setText(currentEq);
            }
            currentEq = currentEq.concat("0");
            current.setText(currentEq);
            isEqualPressed = false;
        } else if (v.getId()==R.id.clearButton) {
            currentEq = "";
            currentHistory = "";
            history.setText(currentHistory);
            current.setText(currentEq);
            isEqualPressed = false;
        } else if (v.getId()==R.id.backspaceButton) {
            StringBuilder sb = new StringBuilder(currentEq);
            if (!currentEq.isEmpty()) {
                currentEq = sb.deleteCharAt(currentEq.length()-1).toString();
                current.setText(currentEq);
            }
            isEqualPressed = false;
        } else if (v.getId()==R.id.addPointButton) {
            if (isPointPressed){
                current.setText(currentEq);
            }
            else {
                currentEq = currentEq.concat(".");
                current.setText(currentEq);
                isPointPressed = true;
            }
            isEqualPressed = false;
        } else if (v.getId()==R.id.addButton) {
            isPointPressed = false;
            if (currentEq.length() > 0 && isOperator(currentEq.substring(currentEq.length()-1))){
                currentEq = currentEq.substring(0,currentEq.length()-1)+"+";
                current.setText(currentEq);
            }
            else if (currentEq.length() > 0 && isNumber(currentEq.substring(currentEq.length()-1))){
                currentEq = currentEq.concat("+");
                current.setText(currentEq);
            }
            isEqualPressed = false;
        } else if (v.getId()==R.id.subtractButton) {
            isPointPressed = false;
            if (currentEq.length() > 0 && isOperator(currentEq.substring(currentEq.length()-1))){
                currentEq = currentEq.substring(0,currentEq.length()-1)+"-";
                current.setText(currentEq);
                System.out.println(currentEq);
            }
            else if (currentEq.length() > 0 && isNumber(currentEq.substring(currentEq.length()-1))){
                currentEq = currentEq.concat("-");
                current.setText(currentEq);
            }
            isEqualPressed = false;
        } else if (v.getId()==R.id.multiplyButton) {
            isPointPressed = false;
            if (currentEq.length() > 0 && isOperator(currentEq.substring(currentEq.length()-1))){
                currentEq = currentEq.substring(0,currentEq.length()-1)+"\u00d7";
                current.setText(currentEq);
                System.out.println(currentEq);
            }
            else if (currentEq.length() > 0 && isNumber(currentEq.substring(currentEq.length()-1))){
                currentEq = currentEq.concat("\u00d7");
                current.setText(currentEq);
            }
            isEqualPressed = false;
        } else if (v.getId()==R.id.divideButton) {
            isPointPressed = false;
            if (currentEq.length() > 0 && isOperator(currentEq.substring(currentEq.length()-1))){
                currentEq = currentEq.substring(0,currentEq.length()-1)+"\u00f7";
                current.setText(currentEq);
                System.out.println(currentEq);
            }
            else if (currentEq.length() > 0 && isNumber(currentEq.substring(currentEq.length()-1))){
                currentEq = currentEq.concat("\u00f7");
                current.setText(currentEq);
            }
            isEqualPressed = false;
        } else if (v.getId()==R.id.modulusButton) {
            isPointPressed = false;
            if (currentEq.length() > 0 && isOperator(currentEq.substring(currentEq.length()-1))){
                currentEq = currentEq.substring(0,currentEq.length()-1)+"\u2052";
                current.setText(currentEq);
                System.out.println(currentEq);
            }
            else if (currentEq.length() > 0 && isNumber(currentEq.substring(currentEq.length()-1))){
                currentEq = currentEq.concat("\u2052");
                current.setText(currentEq);
            }
            isEqualPressed = false;
        } else if (v.getId()==R.id.powerButton) {
            isPointPressed = false;
            if (currentEq.length() > 0 && isOperator(currentEq.substring(currentEq.length()-1))){
                currentEq = currentEq.substring(0,currentEq.length()-1)+"^";
                current.setText(currentEq);
                System.out.println(currentEq);
            }
            else if (currentEq.length() > 0 && isNumber(currentEq.substring(currentEq.length()-1))){
                currentEq = currentEq.concat("^");
                current.setText(currentEq);
            }
            isEqualPressed = false;
        }
    }
    private List<String> tokenizeEquation(String equation) {
        List<String> tokens = new ArrayList<>();
        String operators = "+-×÷⁒^";
        StringBuilder tokenBuilder = new StringBuilder();
        for (char c : equation.toCharArray()) {
            String character = String.valueOf(c);

            if (operators.contains(character)) {
                if (tokenBuilder.length() > 0) {
                    tokens.add(tokenBuilder.toString());
                    tokenBuilder.setLength(0); // Clear the token builder
                }
                tokens.add(character);
            } else {
                // This character is not an operator, so add it to the current token
                tokenBuilder.append(character);
            }
        }
        // Add the last token (if any)
        if (tokenBuilder.length() > 0) {
            tokens.add(tokenBuilder.toString());
        }
        return tokens;
    }

    private float evaluateExpression(List<String> tokens) {
        Stack<Float> numbers = new Stack<>();
        Stack<String> operators = new Stack<>();

        for (String token : tokens) {
            if (isNumber(token)) {
                numbers.push(Float.parseFloat(token));
            } else if (isOperator(token)) {
                while (!operators.isEmpty() && hasHigherPrecedence(operators.peek(), token)) {
                    float operand2 = numbers.pop();
                    float operand1 = numbers.pop();
                    String operator = operators.pop();
                    float result = applyOperator(operand1, operand2, operator);
                    numbers.push(result);
                }
                operators.push(token);
            }
        }

        while (!operators.isEmpty()) {
            float operand2 = numbers.pop();
            float operand1 = numbers.pop();
            String operator = operators.pop();
            float result = applyOperator(operand1, operand2, operator);
            numbers.push(result);
        }

        return numbers.pop();
    }

    private boolean isNumber(String token) {
        try {
            Float.parseFloat(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isOperator(String token) {
        return "+-×÷⁒^".contains(token);
    }

    private boolean hasHigherPrecedence(String operator1, String operator2) {
        // Return true if operator1 has higher precedence than operator2
        if (precedence(operator1)>precedence(operator2)){
            return true;
        }
        return false;
    }

    private int precedence(String operator1) {
        switch (operator1) {
            case "+":
                return 1;
            case "-":
                return 2;
            case "×":
                return 3;
            case "÷":
                return 4;
            case "⁒":
                return 5;
            case "^":
                return 6;
            default:
                return 0;
        }
    }

    private float applyOperator(float operand1, float operand2, String operator) {
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "×":
                return operand1 * operand2;
            case "÷":
                return operand1 / operand2;
            case "⁒":
                return operand1 % operand2;
            case "^":
                return (float) Math.pow(operand1,operand2);
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    private void toggleTheme(boolean isLightMode) {
        int mode = isLightMode ? AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES;
        AppCompatDelegate.setDefaultNightMode(mode);
        View decorView = getWindow().getDecorView();
        if (isLightMode) {
            historyBtn.setBackgroundResource(R.drawable.history_black);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                decorView.getWindowInsetsController().setSystemBarsAppearance(
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                );
            }
        } else {
            historyBtn.setBackgroundResource(R.drawable.history_white);
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
