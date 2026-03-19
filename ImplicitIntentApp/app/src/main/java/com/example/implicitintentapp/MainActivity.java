package com.example.implicitintentapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText urlInput, phoneInput;
    Button launchBtn, ringBtn, closeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        urlInput = findViewById(R.id.urlInput);
        phoneInput = findViewById(R.id.phoneInput);
        launchBtn = findViewById(R.id.launchBtn);
        ringBtn = findViewById(R.id.ringBtn);
        closeBtn = findViewById(R.id.closeBtn);

        launchBtn.setOnClickListener(v -> {
            String url = urlInput.getText().toString();

            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });

        ringBtn.setOnClickListener(v -> {
            String phone = phoneInput.getText().toString();

            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phone));
            startActivity(intent);
        });

        closeBtn.setOnClickListener(v -> finish());
    }
}