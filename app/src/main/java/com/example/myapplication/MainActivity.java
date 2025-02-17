package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    TextView textView;
    TextView levelViev;
    TextView rangeViev;
    TextView attemptViev;
    int level = 1;
    int attempts = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editText = findViewById(R.id.eText);
        button = findViewById(R.id.btn);
        textView = findViewById(R.id.tResult);
        levelViev = findViewById(R.id.tLevel);
        rangeViev = findViewById(R.id.tRange);
        attemptViev  = findViewById(R.id.tAttempt);

        startGame();
    }

    private int getNumber() {
        Random rnd = new Random();
        return rnd.nextInt(level * 10) + 1;
    }

    private void levelUp() {
        level++;
        attempts += 2;
        levelViev.setText("Level " + level);
        attemptViev.setText("Attempts: " + attempts);
        rangeViev.setText("Enter an integer value in 1-" + level * 10);
    }

    private void startGame() {
        int secretKey = getNumber();
        Log.i("Result", secretKey + "");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = editText.getText().toString().trim();
                try {
                    int intValue = Integer.parseInt(value);

                    if (intValue < 1 || intValue > level * 10) {
                        textView.setText("The number must be in the range from " + 1 + " to " + level * 10);
                        return;
                    }

                    if (intValue == secretKey) {
                        textView.setText("You won!");
                        levelUp();
                        startGame();
                    } else{
                        attempts--;
                        if (attempts > 0) {
                            String hint = intValue < secretKey ? "More" : "Less";
                            Toast.makeText(getApplicationContext(), hint, Toast.LENGTH_LONG).show();
                            attemptViev.setText("Attempts: " + attempts);
                        } else {
                            textView.setText("You lost! There was a number: " + secretKey);
                            resetGame();
                        }
                    }

                } catch (NumberFormatException e) {
                    textView.setText("Invalid input! Enter a number.");
                }
            }
        });

    }
    private void resetGame() {
        level = 1;
        attempts = 2;
        levelViev.setText("Level " + level);
        attemptViev.setText("Attempts: " + attempts);
        startGame();
    }
}