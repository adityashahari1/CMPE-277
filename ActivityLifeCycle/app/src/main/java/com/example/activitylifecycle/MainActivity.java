package com.example.activitylifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView tvThreadCounter;
    private TextView tvPauseCounterA;
    private TextView tvDestroyCounterA;

    private int pauseCount = 0;
    private int destroyCount = 0;

    private final Handler handler = new Handler();
    private int threadCounter = 0;

    private Runnable counterRunnable = new Runnable() {
        @Override
        public void run() {
            threadCounter++;
            tvThreadCounter.setText("Thread Counter: " + threadCounter);
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        tvThreadCounter = findViewById(R.id.tvThreadCounter);
        tvPauseCounterA = findViewById(R.id.tvPauseCounterA);
        tvDestroyCounterA = findViewById(R.id.tvDestroyCounterA);

        Button btnStartB = findViewById(R.id.btnStartB);
        Button btnStartC = findViewById(R.id.btnStartC);
        Button btnDialog = findViewById(R.id.btnDialog);
        Button btnCloseApp = findViewById(R.id.btnCloseApp);

        tvThreadCounter.setText("Thread Counter: " + threadCounter);
        tvPauseCounterA.setText("onPause Count: " + pauseCount);
        tvDestroyCounterA.setText("onDestroy Count: " + destroyCount);

        handler.post(counterRunnable);

        btnStartB.setOnClickListener(v -> {
            threadCounter += 5;
            tvThreadCounter.setText("Thread Counter: " + threadCounter);

            Intent intent = new Intent(MainActivity.this, ActivityB.class);
            startActivity(intent);
        });

        btnStartC.setOnClickListener(v -> {
            threadCounter += 10;
            tvThreadCounter.setText("Thread Counter: " + threadCounter);

            Intent intent = new Intent(MainActivity.this, ActivityC.class);
            startActivity(intent);
        });

        btnDialog.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DialogActivity.class);
            startActivity(intent);
        });

        btnCloseApp.setOnClickListener(v -> finish());
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseCount++;
        tvPauseCounterA.setText("onPause Count: " + pauseCount);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyCount++;
        tvDestroyCounterA.setText("onDestroy Count: " + destroyCount);
        handler.removeCallbacks(counterRunnable);
    }
}