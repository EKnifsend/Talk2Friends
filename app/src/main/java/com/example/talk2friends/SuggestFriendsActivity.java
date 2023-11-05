package com.example.talk2friends;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class SuggestFriendsActivity extends AppCompatActivity {
    User user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest_friends);

        Intent intent = getIntent();
        user = (User) intent.getParcelableExtra("user");

        // Set up list of Friend recommendations
        ArrayList<User> friendRecs = new ArrayList<User>();


        /* Make for friends???
        InterestAdapter interestAdapter = new InterestAdapter(this,interests);
        ListView interestsView = (ListView) findViewById(R.id.interests);
        interestsView.setAdapter(interestAdapter);


        interestsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Call meeting page with meeting info from index i
            }
        });

         */
    }

    /*
     *  OnClick function for @+id/back button
     */
    public void cancel(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}
