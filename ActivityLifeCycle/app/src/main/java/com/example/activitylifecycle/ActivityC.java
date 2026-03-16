package com.example.activitylifecycle;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityC extends AppCompatActivity {

    private TextView tvPauseCounterC;
    private TextView tvDestroyCounterC;
    private Button btnFinishC;

    private int pauseCount = 0;
    private int destroyCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_c);

        tvPauseCounterC = findViewById(R.id.tvPauseCounterC);
        tvDestroyCounterC = findViewById(R.id.tvDestroyCounterC);
        btnFinishC = findViewById(R.id.btnFinishC);

        tvPauseCounterC.setText("onPause Count: " + pauseCount);
        tvDestroyCounterC.setText("onDestroy Count: " + destroyCount);

        btnFinishC.setOnClickListener(v -> finish());
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseCount++;
        tvPauseCounterC.setText("onPause Count: " + pauseCount);
    }

    @Override
    protected void onDestroy() {
        destroyCount++;
        super.onDestroy();
    }
}