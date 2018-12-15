package com.example.hemant.gitpullrequest;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_detail);

        TextView screenRotationRequestTest = findViewById(R.id.screen_rotation_request_text);

        /**
         * If screen rotation is portrait notify user to switch to landscape
         */
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            screenRotationRequestTest.setVisibility(View.VISIBLE);
        } else {
            screenRotationRequestTest.setVisibility(View.GONE);
            String diffUrl = getIntent().getStringExtra("PR");

            TextView textView = findViewById(R.id.diff_url);
            textView.setText(diffUrl);

            //TODO The diff parsing and display logic
        }
    }
}
