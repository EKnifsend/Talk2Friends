package com.example.talk2friends;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView homePageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the TextView
        homePageTextView = findViewById(R.id.textView);

        // Retrieve the user ID from the Intent
        String userId = getIntent().getStringExtra("userId");

        // Display "Home page" and the user ID (if available)
        if (userId != null) {
            homePageTextView.setText("Home page\nUser ID: " + userId);
        } else {
            homePageTextView.setText("Home page");
        }
    }
}
