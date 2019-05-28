package com.example.permissiondemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TestCustomActionActivity extends AppCompatActivity {
    TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_test_custom_action);
        tvTest = findViewById(R.id.tvTestCustom);
        tvTest.setText(getIntent().getStringExtra("message"));
    }
}
