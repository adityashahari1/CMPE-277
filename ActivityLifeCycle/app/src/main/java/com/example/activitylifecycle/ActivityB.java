package com.example.activitylifecycle;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityB extends AppCompatActivity {

    private TextView tvPauseCounterB;
    private TextView tvDestroyCounterB;
    private Button btnFinishB;

    private int pauseCount = 0;
    private int destroyCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_b);

        tvPauseCounterB = findViewById(R.id.tvPauseCounterB);
        tvDestroyCounterB = findViewById(R.id.tvDestroyCounterB);
        btnFinishB = findViewById(R.id.btnFinishB);

        tvPauseCounterB.setText("onPause Count: " + pauseCount);
        tvDestroyCounterB.setText("onDestroy Count: " + destroyCount);

        btnFinishB.setOnClickListener(v -> finish());
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseCount++;
        tvPauseCounterB.setText("onPause Count: " + pauseCount);
    }

    @Override
    protected void onDestroy() {
        destroyCount++;
        super.onDestroy();
    }
}