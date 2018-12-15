package com.example.hemant.gitpullrequest;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String diffUrl = getIntent().getStringExtra("PR");

        TextView textView = findViewById(R.id.diff_url);
        textView.setText(diffUrl);
    }
}
