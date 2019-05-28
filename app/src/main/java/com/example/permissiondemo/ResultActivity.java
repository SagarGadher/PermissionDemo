package com.example.permissiondemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    TextView tvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_result);
        tvMessage = findViewById(R.id.tvMessage);
        if (getIntent().getExtras() != null) {
            String message = getIntent().getExtras().getString("message");
            tvMessage.setText(message);
        }
    }
}
