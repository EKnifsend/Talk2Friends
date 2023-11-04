package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String message = intent.getStringExtra("com.example.sendmessage.MESSAGE");
        message = message + " : Fight On!";
        // Create user with ID
    }

    public void editProfile(View view){
        EditText editView = (EditText) findViewById(R.id.editTextTextPersonName);
        String message = editView.getText().toString();

        Intent intent = new Intent(this, DisplayMessageActivity.class);
        intent.putExtra("com.example.sendmessage.MESSAGE", message);

        startActivity(intent);
    }

    public void viewFriends(View view){

    }

    public void suggestFriends(View view){

    }

    public void invite(View view){

    }

    public void createMeeting(View view){

    }

    public void selectMeeting(View view){

    }
}