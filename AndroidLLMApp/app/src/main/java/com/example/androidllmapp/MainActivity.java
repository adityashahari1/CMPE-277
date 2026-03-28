package com.example.androidllmapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    private EditText etPrompt;
    private TextView tvResponse;
    private Button btnSend, btnCancel;

    private static final String OPENAI_API_KEY = "API_KEY";

    private CallOpenAITask currentTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPrompt = findViewById(R.id.etPrompt);
        tvResponse = findViewById(R.id.tvResponse);
        btnSend = findViewById(R.id.btnSend);
        btnCancel = findViewById(R.id.btnCancel);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String prompt = etPrompt.getText().toString().trim();

                if (prompt.isEmpty()) {
                    tvResponse.setText("Please enter a prompt.");
                    return;
                }

                tvResponse.setText("Loading...");

                currentTask = new CallOpenAITask();
                currentTask.execute(prompt);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentTask != null) {
                    currentTask.cancel(true);
                    tvResponse.setText("Request cancelled.");
                }
            }
        });
    }

    private class CallOpenAITask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String userPrompt = strings[0];
            HttpURLConnection connection = null;

            try {
                URL url = new URL("https://api.openai.com/v1/responses");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Authorization", "Bearer " + OPENAI_API_KEY);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                JSONObject jsonBody = new JSONObject();
                jsonBody.put("model", "gpt-5-mini");

                JSONArray inputArray = new JSONArray();
                JSONObject messageObject = new JSONObject();
                messageObject.put("role", "user");
                messageObject.put("content", userPrompt);
                inputArray.put(messageObject);

                jsonBody.put("input", inputArray);

                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(jsonBody.toString());
                writer.flush();
                writer.close();
                os.close();

                int responseCode = connection.getResponseCode();

                InputStream is;
                if (responseCode >= 200 && responseCode < 300) {
                    is = connection.getInputStream();
                } else {
                    is = connection.getErrorStream();
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    if (isCancelled()) {
                        return "Cancelled.";
                    }
                    result.append(line);
                }

                reader.close();

                if (responseCode >= 200 && responseCode < 300) {
                    JSONObject responseJson = new JSONObject(result.toString());

                    // Try to read output_text directly
                    if (responseJson.has("output_text")) {
                        return responseJson.getString("output_text");
                    }

                    // Fallback in case output_text is absent
                    return responseJson.toString(2);
                } else {
                    return "Error: " + result.toString();
                }

            } catch (Exception e) {
                return "Exception: " + e.getMessage();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

        @Override
        protected void onPostExecute(String responseText) {
            tvResponse.setText(responseText);
        }

        @Override
        protected void onCancelled() {
            tvResponse.setText("Request cancelled.");
        }
    }
}