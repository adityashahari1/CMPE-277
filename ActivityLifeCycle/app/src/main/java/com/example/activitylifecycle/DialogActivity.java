package com.example.activitylifecycle;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DialogActivity extends AppCompatActivity {

    private TextView tvPauseCounterDialog;
    private TextView tvDestroyCounterDialog;
    private Button btnCloseDialog;

    private int pauseCount = 0;
    private int destroyCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dialog);

        tvPauseCounterDialog = findViewById(R.id.tvPauseCounterDialog);
        tvDestroyCounterDialog = findViewById(R.id.tvDestroyCounterDialog);
        btnCloseDialog = findViewById(R.id.btnCloseDialog);

        tvPauseCounterDialog.setText("onPause Count: " + pauseCount);
        tvDestroyCounterDialog.setText("onDestroy Count: " + destroyCount);

        btnCloseDialog.setOnClickListener(v -> finish());
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseCount++;
        tvPauseCounterDialog.setText("onPause Count: " + pauseCount);
    }

    @Override
    protected void onDestroy() {
        destroyCount++;
        super.onDestroy();
    }
}